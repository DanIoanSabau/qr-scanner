package com.dan.qrscanner.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.dan.qrscanner.R
import com.dan.qrscanner.extension.isPermanentlyDeclined
import com.dan.qrscanner.presentation.component.Camera
import com.dan.qrscanner.presentation.theme.QRScannerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QRScannerTheme {
                val permissionState = rememberPermissionState(
                    permission = Manifest.permission.CAMERA
                )

                val lifeCycleOwner = LocalLifecycleOwner.current
                DisposableEffect(lifeCycleOwner) {
                    val observer = LifecycleEventObserver { _, event ->
                        if (Lifecycle.Event.ON_START == event) {
                            permissionState.launchPermissionRequest()
                        }
                    }

                    lifeCycleOwner.lifecycle.addObserver(observer)

                    onDispose {
                        lifeCycleOwner.lifecycle.removeObserver(observer)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    when {
                        permissionState.hasPermission -> {
                            val textPlaceHolder = stringResource(R.string.placeholder_qr_text)
                            var text by remember { mutableStateOf(textPlaceHolder) }

                            Camera { text = it }

                            Spacer(modifier = Modifier.height(64.dp))

                            Text(
                                text = text,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp)
                            )
                        }
                        permissionState.shouldShowRationale -> {
                            Text(
                                text = stringResource(R.string.info_camera_permission_denied),
                                textAlign = TextAlign.Center
                            )
                        }
                        permissionState.isPermanentlyDeclined -> {
                            Text(
                                text = stringResource(R.string.info_enable_camera_permission),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}