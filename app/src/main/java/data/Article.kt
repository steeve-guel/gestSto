package data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article (
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val nom : String,
    val categorie: String,
    val quantite: Int,
    val seuil: Int
)