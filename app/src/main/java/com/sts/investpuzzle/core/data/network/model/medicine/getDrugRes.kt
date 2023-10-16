package com.sts.investpuzzle.core.data.network.model.medicine

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Drug(
    val name:String,
    val dose:String,
    val strength:String
):Parcelable