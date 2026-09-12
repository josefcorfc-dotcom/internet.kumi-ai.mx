package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.NeurobinViewModel

@Composable
fun GeminiAiScreen(viewModel: NeurobinViewModel) {
    val aiResult by viewModel.aiAnalysisResult.collectAsState()
    val isAiLoading by viewModel.isAiLoading.collectAsState()

    var userPrompt by remember { mutableStateOf("Analyze current telemetry for node MX-SQ-3000 and verify Cantor recursion invariants.") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeuroBg)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = NeuroPurple)
                Text(
                    text = "GEMINI-3.5-FLASH TELEMETRY INTELLIGENCE",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeuroPurple
                )
            }
            Surface(
                color = NeuroPurple.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, NeuroPurple)
            ) {
                Text(
                    text = "AI CORE ACTIVE",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeuroPurple
                )
            }
        }

        // Prompt Input Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = NeuroCard),
            border = BorderStroke(1.dp, NeuroBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "QUERY NEUROBIN RECURSION ENGINE",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = NeuroText.copy(alpha = 0.7f)
                )

                OutlinedTextField(
                    value = userPrompt,
                    onValueChange = { userPrompt = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeuroPurple,
                        unfocusedBorderColor = NeuroBorder,
                        focusedTextColor = NeuroText,
                        unfocusedTextColor = NeuroText
                    ),
                    textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                )

                Button(
                    onClick = { viewModel.requestGeminiAnalysis(userPrompt) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isAiLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = NeuroPurple),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (isAiLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), color = NeuroBg, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Analyzing Telemetry...", fontFamily = FontFamily.Monospace, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    } else {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Execute Gemini Diagnostic", fontFamily = FontFamily.Monospace, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Analysis Output Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = NeuroCard),
            border = BorderStroke(1.dp, NeuroBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "DIAGNOSTIC OUTPUT & INVARIANT REPORT",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeuroGreen
                )

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = NeuroBg,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, NeuroBorder)
                ) {
                    Text(
                        text = aiResult,
                        modifier = Modifier.padding(12.dp),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = NeuroText
                    )
                }
            }
        }
    }
}
