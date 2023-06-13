package com.vostrikov.mykinopoisk.data.sharedpreferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPref @Inject constructor(
    application: Application
) {

    private val sharedPref: SharedPreferences = application.getSharedPreferences(
        application.packageName,
        Context.MODE_PRIVATE)

    fun writeApiKey(apiKey: String){
        deleteApiKey()
        with (sharedPref.edit()) {
            putString(SP_KEY_API, apiKey).commit()
        }
    }

    fun writeGuestApiKey(){
        with (sharedPref.edit()) {
            putString(SP_KEY_API, SP_KEY_API_VALUE).commit()
        }
    }

    fun deleteApiKey(){
        sharedPref.edit().remove(SP_KEY_API).apply()
    }

    fun getApiKey(): String{
        return sharedPref.getString(SP_KEY_API, "") ?: ""
    }


    companion object{
        private const val SP_KEY_API = "KEY_API"

        //token for kinopoisk API connection
        private const val SP_KEY_API_VALUE = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"

        fun createInstance(application: Application): SharedPref{
            return SharedPref(application)
        }
    }
}

