package data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.snbl.geststo.R

class ArticleAdapter (
    val articles: List<Article>,
    val itemClickListener: View.OnClickListener
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    companion object {
        var color0 = android.graphics.Color.rgb(211,248,226)
        var color1 = android.graphics.Color.rgb(228,193,249)
        var color2 = android.graphics.Color.rgb(246,148,193)
        var color3 = android.graphics.Color.rgb(237,231,177)
        var color4 = android.graphics.Color.rgb(71,152,41)
        var color5 = android.graphics.Color.rgb(253,225,0)
        var color6 = android.graphics.Color.rgb(240,240,240)
        var color = arrayOf(color0,color1,color2,color3,color4,color5,color6)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView1 = itemView.findViewById<CardView>(R.id.card_view1)
        val nom1 = itemView.findViewById<TextView>(R.id.nom1)
        val categorie1 = itemView.findViewById<TextView>(R.id.categorie1)
        val quantite1 = itemView.findViewById<TextView>(R.id.quantite1)
        val seuil1 = itemView.findViewById<TextView>(R.id.seuil1)
        val layout1 = itemView.findViewById<RelativeLayout>(R.id.layout1)

        val cardView2 = itemView.findViewById<CardView>(R.id.card_view2)
        val nom2 = itemView.findViewById<TextView>(R.id.nom2)
        val categorie2 = itemView.findViewById<TextView>(R.id.categorie2)
        val quantite2 = itemView.findViewById<TextView>(R.id.quantite2)
        val seuil2 = itemView.findViewById<TextView>(R.id.seuil2)
        val layout2 = itemView.findViewById<RelativeLayout>(R.id.layout2)

        val cardView3 = itemView.findViewById<CardView>(R.id.card_view3)
        val nom3 = itemView.findViewById<TextView>(R.id.nom3)
        val categorie3 = itemView.findViewById<TextView>(R.id.categorie3)
        val quantite3 = itemView.findViewById<TextView>(R.id.quantite3)
        val seuil3 = itemView.findViewById<TextView>(R.id.seuil3)
        val layout3 = itemView.findViewById<RelativeLayout>(R.id.layout3)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewItem = inflater.inflate(R.layout.item_view, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        val index1 = 3 * position
        val index2 = 3 * position + 1
        val index3 = 3 * position + 2

        // Card 1
        if(index1 < articles.size) {
            holder.cardView1.visibility = View.VISIBLE
            val article = articles[index1]
            holder.nom1.text = article.nom
            holder.categorie1.text = article.categorie
            holder.quantite1.text = article.quantite.toString()
            holder.seuil1.text = article.seuil.toString()
            holder.layout1.setBackgroundColor(color[index1 % color.size])
            holder.cardView1.setOnClickListener(itemClickListener)
            holder.cardView1.tag = index1
        } else {
            holder.cardView1.visibility = View.INVISIBLE
        }

        // Card 2
        if(index2 < articles.size) {
            holder.cardView2.visibility = View.VISIBLE
            val article = articles[index2]
            holder.nom2.text = article.nom
            holder.categorie2.text = article.categorie
            holder.quantite2.text = article.quantite.toString()
            holder.seuil2.text = article.seuil.toString()
            holder.layout2.setBackgroundColor(color[index2 % color.size])
            holder.cardView2.setOnClickListener(itemClickListener)
            holder.cardView2.tag = index2
        } else {
            holder.cardView2.visibility = View.INVISIBLE
        }

        // Card 3
        if(index3 < articles.size) {
            holder.cardView3.visibility = View.VISIBLE
            val article = articles[index3]
            holder.nom3.text = article.nom
            holder.categorie3.text = article.categorie
            holder.quantite3.text = article.quantite.toString()
            holder.seuil3.text = article.seuil.toString()
            holder.layout3.setBackgroundColor(color[index3 % color.size])
            holder.cardView3.setOnClickListener(itemClickListener)
            holder.cardView3.tag = index3
        } else {
            holder.cardView3.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return if(articles.isEmpty()) 0 else (articles.size + 2) / 3
    }
}