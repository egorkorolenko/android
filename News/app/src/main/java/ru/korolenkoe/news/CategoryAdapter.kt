package ru.korolenkoe.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.jetbrains.annotations.NotNull

class CategoryAdapter(
    _categoryList: ArrayList<CategoryModel>,
    _context:Context,
    _categoryClick: ClickCategoryInterface?
): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var articlesArrayList: ArrayList<CategoryModel> = _categoryList
    var context: Context = _context
    private var clickCategory = _categoryClick

    @NotNull
    override fun onCreateViewHolder(@NotNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.category_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryModel:CategoryModel = articlesArrayList[position]
        holder.category.text = categoryModel.category
        Picasso.get().load(categoryModel.categoryImageUrl).into(holder.categoryImage)
        holder.itemView.setOnClickListener {
            clickCategory?.onClickCategory(position)
        }
    }

    override fun getItemCount(): Int {
        return articlesArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       var category: TextView = itemView.findViewById(R.id.text_view_category)
       var categoryImage: ImageView= itemView.findViewById(R.id.image_view_category)
    }
}