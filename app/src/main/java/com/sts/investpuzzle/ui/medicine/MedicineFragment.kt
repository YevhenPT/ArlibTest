package com.sts.investpuzzle.ui.medicine

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.sts.investpuzzle.base.BaseFragment
import com.sts.investpuzzle.databinding.LayoutMedicineBinding
import dagger.hilt.android.AndroidEntryPoint
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.sts.investpuzzle.R
import com.sts.investpuzzle.core.data.network.model.medicine.Drug
import com.sts.investpuzzle.extensions.RecyclerViewTouchListener

import com.sts.investpuzzle.extensions.observeEvent
import com.sts.investpuzzle.ui.detail.DetailViewModel
import com.sts.investpuzzle.ui.login.UserModel
import org.json.JSONObject


@AndroidEntryPoint
class MedicineFragment:BaseFragment<MedicineViewModel, LayoutMedicineBinding>() {

    private val medicineAdapter:MedicineAdapter = MedicineAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        setupViewBinding(LayoutMedicineBinding.inflate(inflater,container,false))
        withViewModel<MedicineViewModel> {
            observeEvent(drugs){
                loadDrugs(it)
            }
        }
        return viewBind.root
    }

    override fun setUp() {

        viewModel.user?.let { displayUser(it) }

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        viewBind.rcyMedicines.layoutManager = layoutManager
        viewBind.rcyMedicines.adapter = medicineAdapter

        viewBind.txtTime.text = viewModel.getTime()


        viewBind.rcyMedicines.addOnItemTouchListener(RecyclerViewTouchListener(requireContext(),viewBind.rcyMedicines, object : RecyclerViewTouchListener.ClickListener{
            override fun onClick(view: View, position: Int) {
                val bundle = Bundle()
                bundle.apply {
                    putParcelable(DetailViewModel.MEDICINE, medicineAdapter.getItem(position))
                }
                findNavController().navigate(R.id.action_medicineFragment_to_detailFragment,bundle)
            }
        }))
        viewModel.getMedicine()
    }

    private fun loadDrugs(data:List<Drug>){
        medicineAdapter.loadData(data.toMutableList())
    }

    fun getMedicine(){

        Rx2AndroidNetworking.get("https://run.mocky.io/v3/029acb35-60fe-438b-9d42-6d32d1baa4a4")
            .build()

            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    // do anything with response

                    val jsonArrProblem = response.getJSONArray("problems")
                    val jsonProblem = jsonArrProblem.getJSONObject(0)
                    val jsonArrayDiabetes = jsonProblem.getJSONArray("Diabetes")
                    val jsonArrayAsthma = jsonProblem.getJSONArray("Asthma")
                    val jsonDiabetes = jsonArrayDiabetes.getJSONObject(0)
                    val jsonArrayMedications = jsonDiabetes.getJSONArray("medications")
                    val jsonMedications = jsonArrayMedications.getJSONObject(0)
                    val jsonArrayMedicationClasses = jsonMedications.getJSONArray("medicationsClasses");
                    val jsonMedicationClasses = jsonArrayMedicationClasses.getJSONObject(0)
                    val jsonArrayClassName = jsonMedicationClasses.getJSONArray("className")
                    val jsonClassName = jsonArrayClassName.getJSONObject(0)
                    val jsonArrayDrug = jsonClassName.getJSONArray("associatedDrug")
                    val jsonDrug = jsonArrayDrug.getJSONObject(0)
                    val jsonArrayClassName2 = jsonMedicationClasses.getJSONArray("className2")

                    Log.d("response===>",jsonDrug.toString())

                }

                override fun onError(error: ANError) {
                    // handle error
                    Log.d("error====>",error.toString())
                }
            })
    }

    private fun displayUser(user:UserModel){
        viewBind.txtWelcome.text = "Welcome" + " "+  user.name + " " + user.email
    }

}
