package cn.wp2app.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import cn.wp2app.model.repository.UserRepository
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject
import java.io.File
import java.util.*


public fun getTimeISO8601() : String {
    var formatter :java.text.SimpleDateFormat = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA)
    formatter.timeZone = TimeZone.getTimeZone("GMT+8")

    val curDate = Date(System.currentTimeMillis())
    return formatter.format(curDate)
}

fun getImgUrlFromString(str:String) : String? {
    //val pattern = """/<img.*?(?: |\\t|\\r|\\n)?src=[\'"]?(.+?)[\'"]?(?:(?: |\\t|\\r|\\n)+.*?)?>/sim"""
    //    Regex(pattern).findAll(source).toList().flatMap(MatchResult::groupValues).forEach(::println)
   // val r = Regex(pattern).findAll(str)
    //val s = r.toString()
    if( str.isEmpty() || str.isBlank() ) return ""
    val p1 = str.indexOf("<img")
    if( p1 == -1 ) return ""
    val p2 = str.indexOf("src", p1)
    if(p2 == -1 ) return ""
    val p3 = str.indexOf("\"", p2)
    if(p3 == -1 ) return ""
    val p4 = str.indexOf("\"", p3+1)
    if(p4 == -1 ) return ""
    if( p4 <= p3 ) return  ""

    return str.substring(p3+1, p4)
}

fun hideKeyboard(context: Activity) {
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    // 隐藏软键盘
    imm.hideSoftInputFromWindow(context.window.decorView.windowToken, 0)
}
