package org.sumit.preferences

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

actual class MultiplatformSettingsFactory {
    actual fun getSettings(): Settings {
        val delegate = Preferences.systemRoot()
        return PreferencesSettings(delegate)
    }
}