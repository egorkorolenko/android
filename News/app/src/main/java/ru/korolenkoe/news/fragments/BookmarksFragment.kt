package ru.korolenkoe.news.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.korolenkoe.news.R
import ru.korolenkoe.news.adapter.NewsAdapter
import ru.korolenkoe.news.model.Articles
import ru.korolenkoe.news.model.UserModel

class BookmarksFragment(var userModel: UserModel) : Fragment() {

    private var articlesArrayList: ArrayList<Articles> = arrayListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var toolBar: Toolbar
    private lateinit var recyclerViewNews: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchView: SearchView
    private lateinit var q: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        articlesArrayList = userModel.bookMarks as ArrayList<Articles>


        return inflater.inflate(R.layout.bookmarks_fragment, container, false)
    }



}