package com.sts.investpuzzle.ui.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name:String,
    val email:String,

): Parcelable