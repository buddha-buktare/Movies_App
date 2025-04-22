package me.buddha.moviesapp.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_remote_keys"
)
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nextPage: Int?,
    val lastUpdated: Long
)