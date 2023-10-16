package com.sts.investpuzzle.core.data.network.model.medicine

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicineResponse (

    @SerializedName("problems" ) val problems : List<Problems>
): Parcelable

@Parcelize
data class Problems (

    @SerializedName("Diabetes" ) var Diabetes : List<Diabetes> ,

):Parcelable

@Parcelize
data class Diabetes (

    @SerializedName("medications" ) var medications : List<Medications> ,
    @SerializedName("labs"        ) var labs        : List<Labs>

):Parcelable

@Parcelize
data class Medications (

    @SerializedName("medicationsClasses" ) var medicationsClasses : List<MedicationsClasses>

):Parcelable

@Parcelize
data class Labs (

    @SerializedName("missing_field" ) var missingField : String? = null

):Parcelable

@Parcelize
data class MedicationsClasses (

    @SerializedName("className"  ) var className  : List<ClassName>,
    @SerializedName("className2" ) var className2 : List<ClassName2>

):Parcelable

@Parcelize
data class ClassName2 (

    @SerializedName("associatedDrug"   ) var associatedDrug1   : List<Drug>,
    @SerializedName("associatedDrug#2" ) var associatedDrug2 : List<Drug>

):Parcelable

@Parcelize
data class ClassName (

    @SerializedName("associatedDrug"   ) var associatedDrug1   : List<Drug>,
    @SerializedName("associatedDrug#2" ) var associatedDrug2 : List<Drug>

):Parcelable


