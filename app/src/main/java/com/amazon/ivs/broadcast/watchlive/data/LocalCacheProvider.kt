package com.amazon.ivs.broadcast.watchlive.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amazon.ivs.broadcast.watchlive.data.entity.SourceDataItem

@Database(entities = [SourceDataItem::class], version = 1, exportSchema = false)
abstract class LocalCacheProvider : RoomDatabase() {

    abstract fun sourcesDao(): PlayerSourceDao

}
