package cn.wp2app.ui.fragment.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.wp2app.app.WPApp
import cn.wp2app.base.BaseViewModel
import cn.wp2app.model.api.WPClient
import cn.wp2app.model.bean.post.WPost
import cn.wp2app.model.bean.tag.WpTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.ext.getOrCreateScope
import retrofit2.Response

class SearchViewModel : BaseViewModel() {
    var wpTag : MutableLiveData<List<WpTag>>  = MutableLiveData()
    var posts: MutableLiveData<List<WPost>> = MutableLiveData()
    var status: MutableLiveData<Int> = MutableLiveData(0)
    private val service = WPClient(WPApp.base_url).service

    fun getTags() {
        viewModelScope.launch() {
            try {
                val result = service.getTags()
                if (result.isNullOrEmpty()) {

                } else {
                    wpTag.value  = result
                }
            } catch (e: Exception) {

            }
        }
    }

    fun search(key:String) {
        viewModelScope.launch() {
            try {
                posts.postValue(service.search(key))
            } catch (e : Exception) {

            }

        }
    }

    fun getPostsByTag(id:Int) {
        viewModelScope.launch() {
            try {
                posts.postValue(service.getPostByTag(id))
            } catch (e: Exception) {

            }

        }
    }

    fun testTag() : Unit {
        viewModelScope.launch() {
            status.value = 0
            try {
                status.value = 1
                val result = WPClient(WPApp.test_url).test.getTagsForTest()
                wpTag.postValue(result)
                status.value = 20
            } catch (e: Exception) {
                status.value = 100
            }

        }
    }
}
