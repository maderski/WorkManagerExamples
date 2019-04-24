package io.maderski.workmanagerexamples.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {
    private val requiredPermissions = arrayListOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    fun isPermissionGranted(context: Context, permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    fun checkPermission(activity: Activity, permission: String) {
        val hasPermission = isPermissionGranted(activity, permission)
        if (!hasPermission) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission),
                PackageManager.PERMISSION_GRANTED)
        }
    }

    fun checkAllRequiredPermissions(activity: Activity) {
        requiredPermissions.forEach {permission ->
            checkPermission(activity, permission)
        }
    }
}