package cn.wp2app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import cn.wp2app.db.dao.UserDao
import cn.wp2app.db.entities.User

@Database(entities = [User::class],version = 1,exportSchema = false)
//@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {
    // 得到UserDao
    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var instance:AppDataBase? = null

        fun getInstance(context: Context):AppDataBase{
            return instance?: synchronized(this){
                instance?:buildDataBase(context)
                    .also {
                        instance = it
                    }
            }
        }

        private fun buildDataBase(context: Context):AppDataBase{
            return Room
                .databaseBuilder(context,AppDataBase::class.java,"wp2app_db_v4")
                .addCallback(object : RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        /*val request = OneTimeWorkRequestBuilder<ShoeWorker>().build()
                        WorkManager.getInstance().enqueue(request)*/
                    }
                })
                .build()
        }
    }
}