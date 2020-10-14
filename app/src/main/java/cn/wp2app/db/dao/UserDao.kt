package cn.wp2app.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cn.wp2app.db.entities.User

@Dao
interface UserDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User):Long

    @Update
    fun updateUser(user:User)

    @Query("SELECT * FROM wp_user")
    fun getAllUsers(): List<User>

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM wp_user")
    suspend fun truncate ()
}