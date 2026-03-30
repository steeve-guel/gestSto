package data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    suspend fun getAll(): List<Article>

    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getById(id: Int): Article

    @Insert
    suspend fun insert(article: Article)

    @Query("DELETE FROM articles WHERE id = :id")
    suspend fun deleteId(id: Int)

    @Delete
    suspend fun delete(article: Article)
}