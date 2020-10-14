package cn.wp2app.config

object Constant {
    //var POST_FILTER = "id,slug,date,modified,link,categories,title.rendered,content.rendered,excerpt.rendered"
    const val POST_FILTER:String= "id,slug,date,modified,link,categories,title.rendered,content.rendered,excerpt.rendered,extras_post_info"

    const val HOME_TOP_BAT_FILE:String="home_top_bar.dat"

    const val SPLASH_TIME:Long = 1000

    const val DEFAULT_BASE_URL = "https://www.wp2app.cn/"
}