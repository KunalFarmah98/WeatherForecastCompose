package com.kunalfarmah.apps.weatherforcastcompose.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity("FavoritesTable")
data class Favorite(
    @PrimaryKey
    @Nonnull
    @ColumnInfo("city")
    val city: String,
    @ColumnInfo("country")
    val country: String
)