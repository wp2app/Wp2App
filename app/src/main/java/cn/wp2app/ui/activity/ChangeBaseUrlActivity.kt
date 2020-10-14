package cn.wp2app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.wp2app.MainActivity
import cn.wp2app.R
import cn.wp2app.app.WPApp
import cn.wp2app.base.BaseActivity
import cn.wp2app.model.repository.UserRepository
import cn.wp2app.ui.fragment.categories.CategoriesViewModel
import cn.wp2app.ui.fragment.search.SearchViewModel
import cn.wp2app.widget.LoadingDialog
import kotlinx.android.synthetic.main.activity_change_base_url.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.title_layout.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import java.io.File
import kotlin.coroutines.CoroutineContext

class ChangeBaseUrlActivity : BaseActivity(), CoroutineScope {
    lateinit var viewModel: SearchViewModel
    lateinit var loading: LoadingDialog

    override fun getLayoutResId() = R.layout.activity_change_base_url

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    var job: Job = Job()

    override fun initData() {
        val builder: LoadingDialog.Builder = LoadingDialog.Builder(this@ChangeBaseUrlActivity).setMessage("请稍等...").setCancelable(false)
        loading = builder.create()
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        viewModel.apply {
            status.observe(this@ChangeBaseUrlActivity, Observer {
                when(it) {
                    1->{

                        loading.show()
                    }
                    20->{
                        WPApp.base_url = WPApp.test_url
                        //需要清除数据
                        clear()
                    }
                    100->{ // 网址错误
                        if(loading.isShowing) {
                            loading.dismiss()
                        }

                        Toast.makeText(this@ChangeBaseUrlActivity, R.string.wp_site_url_error, Toast.LENGTH_SHORT).show()
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
        toolbar_back.setOnClickListener { onBackPressed() }
        toolbar_title.text = "变更Worpdress网站地址"

        change_url_next.setOnClickListener {doNext()}

        btn_cancel.setOnClickListener {
            /*
            val intent = Intent()
            intent.putExtra("do_refresh", true)
            this@ChangeBaseUrlActivity.setResult(RESULT_OK, intent)
            WPApp.base_url = "https://www.wp2app.cn/"
            //startActivity(Intent(this@ChangeBaseUrlActivity, MainActivity::class.java))
            this@ChangeBaseUrlActivity.finish()*/
            finish()
        }

    }

    fun doNext():Unit {
        var sUrl = change_url_edit.text.toString()
        if( sUrl.isEmpty() ) {
            Toast.makeText(this,"请输入你的网址", Toast.LENGTH_SHORT).show()
            change_url_edit.requestFocus()

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

        WPApp.test_url = sUrl

        viewModel.testTag()
    }

    fun clear() {
        launch {
            application.cacheDir.suicide()
            application.filesDir.suicide()

            val sp = this@ChangeBaseUrlActivity.getSharedPreferences("wp_options", MODE_PRIVATE)
            sp.edit().putString("base_url", WPApp.test_url).apply()

            val db: UserRepository by inject()
            db.TruncateTable()

            val intent = Intent()
            intent.putExtra("do_refresh", true)
            this@ChangeBaseUrlActivity.setResult(RESULT_OK, intent)

            withContext(Dispatchers.Main) {
                if(loading.isShowing) {
                    loading.dismiss()
                }

                //startActivity(Intent(this@ChangeBaseUrlActivity, MainActivity::class.java))
                this@ChangeBaseUrlActivity.finish()

            }
        }
    }

    fun File.suicide() {
        if (isFile) delete()
        if (isDirectory) listFiles().forEach { it.suicide() }
    }


}