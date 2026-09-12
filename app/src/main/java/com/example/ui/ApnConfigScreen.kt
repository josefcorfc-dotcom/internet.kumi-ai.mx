package com.example.ui

import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.NetworkCell
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun ApnConfigScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val apnString = "apn=internet.kumi-ai.mx,user=kumiAl.mx,auth=pap,number=*99#"

    var isConnected by remember { mutableStateOf(true) }

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
                Icon(Icons.Default.NetworkCell, contentDescription = null, tint = NeuroCyan)
                Text(
                    text = "KUMI-AI CELLULAR GATEWAY APN",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeuroCyan
                )
            }
            Surface(
                color = if (isConnected) NeuroGreen.copy(alpha = 0.15f) else NeuroRed.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, if (isConnected) NeuroGreen else NeuroRed)
            ) {
                Text(
                    text = if (isConnected) "ONLINE" else "DISCONNECTED",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isConnected) NeuroGreen else NeuroRed
                )
            }
        }

        // Main APN Config Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = NeuroCard),
            border = BorderStroke(1.dp, NeuroBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "ACTIVE APN STRING CONFIGURATION",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = NeuroText.copy(alpha = 0.7f)
                )

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = NeuroBg,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, NeuroCyan.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = apnString,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeuroCyan,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = android.content.ClipData.newPlainText("APN Config", apnString)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "APN string copied to clipboard", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy APN", tint = NeuroCyan, modifier = Modifier.size(18.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Breakdown list
                val apnParams = listOf(
                    Pair("APN Endpoint", "internet.kumi-ai.mx"),
                    Pair("Operator User", "kumiAl.mx"),
                    Pair("Authentication Type", "PAP"),
                    Pair("Dial Number", "*99#"),
                    Pair("Local IP", "192.168.1.76"),
                    Pair("Hardware Terminal", "OPPO CPH2669 (Android 14)"),
                    Pair("Bucket Custodia", "gs://kumi-ghost-alef-g6-000155")
                )

                apnParams.forEach { (label, value) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = label, fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroText.copy(alpha = 0.7f))
                        Text(text = value, fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroText, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { isConnected = !isConnected },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isConnected) NeuroRed.copy(alpha = 0.2f) else NeuroGreen.copy(alpha = 0.2f), contentColor = if (isConnected) NeuroRed else NeuroGreen),
                    border = BorderStroke(1.dp, if (isConnected) NeuroRed else NeuroGreen),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.SignalCellularAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isConnected) "SIMULATE CELLULAR DISCONNECT" else "RE-ESTABLISH KUMI-AI LINK",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Post-Quantum & WSS Stream Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = NeuroCard),
            border = BorderStroke(1.dp, NeuroBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "WEBSOCKET STREAM & CRYPTOGRAPHIC SHIELD",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeuroGreen
                )
                Text(
                    text = "WSS Endpoint: wss://api.neurospark.inc/ws/kumi-stream?project=cantoriano-leyvajf&node=SQ-3000_G6",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = NeuroText
                )
                Text(
                    text = "Post-Quantum Cryptography: ML-KEM-1024_Σ with BLAKE3 / SHA3-512 signatures.",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = NeuroText
                )
            }
        }
    }
}
