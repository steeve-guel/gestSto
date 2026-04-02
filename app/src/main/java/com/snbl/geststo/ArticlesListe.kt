package com.snbl.geststo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import data.AppDatabase
import data.Article
import data.ArticleAdapter
import kotlinx.coroutines.launch

class ArticlesListe : AppCompatActivity(), View.OnClickListener {

    lateinit var articles: List<Article>
    lateinit var adaptar: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.liste_articles)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = AppDatabase.getDatabase(this)
        val articleDao = db.articleDao()

        val recyclerView = findViewById<RecyclerView>(R.id.liste_notes_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            articles = articleDao.getAll()
            adaptar = ArticleAdapter(articles, this@ArticlesListe)
            recyclerView.adapter = adaptar
        }
    }

    override fun onClick(v: View?) {
        if (v?.tag != null) {
            val index = v.tag as Int
            val article = articles[index]
            val intent = Intent(this, DetailArticle::class.java)
            intent.putExtra("article", article)
            intent.putExtra("articleindex", index)
            startActivity(intent)
        }
    }
}