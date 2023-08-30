package com.anilyilmaz.nativefirebase.feature.main

import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.anilyilmaz.nativefirebase.core.designsystem.theme.NativeFirebaseTheme
import com.anilyilmaz.nativefirebase.feature.main.ui.NativeFirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            NativeFirebaseTheme {
                NativeFirebaseApp()
            }
        }
    }
}