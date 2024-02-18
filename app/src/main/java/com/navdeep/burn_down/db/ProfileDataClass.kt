package com.navdeep.burn_down.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.navdeep.burn_down.model.BaseResponse

@Entity(tableName = "profile")
data class ProfileDataClass(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "userName") var userName: String,
    @ColumnInfo(name = "weight") var weight: String,
    @ColumnInfo(name = "height") var height: String)
