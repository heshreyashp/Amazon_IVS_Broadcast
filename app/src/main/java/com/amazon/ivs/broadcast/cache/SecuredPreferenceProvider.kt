package com.amazon.ivs.broadcast.cache

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.text.TextUtils
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.amazon.ivs.broadcast.App
import com.amazon.ivs.broadcast.BuildConfig
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

const val SECURED_PREFERENCES_NAME = "secured_preferences"

class SecuredPreferenceProvider(val context: App) {

    var serverUrl: String? =
        if (!TextUtils.isEmpty(stringPreference(BuildConfig.SERVER_URL).toString())) "rtmps://e469233ce600.global-contribute.live-video.net:443/app/" else ""

    //var playbackUrl: String? by stringPreference(BuildConfig.PLAYBACK_URL)
    var playbackUrl: String? =
        "https://www.ivs.rocks/live#https://e469233ce600.us-east-1.playback.live-video.net/api/video/v1/us-east-1.028785534216.channel.3n2OKmtbn5av.m3u8"
    var streamKey: String? = "sk_us-east-1_ZHdXgiWwdSBj_aMPnOd61beqzaLofTJcV5u4PtrgZET"

    private var spec = KeyGenParameterSpec.Builder(
        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
        .build()

    private var masterKey = MasterKey.Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()

    val preferences = EncryptedSharedPreferences.create(
        context,
        SECURED_PREFERENCES_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private fun stringPreference(defaultValue: String? = null) =
        object : ReadWriteProperty<Any?, String?> {

            override fun getValue(thisRef: Any?, property: KProperty<*>) = preferences.getString(
                property.name,
                defaultValue
            )

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
                preferences.edit().putString(property.name, value).apply()
            }
        }
}
