package com.example.sunnytime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sunnytime.ui.theme.SunnyTimeTheme
import com.example.sunnytime.view.MainContent
import com.example.sunnytime.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SunnyTimeTheme() {
                MainContent(viewModel = MainViewModel())
            }
        }
    }
}


