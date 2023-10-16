package com.sts.investpuzzle.extensions.SmartClickableSpan

import android.content.Context
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import com.sts.investpuzzle.R

interface SmartClickableInterface{
    fun onClick()
}
class ClickableOption(val event: SmartClickableInterface) {

    private lateinit var text: String
    private lateinit var clickableSpan: ClickableSpan
    private var color = R.color.black

    fun setText(text: String, context: Context, isBold: Boolean, isUnderLine: Boolean = false): ClickableOption {
        this.text = text
        this.clickableSpan = object : ClickableSpan(){
            override fun onClick(p0: View) {
                event.onClick()
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = context.getColor(color)
                if (isBold)
                    ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                ds.isUnderlineText = isUnderLine
            }
        }
        return this
    }
    fun setColor(color: Int): ClickableOption {
        this.color = color
        return this
    }
    fun getText(): String = text
    fun getOnClick(): ClickableSpan = clickableSpan
    fun getColor(): Int = color
}