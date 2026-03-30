package com.snbl.geststo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import data.AppDatabase
import data.Article
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = AppDatabase.getDatabase(this)
        val articleDao = db.articleDao()

        val etNom = findViewById<EditText>(R.id.etNom)
        val etCategorie = findViewById<EditText>(R.id.etCategorie)
        val etQuantite = findViewById<EditText>(R.id.etQuantite)
        val etSeuil = findViewById<EditText>(R.id.etSeuil)


        val btnInsert = findViewById<Button>(R.id.btnInsert)
        val btnList = findViewById<Button>(R.id.btnList)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnInsert.setOnClickListener {
            val nom = etNom.text.toString()
            val categorie = etCategorie.text.toString()
            val quantite = etQuantite.text.toString().toIntOrNull() ?: 0
            val seuil = etSeuil.text.toString().toIntOrNull() ?: 0
            if (nom.isNotEmpty()) {
                val article = Article(0, nom, categorie, quantite, seuil)
                lifecycleScope.launch {
                    articleDao.insert(article)
                    // Effacer les champs après insertion
                    etNom.text.clear()
                    etCategorie.text.clear()
                    etQuantite.text.clear()
                    etSeuil.text.clear()
                }
            }
        }

        btnList.setOnClickListener {
            lifecycleScope.launch {
                val articles = articleDao.getAll()
                val displayText = articles.joinToString("\n") { "${it.id}: ${it.nom} (${it.quantite})" }
                tvResult.text = displayText
            }
        }
    }
}