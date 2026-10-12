package com.hosseinalipour.barnamerizi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hosseinalipour.barnamerizi.ui.PlanningApp
import com.hosseinalipour.barnamerizi.ui.theme.BarnameriziTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BarnameriziTheme {
                PlanningApp()
            }
        }
    }
}
