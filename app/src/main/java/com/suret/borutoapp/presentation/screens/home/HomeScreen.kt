package com.suret.borutoapp.presentation.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            HomeTopBar(onSearchClicked = {})
        }
    ) {

    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun HomeScreenDarkPreview() {
    HomeScreen()
}