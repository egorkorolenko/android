package ru.korolenkoe.news.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import ru.korolenkoe.news.R
import ru.korolenkoe.news.activity.OpenNews
import ru.korolenkoe.news.db.UserDatabase
import ru.korolenkoe.news.menuAction.AddIntoBookmarks
import ru.korolenkoe.news.menuAction.SendNews
import ru.korolenkoe.news.model.Articles
import ru.korolenkoe.news.model.UserModel


class NewsAdapter(
    _articlesArrayList: ArrayList<Articles>, _context: Context,
    var database: UserDatabase?, var userModel: UserModel?
) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var articlesArrayList: ArrayList<Articles> = _articlesArrayList
    private var context: Context = _context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animation: Animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.left_animation)
        val articles: Articles = articlesArrayList[position]
        holder.title.text = articles.title
        val time2 = articles.publishedAt?.toInstant()
            ?.plus(6, DateTimeUnit.HOUR, TimeZone.currentSystemDefault()).toString()
        holder.time.text = time2.replace('T', ' ').replace('Z', ' ')
        if (articles.urlToImage != "") {
            Picasso.get().load(articles.urlToImage).into(holder.newsImage)
        } else {
            Picasso.get().load("res/drawable-anydpi/group.png").into(holder.newsImage)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, OpenNews::class.java)
            intent.putExtra("title", articles.title)
            intent.putExtra("content", articles.content)
            intent.putExtra("desc", articles.description)
            intent.putExtra("image", articles.urlToImage)
            intent.putExtra("url", articles.url)
            intent.putExtra("publishedAt", articles.publishedAt)
            context.startActivity(intent)
        }
        holder.itemView.startAnimation(animation)

        holder.popupMenu.setOnClickListener {
            showPopupMenu(it, articles.url!!, context, position)
        }
    }

    private fun showPopupMenu(v: View, url: String, context: Context, position: Int) {

        val popupMenu = PopupMenu(context, v)
        popupMenu.inflate(R.menu.card_menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.send_news -> {
                    val sender = SendNews()
                    sender.sendNews(url, context)
                    true
                }
                R.id.addBookmarks -> {
                    val addIntoBookmarks = AddIntoBookmarks()
                    if (userModel?.login != "") {
                        addIntoBookmarks.addBookMark(
                            userModel!!,
                            database!!,
                            articlesArrayList[position]
                        )
                        Toast.makeText(
                            context,
                            "Успешно!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Вы не вошли в аккаунт!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    true
                }
                R.id.download -> {
                    Toast.makeText(
                        context,
                        "Вы выбрали PopupMenu 3",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    override fun getItemCount(): Int {
        return articlesArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.id_heading)
        var newsImage: ImageView = itemView.findViewById(R.id.id_imageview_news_card)
        var popupMenu: ImageView = itemView.findViewById(R.id.ib_popup_menu)
        var time: TextView = itemView.findViewById(R.id.id_time_published)
    }

    fun setUser(_userModel: UserModel?) {
        userModel = _userModel
    }

}