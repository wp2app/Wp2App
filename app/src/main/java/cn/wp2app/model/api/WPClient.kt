package cn.wp2app.model.api

import cn.wp2app.app.WPApp
import cn.wp2app.config.Constant
import cn.wp2app.utils.NetWorkUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.io.File

class WPClient(url: String) : RetrofitClient() {

    var service = getService(ApiService::class.java, url + "wp-json/wp/v2/")
    var test = getService(ApiService::class.java, url + "wp-json/wp/v2/")

    //private val cookieJar by lazy { PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.CONTEXT)) }

    override fun handleBuilder(builder: OkHttpClient.Builder) {

        val httpCacheDirectory = File(WPApp.instance.cacheDir, "responses")
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)
        builder.cache(cache)
            //.cookieJar(cookieJar)
            .addInterceptor { chain ->
                var request = chain.request()
                if (!NetWorkUtils.isNetworkAvailable(WPApp.instance)) {
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                }
                val response = chain.proceed(request)
                if (!NetWorkUtils.isNetworkAvailable(WPApp.instance)) {
                    val maxAge = 60 * 60
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
                } else {
                    val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }
                response
            }
    }
}