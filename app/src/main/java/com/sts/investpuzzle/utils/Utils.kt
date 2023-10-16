package com.sts.investpuzzle.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager

import androidx.viewbinding.ViewBinding

import com.sts.investpuzzle.constants.MarketTimeZone

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt

object Utils {

    fun getTimePoint(timeStamp : Long, dateFormat : SimpleDateFormat, hhmmss : String) : Date {
        val date = Date(timeStamp)
        val year = timeFormat("yyyy", dateFormat.timeZone.id).format(date)
        val month = timeFormat("MM", dateFormat.timeZone.id).format(date)
        val day = timeFormat("dd", dateFormat.timeZone.id).format(date)

        return dateFormat.parse("$year-$month-$day $hhmmss")!!
    }
    @SuppressLint("SimpleDateFormat")
    fun timeFormat(format : String, zoneId: String = MarketTimeZone.EST) : SimpleDateFormat {
        val dt = SimpleDateFormat(format)
        dt.timeZone = TimeZone.getTimeZone(zoneId)
        return dt
    }

    fun getFormattedNumber(number : Float) : String {
        if(number >= 1000000000){
            return String.format("%.2fB", number/ 1000000000.0)
        }

        if(number >= 1000000){
            return String.format("%.2fM", number/ 1000000.0)
        }

        if(number >= 100000){
            return String.format("%.2fL", number/ 100000.0)
        }

        if(number >=1000){
            return String.format("%.2fK", number/ 1000.0)
        }
        return number.toString()
    }

    fun roundUp2(value : Float) : String {
        return DecimalFormat("0.00").format(value)
    }

    fun roundUp3(value : Float) : String {
        return DecimalFormat("0.000").format(value)
    }

    fun getPriceRange(range : String?) : String {
        if (range == null) return "null"
        val start = range.split("-").first().trim()
        val end = range.split("-").last().trim()
        return "\$$start - \$$end"
    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeDiff(strDate: String) : String {
        val df1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        df1.timeZone = TimeZone.getTimeZone("UTC")
        val serverDate = df1.parse(strDate)
        val diff = Calendar.getInstance().time.time - serverDate!!.time
        val days = (diff / (1000*60*60*24)).toInt()
        if (days > 30) {
            val m = days / 30
            val d = days % 30
            if (d == 0) {
                if (m > 1) return "${abs(m)}mo"
                return "${abs(m)}mo"
            }
            return "${abs(m)}mo"
        }
        if (days > 1) return "${abs(days)}d"
        val hours = ((diff - (1000*60*60*24*days)) / (1000*60*60)).toInt()
        if (hours > 1) {
            return "${abs(hours)}h"
        }

        val min : Long = (diff - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60)

        if (min > 1) return "${abs(min)}m"
        return "${abs(min)}m"
    }

    fun numberFormat(number : Float) : String{
        val data = number.toString()
        if (!TextUtils.isEmpty(data)){
            val bd = BigDecimal(data.toDouble())
            val df = DecimalFormat(",###,###.00")
            return df.format(bd)
        }
        return  ""
    }

    fun getFileExtension(filePath : String) : String = filePath.substring(filePath.lastIndexOf("."))

    fun getPercent(value : Float ) : String{
        return "${roundUp2(value)}%"
    }

    fun customDialog(context: Context, viewBinding: ViewBinding) : Dialog{
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(viewBinding.root)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        return dialog
    }

    fun getMaxYearOfBirthday() : Int{
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    fun intToStringTime(sec : Int) : String {
        val min = sec / 60
        val seconds = sec - min * 60
        return "$min:$seconds"
    }

    fun getTotalPrice(price : Float, vol : Int, currencyRate : Float) : String{
        val totalPrice = (100 * price * vol * currencyRate).roundToInt() / 100.0
        return numberFormat(totalPrice.toFloat())
    }

    @SuppressLint("SimpleDateFormat")
    fun getTime(timestamp : Long) : String {
        val df = SimpleDateFormat("hh:mm aa")
        return df.format(timestamp)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(timestamp: Long) : String {
        val df = SimpleDateFormat("MM-dd-yyyy")
        return df.format(timestamp)
    }
}