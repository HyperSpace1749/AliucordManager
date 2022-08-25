/*
 * Copyright (c) 2022 Juby210 & zt
 * Licensed under the Open Software License version 3.0
 */

package com.aliucord.manager.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.aliucord.manager.R

enum class DownloadMethod { DOWNLOAD, SELECT }

@Composable
fun InstallerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (DownloadMethod) -> Unit
) {
    var selectedMethod by rememberSaveable { mutableStateOf(DownloadMethod.DOWNLOAD) }

    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = stringResource(R.string.download_method)
            )
        },
        title = {
            Text(stringResource(R.string.download_method))
        },
        text = {
            Column {
                mapOf(
                    DownloadMethod.DOWNLOAD to stringResource(R.string.download_apk),
                    // DownloadMethod.SELECT to stringResource(R.string.select_apk)
                ).forEach { (method, name) ->
                    Column {
                        Row(
                            modifier = Modifier.clickable { selectedMethod = method },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = name,
                                style = MaterialTheme.typography.labelLarge
                            )

                            Spacer(Modifier.weight(1f, true))

                            RadioButton(
                                selected = selectedMethod == method,
                                onClick = { selectedMethod = method }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(selectedMethod)
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(stringResource(R.string.dismiss))
            }
        },
        onDismissRequest = onDismissRequest
    )
}