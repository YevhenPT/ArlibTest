import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.widget.TextView
import com.sts.investpuzzle.extensions.SmartClickableSpan.ClickableOption

class SmartClickableSpan(builder: Builder, textView: TextView) {
    private var stringBuilder: SpannableStringBuilder
    private var mTextView: TextView = textView

    class  Builder{

        val stringBuilder = SpannableStringBuilder()
        private var isAutoSpacing = false
        var highlighterColor = Color.TRANSPARENT

        fun regularText(text: String): Builder {
            stringBuilder.append(text).append(if (isAutoSpacing) " " else "")
            return this
        }
        fun autoSpacing() : Builder {
            isAutoSpacing = true
            return this
        }
        fun clickableText(options: ClickableOption): Builder{
            val text = options.getText()
            this.stringBuilder.append(text)

            val clickableSpan = options.getOnClick()
            val lengthLeft = this.stringBuilder.toString().indexOf(text)
            val lengthRight = this.stringBuilder.toString().indexOf(text) + text.length
            this.stringBuilder.setSpan(clickableSpan, lengthLeft, lengthRight, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            return this
        }

        fun into(textView: TextView): SmartClickableSpan {
            return SmartClickableSpan(this, textView)
        }
    }

    init {
        this.stringBuilder = builder.stringBuilder
        mTextView.highlightColor = builder.highlighterColor
        mTextView.movementMethod = LinkMovementMethod.getInstance()
        mTextView.setText(this.stringBuilder, TextView.BufferType.SPANNABLE)
        mTextView.isSelected = true
    }

    fun getAppendedText(): String = stringBuilder.toString()
}