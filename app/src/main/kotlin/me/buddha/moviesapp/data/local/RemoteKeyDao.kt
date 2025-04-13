package me.buddha.moviesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.buddha.moviesapp.data.model.local.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<RemoteKey>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: RemoteKey)

    @Query("SELECT * from movie_remote_keys where id=:key")
    suspend fun getKeyByMovie(key: String): RemoteKey?

    @Query("delete from movie_remote_keys")
    suspend fun clearKeys()
}