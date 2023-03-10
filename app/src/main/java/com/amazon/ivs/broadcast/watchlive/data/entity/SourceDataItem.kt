package com.amazon.ivs.broadcast.watchlive.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amazon.ivs.broadcast.watchlive.common.Configuration

@Entity(tableName = "source_table")
data class SourceDataItem(
    @PrimaryKey
    val url: String,
    val title: String = "",
    val isDefault: Boolean = false
) {
    fun isDefaultOption(): Boolean = (title == Configuration.LANDSCAPE_OPTION || title == Configuration.PORTRAIT_OPTION)
}
