package data

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(nom)
        dest.writeString(categorie)
        dest.writeInt(quantite)
        dest.writeInt(seuil)
    }

    companion object CREATOR : Parcelable.Creator<Article>{
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}