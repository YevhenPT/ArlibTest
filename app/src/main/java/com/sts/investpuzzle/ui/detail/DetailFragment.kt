package com.sts.investpuzzle.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sts.investpuzzle.base.BaseFragment
import com.sts.investpuzzle.core.data.network.model.medicine.Drug
import com.sts.investpuzzle.databinding.LayoutDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment:BaseFragment<DetailViewModel,LayoutDetailBinding>() {
    override fun setUp() {

        viewModel.medicine?.let {
            display(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setupViewBinding(LayoutDetailBinding.inflate(inflater,container,false))
        withViewModel<DetailViewModel> {

        }
        return viewBind.root
    }

    private fun display(drug:Drug){

        viewBind.txtName.text = drug.name
        viewBind.txtDose.text = drug.dose
        viewBind.txtStrength.text = drug.strength
    }
}