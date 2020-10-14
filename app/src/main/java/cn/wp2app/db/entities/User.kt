package cn.wp2app.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用户表
 */
@Entity(tableName = "wp_user")
data class User(var name:String, val pwd:String, val login_name:String, @PrimaryKey val user_id:Int = 0)