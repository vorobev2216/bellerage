package com.example.test_bellerage.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceDataStore

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SecureTokenManager(private val context: Context){

    private val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPrefsFileName = "crypto_shared_prefs"

    private val sharedPrefs: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            sharedPrefsFileName,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun storeToken(token: Int) {
        with(sharedPrefs.edit()) {
            putInt("token", token)
            apply()
        }
    }

    fun retrieveToken(): Int {
        return sharedPrefs.getInt("token",0)
    }

    fun deleteToken() {
        with(sharedPrefs.edit()) {
            remove("token")
            apply()
        }
    }

    fun hasToken(): Boolean {
        return sharedPrefs.contains("token")
    }
}