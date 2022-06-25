package ru.korolenkoe.news.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.NotNull
import ru.korolenkoe.news.R
import ru.korolenkoe.news.utils.ClickCategoryInterface

class DownloadAdapter(
    _downloadsList: Array<String?>?,
    _context: Context,
    _categoryClick: ClickCategoryInterface?
) : RecyclerView.Adapter<DownloadAdapter.ViewHolder>() {

    private var downloadNewsArrayList: Array<String?>? = _downloadsList
    var context: Context = _context
    private var clickCategory = _categoryClick

    @NotNull
    override fun onCreateViewHolder(@NotNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_download_news, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryModel: String? = downloadNewsArrayList?.get(position)
        holder.title.text = categoryModel
        holder.itemView.setOnClickListener {
            clickCategory?.onClickCategory(position)
            holder.itemView.setBackgroundResource(R.color.cardview_dark_background)
            notifyItemChanged(position)


        }
        holder.itemView.setBackgroundResource(R.color.white)
    }

    override fun getItemCount(): Int {
        return downloadNewsArrayList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.id_title)
    }
}