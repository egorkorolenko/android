package ru.korolenkoe.news

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class NewsAdapter(_articlesArrayList: ArrayList<Articles>, _context:Context)
    : RecyclerView.Adapter<NewsAdapter.ViewHolder>(){

    private var articlesArrayList: ArrayList<Articles> = _articlesArrayList
    private var context: Context = _context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.news_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articles: Articles = articlesArrayList[position]
        holder.title.text = articles.title
        holder.time.text = articles.publishedAt?.replace('T',' ')?.replace('Z',' ')
        Picasso.get().load(articles.urlToImage).into(holder.newsImage)
        holder.itemView.setOnClickListener{
            val intent = Intent(context,OpenNews::class.java)
            intent.putExtra("title",articles.title)
            intent.putExtra("content",articles.content)
            intent.putExtra("desc",articles.description)
            intent.putExtra("image",articles.urlToImage)
            intent.putExtra("url",articles.url)
            intent.putExtra("publishedAt",articles.publishedAt)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return articlesArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.id_heading)
        var newsImage: ImageView = itemView.findViewById(R.id.id_imageview_news_card)
        var time:TextView = itemView.findViewById(R.id.id_time_published)
    }
}