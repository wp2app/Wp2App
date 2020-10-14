package cn.wp2app.ui.fragment.posts

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.wp2app.app.WPApp
import cn.wp2app.base.BaseViewModel
import cn.wp2app.config.Constant
import cn.wp2app.entity.HomeBarUnit
import cn.wp2app.model.api.WPClient
import cn.wp2app.model.bean.PostInfo
import cn.wp2app.model.bean.category.Category
import cn.wp2app.model.bean.post.WPost
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PostsViewModel : BaseViewModel() {
    var posts: MutableLiveData<List<WPost>> = MutableLiveData()
    var postsAfter: MutableLiveData<List<WPost>> = MutableLiveData()
    private val service = WPClient(WPApp.base_url).service

    fun getPost() {
        viewModelScope.launch() {
            try {
                val result = service.getPosts()
                posts.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()

                // 空集合
                posts.postValue(mutableListOf())
            }
        }
    }

    fun getPostBefore(date:String) {
        viewModelScope.launch() {
            try {
                val result =service.getPostsBefore(date)
                posts.postValue(result)
            } catch (e:Exception) {
                e.printStackTrace()

                posts.postValue(mutableListOf())
            }
        }
    }

    fun getPostAfter(date:String) {
        viewModelScope.launch() {
            try {
                val result = service.getPostsAfter(date);
                postsAfter.postValue(result)
            } catch (e:Exception) {
                e.printStackTrace()
            }

        }
    }

    fun getPost(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = service.getPosts(id);
                posts.postValue(result)
            } catch (e:Exception) {
                e.printStackTrace()

                posts.postValue(mutableListOf())
            }

        }
    }

    fun getPostBefore(id:Int, date:String) {
        viewModelScope.launch() {
            try {
                val result = service.getPostsBefore(id,date);
                posts.postValue(result)
            } catch (e:Exception) {
                e.printStackTrace()

                posts.postValue(mutableListOf())
            }

        }
    }

    fun getPostAfter(id:Int, date:String) {
        viewModelScope.launch() {
            try {
                val result = service.getPostsAfter(id, date);
                postsAfter.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()

                posts.postValue(mutableListOf())
            }

        }
    }
}