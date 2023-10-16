package com.sts.investpuzzle.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.sts.investpuzzle.R
import com.sts.investpuzzle.base.BaseFragment
import com.sts.investpuzzle.databinding.LayoutLoginBinding
import com.sts.investpuzzle.extensions.observeEvent
import com.sts.investpuzzle.ui.medicine.MedicineViewModel
import com.sts.investpuzzle.utils.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment:BaseFragment<LoginViewModel,LayoutLoginBinding>() {

    private val validator = Validator()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setupViewBinding(LayoutLoginBinding.inflate(inflater,container,false))
        withViewModel<LoginViewModel> {

            observeEvent(users){

                Log.d("users====>",it.size.toString())
            }
        }
        return viewBind.root
    }

    override fun setUp() {

        viewBind.txvLogIn.setOnClickListener(this)
        viewBind.txvContact.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.txvLogIn ->{

                if(viewBind.edtUserName.text.toString().isEmpty()){
                    showError(getString(R.string.error),getString(R.string.input_username))
                }else if (viewBind.edtEmail.text.toString().isEmpty()){
                    showError(getString(R.string.error), getString(R.string.input_email))
                }else if( !validator.isValidEmail(viewBind.edtEmail.text.toString())){

                    showError(getString(R.string.error), getString(R.string.wrong_email))
                }else{


                        viewModel.getUserName(viewBind.edtUserName.text.toString())
                        if (viewModel.isUser){

                            val bundle = Bundle()
                            bundle.apply {
                                putParcelable(MedicineViewModel.USER, UserModel(viewBind.edtUserName.text.toString(),viewBind.edtEmail.text.toString()))
                            }


                            findNavController().navigate(R.id.action_loginFragment_to_medicineFragment, bundle)
                        }else{
                            viewModel.insertUser(viewBind.edtUserName.text.toString(),
                                viewBind.edtEmail.text.toString())
                            viewModel.getAllUsers()
                            findNavController().navigate(R.id.action_loginFragment_to_medicineFragment)
                        }
                    }

                }
            R.id.txvContact ->{
                findNavController().navigate(R.id.action_loginFragment_to_contactFragment)
            }

        }
        super.onClick(v)
    }


}