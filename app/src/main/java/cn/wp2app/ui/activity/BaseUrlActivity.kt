package cn.wp2app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import cn.wp2app.MainActivity
import cn.wp2app.R
import cn.wp2app.app.WPApp
import cn.wp2app.base.BaseActivity
import cn.wp2app.config.Constant
import cn.wp2app.ui.fragment.categories.CategoriesViewModel
import cn.wp2app.ui.fragment.search.SearchViewModel
import cn.wp2app.widget.LoadingDialog
import kotlinx.android.synthetic.main.activity_base_url.*
import kotlinx.android.synthetic.main.title_layout.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.java.KoinJavaComponent.inject

class BaseUrlActivity : BaseActivity() {
    private val regex="((https|http)?://)[^s]+".toRegex()

    private val viewModel: SearchViewModel by lazy {ViewModelProvider(this)[SearchViewModel::class.java]}
    lateinit var loading: LoadingDialog

    override fun getLayoutResId() = R.layout.activity_base_url

    override fun initData() {
        val builder: LoadingDialog.Builder = LoadingDialog.Builder(this@BaseUrlActivity).setMessage("请稍等...").setCancelable(false)
        loading = builder.create()

        viewModel.apply {
            status.observe(this@BaseUrlActivity, Observer {
                when(it) {
                    1->{

                        loading.show()
                    }
                    20->{
                        if(loading.isShowing) {
                            loading.dismiss()
                        }
                        val sp = this@BaseUrlActivity.getSharedPreferences("wp_options", MODE_PRIVATE)
                        sp.edit().putString("base_url", WPApp.test_url).apply()

                        startActivity(Intent(this@BaseUrlActivity, MainActivity::class.java))
                        finish()
                    }
                    100->{ // 网址错误
                        if(loading.isShowing) {
                            loading.dismiss()
                        }
                        Toast.makeText(this@BaseUrlActivity, R.string.wp_site_url_error, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        if(loading.isShowing) {
                            loading.dismiss()
                        }
                    }
                }

            })
        }
    }

    override fun initView() {
        url_next.setOnClickListener { doNext() }

        btn_skip.setOnClickListener { skip() }
    }

    fun isUrl(s:String):Boolean {
        if( s.startsWith("https://") || s.startsWith("http://") ) {
            return true
        }
        return false
    }

    fun doNext():Unit {
        var sUrl = base_url_edit.text.toString()
        if( sUrl.isEmpty() ) {
            Toast.makeText(this,"请输入你的网址", Toast.LENGTH_SHORT).show()
            base_url_edit.requestFocus()

            return
        }

        /*
        if( !isUrl(sUrl) ) {
            Toast.makeText(this,"请输入合法的网站", Toast.LENGTH_SHORT).show()
            base_url_edit.requestFocus()

            return
        }
        */
        if( !sUrl.startsWith("https://")) sUrl = "https://" + sUrl

        if( !sUrl.endsWith("/")) sUrl += "/"

       // WPApp.base_url = sUrl
        WPApp.test_url = sUrl

        viewModel.testTag()
    }

    private fun skip() {
        val sp = this@BaseUrlActivity.getSharedPreferences("wp_options", MODE_PRIVATE)
        sp.edit().putString("base_url", Constant.DEFAULT_BASE_URL).apply()

        startActivity(Intent(this@BaseUrlActivity, MainActivity::class.java))
        finish()
    }
}
