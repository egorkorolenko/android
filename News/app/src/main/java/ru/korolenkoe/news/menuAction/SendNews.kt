package ru.korolenkoe.news.menuAction

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

class SendNews : AppCompatActivity(){

    fun sendNews(url:String){
        val intent = Intent()
        val sendUri = Uri.parse(url)
        intent.action =  Intent.ACTION_SEND
        intent.type = "text/plain";
        intent.putExtra(Intent.EXTRA_TEXT,sendUri);
        startActivity(intent);
    }
}