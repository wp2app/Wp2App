package cn.wp2app.ui.fragment.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.wp2app.base.BaseViewModel
import cn.wp2app.config.Constant
import cn.wp2app.entity.HomeBarUnit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class HomeViewModel : BaseViewModel() {

    var topBar:MutableLiveData<MutableList<HomeBarUnit>> = MutableLiveData()

    fun getTopBarData(context: Context?) {
        viewModelScope.launch {
            var fn = context?.filesDir.toString() + "/options/"+ Constant.HOME_TOP_BAT_FILE

            var file = File(fn)
            if( file.exists() ) {
                var content = file.readText()
                if( content.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        topBar.value = Gson().fromJson(
                            content,
                            object : TypeToken<MutableList<HomeBarUnit>>() {}.type
                        )
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        topBar.value = mutableListOf()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    topBar.value = mutableListOf()
                }
            }
        }
    }

    fun delShowCategory(position:Int, ctx:Context) {
        viewModelScope.launch {
            topBar.value?.removeAt(position)

            val json = Gson().toJson(topBar.value)
            var fn = ctx.filesDir.toString() + "/options"
            var file = File(fn)
            if( !file.exists()) file.mkdirs()


            val f = File(fn+"/"+Constant.HOME_TOP_BAT_FILE)
            if( !f.exists() ) f.createNewFile()

            f.writeText(json)

        }
    }
}
