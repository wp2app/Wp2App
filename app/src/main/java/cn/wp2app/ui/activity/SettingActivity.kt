package cn.wp2app.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.icu.math.BigDecimal
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cn.wp2app.R
import cn.wp2app.base.BaseActivity
import cn.wp2app.config.Constant
import cn.wp2app.model.repository.UserRepository
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.title_layout.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import java.io.File
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

class SettingActivity : BaseActivity(), CoroutineScope {
    var totalSize : Long = 0
    override fun getLayoutResId() = R.layout.activity_setting

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    var job: Job = Job()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroy() {
        job.cancel()

        super.onDestroy()
    }

    override fun initData() {
        getCacheSize()

        toolbar_back.setOnClickListener { onBackPressed() }
    }

    override fun initView() {
        toolbar_title.text = "设置"
        tv_clear_cache.setOnClickListener {
            this.alert("清除所有缓存？", "清除", "取消") {


                launch {
                    application.cacheDir.suicide()
                    application.filesDir.suicide()

                    val sp = application.getSharedPreferences("wp_options", MODE_PRIVATE)
                    sp.edit().putString("base_url", "").apply()
                    //message.value = "成功清除${cacheSize.formatMemorySize()}缓存"
                    //this.cacheSize.set("0K")

                    val db: UserRepository by inject()
                    db.TruncateTable()

                    totalSize = 0

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SettingActivity, "成功清除${totalSize.formatMemorySize()}缓存", Toast.LENGTH_SHORT).show()

                        tv_cache_size.text = "0K"
                    }
                }
            }
        }

        val sp = application.getSharedPreferences("wp_options", MODE_PRIVATE)
        var checked = sp.getBoolean("post_use_web_view", true)
        switcher_x_webview.setChecked(checked)

        switcher_x_webview.setOnCheckedChangeListener { checked ->
            val sp = application.getSharedPreferences("wp_options", MODE_PRIVATE)
            sp.edit().putBoolean("post_use_web_view", checked).apply()
            if(checked) {
                tv_use_webview.text = "使用系统Webview查看文章"
            } else {
                tv_use_webview.text = "使用App内置文章查看器"
            }
        }
    }

    private fun getCacheSize() {
        launch {
            val cacheSize = application.cacheDir.size()
            val fileSize = application.filesDir.size()
            totalSize = cacheSize + fileSize
            val s = totalSize.formatMemorySize()

            withContext(Dispatchers.Main) {
                tv_cache_size.text = s
            }

        }
    }

    fun File.size(): Long {
        var size = 0L
        try {
            if (isFile) {
                size += length()
            }
            if (isDirectory) {
                listFiles().forEach {
                    size += it.size()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    fun File.suicide() {
        if (isFile) delete()
        if (isDirectory) listFiles().forEach { it.suicide() }
    }


    //show alert dialog
    fun Context.alert(message: String, confirmText: String, cancelText: String, onConfirm: () -> Unit) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setNegativeButton(cancelText) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(confirmText) { dialog, _ ->
                dialog.dismiss()
                onConfirm()
            }.show()
    }

    fun Long.formatMemorySize(): String {
        val kiloByte = this / 1024.toDouble()

        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            return kiloByte.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "K"
        }

        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            return megaByte.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "M"
        }

        val teraBytes = megaByte / 1024
        if (teraBytes < 1) {
            return megaByte.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "G"
        }

        return teraBytes.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "T"
    }

}
