package ru.korolenkoe.news.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.core.net.toUri
import de.hdodenhof.circleimageview.CircleImageView
import ru.korolenkoe.news.R
import ru.korolenkoe.news.db.UserDatabase
import ru.korolenkoe.news.model.UserModel
import ru.korolenkoe.news.repository.UserRepository


class ProfileActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var imageProfile:CircleImageView
    private lateinit var okButton:Button
    private lateinit var userModel: UserModel

    private lateinit var database: UserDatabase
    private lateinit var repository: UserRepository

    private val GALLERY_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_fragment)

        editText = findViewById(R.id.userNameProfileSettings)
        editText.setTextColor(Color.WHITE)
        imageProfile = findViewById(R.id.profileImage)
        okButton = findViewById(R.id.profileSettingOkButton)


        database = UserDatabase.getDatabase(this.application)
        repository = UserRepository(database.userDao())
        val argument: Bundle? = intent.extras

        val login = argument?.get("login").toString()
        if (login != "null") {
            userModel = getUserByLogin(login)
//            imageProfile.setImageURI(userModel.image.toUri())
        }

        val userName: Editable? = editText.text

        imageProfile.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        okButton.setOnClickListener {
            if(userName.toString()!="")
            userModel.name = userName.toString()
            updateUser(userModel)
            Toast.makeText(this,"Готово",Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val selectedImage: Uri? = data?.data
        imageProfile.setImageURI(selectedImage)
        userModel.image = data?.data?.path.toString()
    }

    private fun getUserByLogin(login: String): UserModel {
        return repository.getUserByLogin(login)
    }

    private fun updateUser(userModel: UserModel){
        repository.updateUser(userModel)
    }
}