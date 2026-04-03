package com.snbl.geststo

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import data.AppDatabase
import data.Article
import data.ArticleAdapter
import kotlinx.coroutines.launch

class DetailArticle : AppCompatActivity() {

    private var article: Article? = null
    lateinit var editTextNom: EditText
    lateinit var textViewCategorie: TextView
    lateinit var editTextQuantite: EditText
    lateinit var editTextSeuil : EditText
    lateinit var rLayout: RelativeLayout

    var articleIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_article)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        article = IntentCompat.getParcelableExtra(intent, "article", Article::class.java)
        articleIndex = intent.getIntExtra("articleindex", -1)

        editTextNom = findViewById(R.id.nom)
        textViewCategorie = findViewById(R.id.categorie)
        editTextQuantite = findViewById(R.id.quantite)
        editTextSeuil = findViewById(R.id.seuil)
        rLayout = findViewById(R.id.layout)

        article?.let {
            editTextNom.setText(it.nom)
            textViewCategorie.text = it.categorie
            editTextQuantite.setText(it.quantite.toString())
            editTextSeuil.setText(it.seuil.toString())
            
            val color = ArticleAdapter.color[it.id % ArticleAdapter.color.size]
            rLayout.setBackgroundColor(color)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_save -> {
                saveArticle()
                true
            }
            R.id.action_delete -> {
                confirmDelete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun confirmDelete() {
        AlertDialog.Builder(this)
            .setTitle("Suppression")
            .setMessage("Voulez-vous vraiment supprimer cet article ?")
            .setPositiveButton("Supprimer") { _, _ ->
                deleteArticle()
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    private fun deleteArticle() {
        article?.let {
            lifecycleScope.launch {
                AppDatabase.getDatabase(this@DetailArticle).articleDao().delete(it)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun saveArticle() {
        val updatedArticle = Article(
            id = article?.id ?: 0,
            nom = editTextNom.text.toString(),
            categorie = textViewCategorie.text.toString(),
            quantite = editTextQuantite.text.toString().toIntOrNull() ?: 0,
            seuil = editTextSeuil.text.toString().toIntOrNull() ?: 0
        )

        lifecycleScope.launch {
            AppDatabase.getDatabase(this@DetailArticle).articleDao().update(updatedArticle)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}