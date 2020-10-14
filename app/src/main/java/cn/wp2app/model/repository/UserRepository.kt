package cn.wp2app.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import cn.wp2app.db.AppDataBase
import cn.wp2app.db.entities.User

class UserRepository(ctx: Context) {
    val dao = AppDataBase.getInstance(ctx).userDao()

    suspend fun getUsers() : List<User> {
       return dao.getAllUsers()
    }

    fun addUser(user:User) {
        dao.insertUser(user)
    }

    fun deleteUser(user:User) {
        dao.deleteUser(user)
    }

    suspend fun TruncateTable() {
        dao.truncate()
    }
}