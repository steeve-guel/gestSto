package com.snbl.geststo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import data.Article
import data.ArticleAdapter

class DetailArticle : AppCompatActivity(), View.OnClickListener {

    private var article: Article? = null
    lateinit var textViewNom: TextView
    lateinit var textViewCategorie: TextView
    lateinit var textViewQuantite: TextView
    lateinit var textViewSeuil : TextView
    lateinit var rLayout: RelativeLayout
    lateinit var radioGroup: RadioGroup

    var articleIndex = -1

    companion object{
        var listCategorie = listOf("Produits frais","Produits laitiers","Boissons","Fruits","Légumes")
    }

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

        textViewNom = findViewById(R.id.nom)
        textViewCategorie = findViewById(R.id.categorie)
        //
        textViewCategorie.setOnClickListener(this)
        textViewQuantite = findViewById(R.id.quantite)
        textViewSeuil = findViewById(R.id.seuil)
        //
        radioGroup = findViewById(R.id.radioGroup)
        rLayout = findViewById(R.id.layout)

        article?.let {
            textViewNom.text = it.nom
            textViewCategorie.text = it.categorie // listcategorie[it.categorie]
            textViewQuantite.text = it.quantite.toString()
            textViewSeuil.text = it.seuil.toString()
            
            // Appliquer la couleur de fond
            val color = ArticleAdapter.color[it.id % ArticleAdapter.color.size]
            rLayout.setBackgroundColor(color)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_save -> {
                Toast.makeText(this,"icone save clicke",Toast.LENGTH_SHORT).show()
                return true
            } else -> return super.onOptionsItemSelected(item)

        }
    }

    override fun onClick(v: View?) {
        if(v == textViewCategorie){
            Toast.makeText(this,"icone categorie clicke",Toast.LENGTH_SHORT).show()
            radioGroup.visibility=View.VISIBLE
        }
    }

}