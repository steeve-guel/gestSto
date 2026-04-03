package com.snbl.geststo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import data.AppDatabase
import data.Article
import data.ArticleAdapter
import kotlinx.coroutines.launch

class ArticlesListe : AppCompatActivity(), View.OnClickListener {

    private var articles: List<Article> = listOf()
    private lateinit var adaptar: ArticleAdapter
    private var badgeCount: TextView? = null

    private val detailArticleLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            loadArticles()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.liste_articles)
        
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.liste_notes_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        adaptar = ArticleAdapter(articles, this)
        recyclerView.adapter = adaptar

        val fab = findViewById<FloatingActionButton>(R.id.creat_article_fab)
        fab.setOnClickListener(this)

        loadArticles()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu.findItem(R.id.action_notification)
        val actionView = menuItem.actionView
        badgeCount = actionView?.findViewById(R.id.badge_count)
        updateBadge()
        return true
    }

    private fun loadArticles() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@ArticlesListe)
            articles = db.articleDao().getAll()
            adaptar = ArticleAdapter(articles, this@ArticlesListe)
            val recyclerView = findViewById<RecyclerView>(R.id.liste_notes_recycler_view)
            recyclerView.adapter = adaptar
            updateBadge()
        }
    }

    private fun updateBadge() {
        val count = articles.count { it.quantite <= it.seuil }
        badgeCount?.let {
            if (count > 0) {
                it.text = count.toString()
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
        }
    }

    private fun createArticle() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_add_article, null)
        
        val etNom = dialogView.findViewById<EditText>(R.id.etNom)
        val etCategorie = dialogView.findViewById<EditText>(R.id.etCategorie)
        val etQuantite = dialogView.findViewById<EditText>(R.id.etQuantite)
        val etSeuil = dialogView.findViewById<EditText>(R.id.etSeuil)

        builder.setView(dialogView)
            .setTitle("Nouvel Article")
            .setPositiveButton("Enregistrer") { _, _ ->
                val nom = etNom.text.toString()
                val categorie = etCategorie.text.toString()
                val quantite = etQuantite.text.toString().toIntOrNull() ?: 0
                val seuil = etSeuil.text.toString().toIntOrNull() ?: 0

                if (nom.isNotEmpty()) {
                    val article = Article(nom = nom, categorie = categorie, quantite = quantite, seuil = seuil)
                    lifecycleScope.launch {
                        AppDatabase.getDatabase(this@ArticlesListe).articleDao().insert(article)
                        loadArticles()
                    }
                }
            }
            .setNegativeButton("Annuler", null)
            .create()
            .show()
    }

    override fun onClick(v: View?) {
        if (v?.tag != null) {
            val index = v.tag as Int
            val article = articles[index]
            val intent = Intent(this, DetailArticle::class.java)
            intent.putExtra("article", article)
            intent.putExtra("articleindex", index)
            detailArticleLauncher.launch(intent)
        } else {
            when(v?.id) {
                R.id.creat_article_fab -> {
                    createArticle()
                }
            }
        }
    }
}