package ru.korolenkoe.news.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.NotNull
import ru.korolenkoe.news.utils.ClickCategoryInterface
import ru.korolenkoe.news.R
import ru.korolenkoe.news.model.CategoryModel

class CategoryAdapterSetting(
    _categoryList: ArrayList<CategoryModel>,
    _context: Context,
    _categoryClick: ClickCategoryInterface?
) : RecyclerView.Adapter<CategoryAdapterSetting.ViewHolder>() {

    private var articlesArrayList: ArrayList<CategoryModel> = _categoryList
    var context: Context = _context
    private var clickCategory = _categoryClick

    @NotNull
    override fun onCreateViewHolder(@NotNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.category_card_settings, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryModel: CategoryModel = articlesArrayList[position]
        holder.category.text = categoryModel.category
        holder.itemView.setOnClickListener {
            clickCategory?.onClickCategory(position)
            holder.itemView.setBackgroundResource(R.color.cardview_dark_background)
            notifyItemChanged(position)
        }
        holder.itemView.setBackgroundResource(R.color.white)
    }

    override fun getItemCount(): Int {
        return articlesArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category: TextView = itemView.findViewById(R.id.text_view_category)
    }

}