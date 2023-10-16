package com.sts.investpuzzle.ui.medicine

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.investpuzzle.core.data.network.model.medicine.Drug
import com.sts.investpuzzle.databinding.ItemMedicineBinding

class MedicineAdapter:RecyclerView.Adapter<MedicineAdapter.ViewHolder>() {

    private val allData = mutableListOf<Drug>()
    private lateinit var context : Context

    @SuppressLint("NotifyDataSetChanged")
    fun loadData(data : List<Drug>){
        allData.clear()
        allData.addAll(data)
        notifyDataSetChanged()
        Log.d("value====", allData.toString())

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ItemMedicineBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        Log.d("length--->", allData.size.toString())

        return allData.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(allData[position]){


                viewBinding.txvName.text = name
                viewBinding.txvDose.text= dose
                viewBinding.txvStength.text = strength


            }
        }


    }
    fun getItem(position: Int) : Drug = allData[position]


    inner class ViewHolder(val viewBinding:ItemMedicineBinding):RecyclerView.ViewHolder(viewBinding.root)

}


