package com.sts.investpuzzle.base

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.sts.investpuzzle.R
import com.sts.investpuzzle.extensions.observeEvent
import es.dmoral.toasty.Toasty

abstract class BaseActivity<V: BaseViewModel<*>, VB : ViewBinding> : AppCompatActivity(), View.OnClickListener {
    lateinit var viewModel : V

    private var viewBinding : VB? = null
    protected val viewBind get() = viewBinding!!
    protected abstract val progressView : View?
    protected abstract val errorView : View?
    protected abstract fun setUp()

    fun showToast(message : String){
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        val view = toast.view
        view?.setPadding(40, 25, 40, 25)
        view?.setBackgroundResource(R.drawable.bg_toast)
        val text= view?.findViewById<TextView>(android.R.id.message)
        text?.setTextColor(Color.WHITE)
        toast.show()
    }

    fun setupViewBinding(viewBinding: VB){
        this.viewBinding = viewBinding
        setContentView(viewBinding.root)
    }

    fun showLoading() {progressView?.visibility = VISIBLE}
    fun hideLoading(){progressView?.visibility = GONE}
    fun showError(title : String, message : String) {
        errorView?.visibility = VISIBLE
        errorView?.findViewById<TextView>(R.id.txvErrorTitle)?.text = title
        errorView?.findViewById<TextView>(R.id.txvErrorMessage)?.text = message
        errorView?.findViewById<TextView>(R.id.txvClose)?.setOnClickListener { errorView?.visibility = GONE }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        if (viewBind == null){
            throw  java.lang.RuntimeException("viewBinding not initialized")
        }
        setUp()
    }

    internal inline fun <reified T : ViewModel> withViewModel(body: T.() -> Unit): T {
        val vm = ViewModelProvider(this)[T::class.java]
        vm.takeIf { it is BaseViewModel<*> }?.let {
            @Suppress("UNCHECKED_CAST")
            viewModel = it as V
            initBaseModelObservers()
        }
        vm.body()
        return vm
    }

    private fun initBaseModelObservers(){
        observeEvent(viewModel.toastMessage) {
            showToast(it)
        }
        observeEvent(viewModel.progressVisible){
            if (it) showLoading() else hideLoading()
        }
        observeEvent(viewModel.errorMessage) { showError(it.first, it.second) }
        observeEvent(viewModel.showInternetConnectionError) {
            if (it) hideLoading() else showLoading()
        }
    }


    override fun dispatchTouchEvent(ev : MotionEvent): Boolean {
        if (currentFocus != null) {
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}