package com.mainsteps.martiniconecta.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritoDao {

    @Query("SELECT * FROM favoritos ORDER BY titulo")
    fun observarFavoritos(): Flow<List<FavoritoEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favoritos WHERE id = :id)")
    suspend fun existe(id: String): Boolean

    @Upsert
    suspend fun guardar(favorito: FavoritoEntity)

    @Delete
    suspend fun quitar(favorito: FavoritoEntity)
}
