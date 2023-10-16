package com.sts.investpuzzle

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.sts.investpuzzle.core.data.network.model.medicine.ClassName
import com.sts.investpuzzle.core.data.network.model.medicine.ClassName2
import com.sts.investpuzzle.core.data.network.model.medicine.Diabetes
import com.sts.investpuzzle.core.data.network.model.medicine.Drug
import com.sts.investpuzzle.core.data.network.model.medicine.Labs
import com.sts.investpuzzle.core.data.network.model.medicine.Medications
import com.sts.investpuzzle.core.data.network.model.medicine.MedicationsClasses
import com.sts.investpuzzle.core.data.network.model.medicine.MedicineResponse
import com.sts.investpuzzle.core.data.network.model.medicine.Problems
import com.sts.investpuzzle.core.data.network.repository.MedicineRepository
import com.sts.investpuzzle.core.data.network.repository.MedicineRepositoryImp
import com.sts.investpuzzle.core.data.prefs.PreferencesHelper
import com.sts.investpuzzle.core.data.session.SessionHelper
import com.sts.investpuzzle.ui.medicine.MedicineInteractor
import com.sts.investpuzzle.ui.medicine.MedicineViewModel
import com.sts.investpuzzle.utils.Validator
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MedicineUnitTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testScheduler = TestScheduler();
    private val testSchedulerProvider = TestSchedulerProvider(testScheduler);

    private val compositeDisposable = CompositeDisposable()

    private lateinit var interactor : MedicineInteractor
    private lateinit var viewModel : MedicineViewModel

    private val preferenceHelper: PreferencesHelper = TestPreferenceHelper()
    private val sessionHelper = mockk<SessionHelper>()

    private val savedHandleState = SavedStateHandle();

    private val drug1: Drug = mockk {
        every {
            name
        } returns "asprin"

        every {
            dose
        } returns ""

        every {
            strength
        } returns "500 mg"
    }

    private val drug2: Drug = mockk {
        every {
            name
        } returns "somethingElse"

        every {
            dose
        } returns ""

        every {
            strength
        } returns "500 mg"
    }

    private val mockClass1: ClassName = mockk {
        every { associatedDrug1 } returns listOf(drug1)
        every { associatedDrug2 } returns listOf(drug2)
    }

    private val mockClass2: ClassName2 = mockk {
        every { associatedDrug1 } returns listOf(drug1)
        every { associatedDrug2 } returns listOf(drug2)
    }

    private val mockMedicationClasses: MedicationsClasses = mockk {
        every { className } returns listOf(mockClass1)
        every { className2 } returns listOf(mockClass2)
    }

    private val mockLabs: Labs = mockk {
        every { missingField } returns null
    }

    private val mockMedications: Medications = mockk {
        every { medicationsClasses } returns listOf(mockMedicationClasses)
    }

    private val mockDiabetes: Diabetes = mockk {
        every { medications } returns listOf(mockMedications)
        every { labs } returns listOf(mockLabs)
    }

    private val mockProblems: Problems = mockk {
        every {
            Diabetes
        } returns listOf(mockDiabetes)
    }

    private val mockResponse: MedicineResponse = mockk {
        every {
            problems
        } returns listOf(mockProblems)
    }

    @Before
    fun setUp() {
        interactor = spyk(MedicineInteractor(preferenceHelper, sessionHelper, mockk()))
        InteractorSetup.setInteractorDefaults(interactor)
    }

    @After
    fun tearDown() {
        compositeDisposable.dispose()
    }

    @Test
    fun test_emailValidation() {

        // check email validation
        val validator = Validator();
        assertTrue(validator.isValidEmail("test@gmail.com"));
        assertFalse(validator.isValidEmail("test@gailcom"));
        assertTrue(validator.isValidEmail("testgmail.com"));

    }

    @Test
    fun test_APIResponseParse() {
        setupViewModelTriggerActions(mockResponse)

        // check API response is not null
        assertNotNull(viewModel.drugs.value?.peekContent())

        // check API response(drug size) is 2 =
        assertTrue(
            viewModel.drugs.value?.peekContent()?.size == 2,
        )

        // check first drug name is "somethingElse
        assertTrue(viewModel.drugs.value?.peekContent()?.get(0)?.name == "somethingElse")

        // check second drug name is asprin
        assertTrue(viewModel.drugs.value?.peekContent()?.get(1)?.name == "asprin")

        // check first drug name is  not asprin
        assertFalse(viewModel.drugs.value?.peekContent()?.get(0)?.name == "asprin")
    }

    private fun setupViewModelTriggerActions(response: MedicineResponse) {
        every {
            interactor.doMedicine()
        } returns Single.just(response)

        viewModel = MedicineViewModel(testSchedulerProvider, compositeDisposable, interactor, savedHandleState)
        viewModel.getMedicine()
        testScheduler.triggerActions()
    }
}