package cn.wp2app.ui.fragment.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.wp2app.adapter.category.CategoriesWithParent
import cn.wp2app.adapter.category.CategorySection
import cn.wp2app.app.WPApp
import cn.wp2app.base.BaseViewModel
import cn.wp2app.model.api.WPClient
import cn.wp2app.model.bean.category.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel : BaseViewModel() {
    var categories : MutableLiveData<MutableList<CategorySection>> = MutableLiveData()
    var category : MutableLiveData<Category> = MutableLiveData()
    private val service = WPClient(WPApp.base_url).service

    fun getCategoriesByPrent(id: Int) {
        viewModelScope.launch{
            try {
                val list = service.getCategoriesByParent(id)

                list?.let {
                    val l:MutableList<CategorySection> = mutableListOf()
                    for(  i:Category in it) {
                        var sc:CategorySection = CategorySection(false, i)
                        /*
                        if( i.parent > 0) {

                            sc = CategorySection(false, i)
                        } else {
                            sc = CategorySection(true, i)
                        }
                        */
                        l.add(sc)
                    }
                    categories.postValue(l)
                }
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getCategoryById(id: Int) {
        viewModelScope.launch() {
            try {
                val result = service.getCategoryByID(id);

                if (result.isNotEmpty() ) category.value = result[0]
            } catch (e:Exception) {
                e.printStackTrace()
            }

        }
    }
}
