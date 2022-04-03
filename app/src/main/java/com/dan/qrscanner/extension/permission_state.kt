package com.dan.qrscanner.extension

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@ExperimentalPermissionsApi
val PermissionState.isPermanentlyDeclined: Boolean
    get() = !hasPermission && !shouldShowRationale