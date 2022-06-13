package ru.korolenkoe.news.dao

import androidx.room.*
import ru.korolenkoe.news.model.UserModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(userModel: UserModel)

    @Query("SELECT * FROM users WHERE login = :login")
    fun getUserByLogin(login: String): UserModel

    @Query("SELECT * from users")
    fun readAllData(): List<UserModel>

    @Update
    fun updateUser(userModel: UserModel)
}