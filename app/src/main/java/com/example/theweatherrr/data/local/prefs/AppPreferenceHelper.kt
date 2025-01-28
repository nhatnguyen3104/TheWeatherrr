package com.example.theweatherrr.data.local.prefs


class AppPreferenceHelper {

    companion object {
        private var instance: AppPreferenceHelper? = null

        /**
         * Get instance of the AppPreferenceHelper
         * This is a singleton pattern
         * @return
         */
        fun getInstance(): AppPreferenceHelper {
            if (instance == null) instance = AppPreferenceHelper()
            return instance!!
        }
    }
}