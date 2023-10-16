package com.sts.investpuzzle.extensions

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.github.mikephil.charting.charts.LineChart

class CustomViewPager(context: Context, attrs : AttributeSet) : ViewPager(context, attrs) {

    var mAnimStarted = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!mAnimStarted && null != adapter) {
            var  height = 0
            val child = (adapter as FragmentPagerAdapter).getItem(currentItem).view
            if (child != null) {
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
                height = child.measuredHeight
                if (height < minimumHeight){
                    height = minimumHeight
                }
            }

            val newHeight = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            if  (layoutParams.height != 0 && heightMeasureSpec != newHeight){
                val targetHeight = height
                val currentHeight = layoutParams.height
                val heightChange = targetHeight - currentHeight

                val a = object : Animation(){
                    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                        super.applyTransformation(interpolatedTime, t)
                        if (interpolatedTime >= 1){
                            layoutParams.height = targetHeight
                        }else {
                            val stepHeight : Int = (heightChange * interpolatedTime).toInt()
                            layoutParams.height = currentHeight + stepHeight
                        }
                        requestLayout()
                    }

                    override fun willChangeBounds(): Boolean {
                        return true
                    }
                }
                a.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {
                        mAnimStarted = true
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        mAnimStarted = false
                    }

                    override fun onAnimationRepeat(p0: Animation?) {}
                })
                a.duration = 300
                startAnimation(a)
                mAnimStarted = true
            } else {
                //heightMeasureSpec = newHeight
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun canScroll(v: View?, checkV: Boolean, dx: Int, x: Int, y: Int): Boolean {
        if (v is LineChart) return true
        return super.canScroll(v, checkV, dx, x, y)
    }
}