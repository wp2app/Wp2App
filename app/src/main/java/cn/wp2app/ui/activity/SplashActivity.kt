package cn.wp2app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import cn.wp2app.MainActivity
import cn.wp2app.R
import cn.wp2app.app.WPApp
import cn.wp2app.base.BaseActivity
import cn.wp2app.config.Constant
import cn.wp2app.config.Constant.SPLASH_TIME
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : BaseActivity() {
    lateinit var mCount :CountDownTimer
    lateinit var url:String

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        /*
        val decorView = window.decorView
        val uiOptions =  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        decorView.systemUiVisibility = uiOptions
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        */
    }


    companion object {

    }

    override fun getLayoutResId() = R.layout.activity_splash
    override fun initData() {
        tv_splash_skip.setOnClickListener {
            mCount.cancel()

            startMain()
        }
        mCount = object : CountDownTimer(SPLASH_TIME,1000){

            override fun onTick(millisUntilFinished: Long) {
                tv_splash_skip.text = "${millisUntilFinished / 1000 + 1}秒后跳过"
                //Timber.i(javaClass.name+"onTick()当前毫秒值："+millisUntilFinished)
            }

            override fun onFinish() {
                tv_splash_skip.text = "0秒后跳过"
                mCount.cancel()

                //Timber.i(javaClass.name+"onFinish()")
                startMain()
            }
        }
        mCount.start()

        var sp = this.getSharedPreferences("wp_options", MODE_PRIVATE)
        url =sp.getString("base_url", "") as String

    }

    override fun initView() {
        supportActionBar?.hide()

        splash_layout.setOnClickListener {  }

    }

    override fun onBackPressed() {}

    fun startMain() {
        //判断跳转Main，还是网址输入
        if( url.isNullOrEmpty() ) {
            WPApp.base_url = "https://www.wp2app.cn/"
            startActivity(Intent(this, BaseUrlActivity::class.java))
            finish()
        } else {
            if( !(url.endsWith("/"))) url += "/"
            if( !(url.startsWith("https")) || !(url.startsWith("https")) ) url = "https://" + url
            WPApp.base_url = url
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }
}
