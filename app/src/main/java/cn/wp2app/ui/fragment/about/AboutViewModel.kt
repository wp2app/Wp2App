package cn.wp2app.ui.fragment.about

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.wp2app.base.BaseViewModel
import cn.wp2app.db.entities.User
import cn.wp2app.model.repository.UserRepository
import com.fasterxml.jackson.databind.node.BooleanNode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AboutViewModel(var repository:UserRepository) : BaseViewModel() {
    var user:MutableLiveData<List<User>> = MutableLiveData()
    val doDelete:MutableLiveData<Boolean> =MutableLiveData<Boolean>(false)

    fun getUser(ctx:Context) {
        viewModelScope.launch(Dispatchers.IO) {
           val a = repository.getUsers()
            user.postValue(a)
        }
    }

    fun delUser(u:User) {
        viewModelScope.launch(Dispatchers.IO) {
            doDelete.postValue(true)
            repository.deleteUser(u)
            doDelete.postValue(false)
        }

    }

}
