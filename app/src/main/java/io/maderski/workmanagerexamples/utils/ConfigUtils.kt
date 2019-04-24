package io.maderski.workmanagerexamples.utils

import android.content.res.Resources
import android.util.Log
import io.maderski.workmanagerexamples.R
import java.io.IOException
import java.util.*

/**
 * Reads value from a given key in a config file.
 *
 * config file: res/raw/config.properties
 * The config file should contain:
 *      some_key=12345
 */

object ConfigUtils {
    const val TAG = "ConfigUtils"
    const val SLACK_API_TOKEN_KEY = "slack_api_token"

    fun getValue(resources: Resources, key: String): String? {
        return try {
            val rawResource = resources.openRawResource(R.raw.config)
            val properties = Properties()
            properties.load(rawResource)
            properties.getProperty(key)
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Unable to find the config file: ${e.message} " +
                    "\nPlease create a res/raw/config.properties")
            return null
        } catch (e: IOException) {
            Log.e(TAG, "Failed to open config file.")
            null
        }
    }
}