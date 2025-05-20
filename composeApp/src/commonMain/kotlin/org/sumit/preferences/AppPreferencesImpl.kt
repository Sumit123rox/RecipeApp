package org.sumit.preferences

import com.russhwolf.settings.Settings

class AppPreferencesImpl(private val settingsFactory: MultiplatformSettingsFactory) :
    AppPreferences {

    private val settings: Settings by lazy { settingsFactory.getSettings() }

    override fun clear() {
        settings.clear()
    }

    override fun getInt(key: String, defaultValue: Int): Int =
        settings.getInt(key, defaultValue)

    override fun getIntOrNull(key: String): Int? =
        settings.getIntOrNull(key)

    override fun getString(key: String, defaultValue: String): String =
        settings.getString(key, defaultValue)

    override fun getStringOrNull(key: String): String? =
        settings.getStringOrNull(key)

    override fun getLong(key: String, defaultValue: Long): Long =
        settings.getLong(key, defaultValue)

    override fun getLongOrNull(key: String): Long? =
        settings.getLongOrNull(key)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        settings.getBoolean(key, defaultValue)

    override fun getBooleanOrNull(key: String): Boolean? =
        settings.getBooleanOrNull(key)

    override fun putInt(key: String, value: Int) =
        settings.putInt(key, value)

    override fun putString(key: String, value: String) =
        settings.putString(key, value)

    override fun putLong(key: String, value: Long) =
        settings.putLong(key, value)

    override fun putBoolean(key: String, value: Boolean) =
        settings.putBoolean(key, value)

    override fun hasKey(key: String): Boolean =
        settings.hasKey(key)

    override fun remove(key: String) =
        settings.remove(key)

}