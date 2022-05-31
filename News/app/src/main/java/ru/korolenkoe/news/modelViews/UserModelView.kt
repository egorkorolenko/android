package ru.korolenkoe.news.modelViews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.korolenkoe.news.db.UserDatabase
import ru.korolenkoe.news.model.UserModel
import ru.korolenkoe.news.repository.UserRepository

//class UserModelView(application: Application):AndroidViewModel(application) {
class UserModelView(private val repository: UserRepository):ViewModel(){

    private val readAllData : List<UserModel> = repository.readAllData

    fun insertUser(userModel: UserModel) = viewModelScope.launch{
        repository.insertUser(userModel)
    }

    fun getUserByLogin(login:String){
        repository.getUserByLogin(login)
    }

    fun getAllUsers(): List<UserModel>{
        return repository.readAllData
    }
}