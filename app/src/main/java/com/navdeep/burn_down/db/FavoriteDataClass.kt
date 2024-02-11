package com.navdeep.burn_down.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.navdeep.burn_down.model.BaseResponse


@Entity(tableName = "add_favorite")
data class FavoriteDataClass(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "date") var completedDate: String,
    @ColumnInfo(name = "workout_fav_list") var yourModelList: List<BaseResponse>,
)