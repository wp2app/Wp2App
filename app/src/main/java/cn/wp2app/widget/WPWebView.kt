package cn.wp2app.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class WPWebView  : WebView {
    @SuppressLint("SetJavaScriptEnabled")
    constructor(arg0: Context?, arg1: AttributeSet?) : super(
        arg0,
        arg1
    ) {
        /**
         * 防止加载网页时调起系统浏览器
         */
        val client: WebViewClient = object : WebViewClient() {
            /**
             * 防止加载网页时调起系统浏览器
             */
           override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        this.setWebViewClient(client)
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings()
        this.setClickable(true)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebViewSettings() {
        val webSetting: WebSettings = this.getSettings()
        webSetting.setJavaScriptEnabled(true)
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true)
        webSetting.setAllowFileAccess(true)
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS)
        webSetting.setSupportZoom(true)
        webSetting.setBuiltInZoomControls(true)
        webSetting.setUseWideViewPort(true)
        webSetting.setSupportMultipleWindows(true)
        webSetting.setLoadWithOverviewMode(true)
        webSetting.setAppCacheEnabled(true)
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true)
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE)
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND)
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE)

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }

    //	@Override
    //	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
    //		boolean ret = super.drawChild(canvas, child, drawingTime);
    //		canvas.save();
    //		Paint paint = new Paint();
    //		paint.setColor(0x7fff0000);
    //		paint.setTextSize(24.f);
    //		paint.setAntiAlias(true);
    //		if (getX5WebViewExtension() != null) {
    //			canvas.drawText(this.getContext().getPackageName() + "-pid:"
    //					+ android.os.Process.myPid(), 10, 50, paint);
    //			canvas.drawText(
    //					"X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
    //					100, paint);
    //		} else {
    //			canvas.drawText(this.getContext().getPackageName() + "-pid:"
    //					+ android.os.Process.myPid(), 10, 50, paint);
    //			canvas.drawText("Sys Core", 10, 100, paint);
    //		}
    //		canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
    //		canvas.drawText(Build.MODEL, 10, 200, paint);
    //		canvas.restore();
    //		return ret;
    //	}
    constructor(arg0: Context?) : super(arg0) {
        setBackgroundColor(85621)
    }
}