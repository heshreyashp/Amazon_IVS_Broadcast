package com.amazon.ivs.broadcast.injection

import androidx.room.Room
import com.amazon.ivs.broadcast.App
import com.amazon.ivs.broadcast.cache.PREFERENCES_NAME
import com.amazon.ivs.broadcast.cache.PreferenceProvider
import com.amazon.ivs.broadcast.cache.SecuredPreferenceProvider
import com.amazon.ivs.broadcast.common.broadcast.BroadcastManager
import com.amazon.ivs.broadcast.watchlive.data.LocalCacheProvider
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class InjectionModule(private val context: App) {

    @Singleton
    @Provides
    fun providePreferences() = PreferenceProvider(context, PREFERENCES_NAME)

    @Singleton
    @Provides
    fun provideSecuredPreferences() = SecuredPreferenceProvider(context)

    @Singleton
    @Provides
    fun provideBroadcastManager() = BroadcastManager(context)

    @Provides
    @Singleton
    fun provideLocalCacheProvider(): LocalCacheProvider
            = Room.databaseBuilder(context, LocalCacheProvider::class.java, "twitch_database").build()
}
