package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.NetworkCell
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.ApnConfigScreen
import com.example.ui.DashboardScreen
import com.example.ui.GeminiAiScreen
import com.example.ui.ManifestScreen
import com.example.ui.theme.*
import com.example.viewmodel.NeurobinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: NeurobinViewModel = viewModel()
                var selectedTab by remember { mutableIntStateOf(0) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            containerColor = NeuroCard,
                            tonalElevation = 8.dp
                        ) {
                            NavigationBarItem(
                                selected = selectedTab == 0,
                                onClick = { selectedTab = 0 },
                                icon = { Icon(Icons.Default.ShowChart, contentDescription = "Dashboard") },
                                label = { Text("Telemetry", fontFamily = FontFamily.Monospace, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = NeuroGreen,
                                    selectedTextColor = NeuroGreen,
                                    unselectedIconColor = NeuroText.copy(alpha = 0.6f),
                                    unselectedTextColor = NeuroText.copy(alpha = 0.6f),
                                    indicatorColor = NeuroGreen.copy(alpha = 0.2f)
                                )
                            )
                            NavigationBarItem(
                                selected = selectedTab == 1,
                                onClick = { selectedTab = 1 },
                                icon = { Icon(Icons.Default.NetworkCell, contentDescription = "APN Gateway") },
                                label = { Text("APN Gateway", fontFamily = FontFamily.Monospace, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = NeuroCyan,
                                    selectedTextColor = NeuroCyan,
                                    unselectedIconColor = NeuroText.copy(alpha = 0.6f),
                                    unselectedTextColor = NeuroText.copy(alpha = 0.6f),
                                    indicatorColor = NeuroCyan.copy(alpha = 0.2f)
                                )
                            )
                            NavigationBarItem(
                                selected = selectedTab == 2,
                                onClick = { selectedTab = 2 },
                                icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "Gemini AI") },
                                label = { Text("Gemini AI", fontFamily = FontFamily.Monospace, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = NeuroPurple,
                                    selectedTextColor = NeuroPurple,
                                    unselectedIconColor = NeuroText.copy(alpha = 0.6f),
                                    unselectedTextColor = NeuroText.copy(alpha = 0.6f),
                                    indicatorColor = NeuroPurple.copy(alpha = 0.2f)
                                )
                            )
                            NavigationBarItem(
                                selected = selectedTab == 3,
                                onClick = { selectedTab = 3 },
                                icon = { Icon(Icons.Default.Security, contentDescription = "Manifest") },
                                label = { Text("Manifest", fontFamily = FontFamily.Monospace, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = NeuroAmber,
                                    selectedTextColor = NeuroAmber,
                                    unselectedIconColor = NeuroText.copy(alpha = 0.6f),
                                    unselectedTextColor = NeuroText.copy(alpha = 0.6f),
                                    indicatorColor = NeuroAmber.copy(alpha = 0.2f)
                                )
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (selectedTab) {
                            0 -> DashboardScreen(viewModel)
                            1 -> ApnConfigScreen()
                            2 -> GeminiAiScreen(viewModel)
                            3 -> ManifestScreen()
                        }
                    }
                }
            }
        }
    }
}
