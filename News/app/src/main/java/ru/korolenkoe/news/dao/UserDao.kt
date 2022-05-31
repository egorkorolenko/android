package ru.korolenkoe.news.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.korolenkoe.news.model.UserModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(userModel: UserModel)

    @Query("SELECT * from users where login =login")
    fun getUserByLogin(login :String):UserModel

    @Query("SELECT * from users")
    fun readAllData():List<UserModel>
}