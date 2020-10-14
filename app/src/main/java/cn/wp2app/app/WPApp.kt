package cn.wp2app.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import cn.wp2app.inject.appModule
import cn.wp2app.widget.CustomLoadMoreView
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

class WPApp : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context

        var base_url = "https://www.wp2app.cn/"
        var test_url = "https://www.wp2app.cn/"
    }

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext

        startKoin {

            androidContext(this@WPApp)
            androidFileProperties()

            modules(appModule)
        }

    }


}