/*
 * Copyright (c) 2022 Juby210 & zt
 * Licensed under the Open Software License version 3.0
 */


package com.aliucord.manager.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliucord.manager.ui.component.home.CommitList
import com.aliucord.manager.ui.component.home.InfoCard
import com.aliucord.manager.ui.dialog.InstallerDialog
import com.aliucord.manager.ui.viewmodel.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getViewModel(),
    onClickInstall: (InstallData) -> Unit
) {
    var showInstallerDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.preferences.packageName) {
        viewModel.fetchInstalledVersion()
    }

    if (showInstallerDialog) {
        InstallerDialog(
            onDismiss = { showInstallerDialog = false },
            onConfirm = { data ->
                showInstallerDialog = false
                onClickInstall(data)
            }
        )
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InfoCard(
            packageName = viewModel.preferences.packageName,
            supportedVersion = viewModel.supportedVersion,
            currentVersion = viewModel.installedVersion,
            onDownloadClick = { showInstallerDialog = true },
            onLaunchClick = viewModel::launchAliucord,
            onUninstallClick = viewModel::uninstallAliucord
        )

        CommitList(
            commits = viewModel.commits,
            onRetry = { viewModel.fetchSupportedVersion() }
        )
    }
}
