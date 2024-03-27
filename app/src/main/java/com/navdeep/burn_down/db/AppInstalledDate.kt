package com.navdeep.burn_down.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "app_installed_date")
data class AppInstalledDate(
    @ColumnInfo(name = "date") var date: String)

