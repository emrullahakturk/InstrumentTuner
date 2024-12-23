package com.yargisoft.tuner.core.utils

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionUtils {

    // İzin verilmiş mi kontrol et
    fun isPermissionGranted(fragment: Fragment, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            fragment.requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    // İzin isteme
    fun requestPermission(
        launcher: ActivityResultLauncher<String>,
        permission: String
    ) {
        launcher.launch(permission)
    }
}
