package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun ManifestScreen() {
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
                Icon(Icons.Default.Security, contentDescription = null, tint = NeuroAmber)
                Text(
                    text = "SOVEREIGN CUSTODY & MANIFEST",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeuroAmber
                )
            }
            Surface(
                color = NeuroAmber.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, NeuroAmber)
            ) {
                Text(
                    text = "CALF8712186T5",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeuroAmber
                )
            }
        }

        // Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = NeuroCard),
            border = BorderStroke(1.dp, NeuroBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "RECURSIÓN DE CANTOR VALIDADA",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeuroGreen
                )

                Text(
                    text = "Formula: ∞ - n = NeuroBIN\n" +
                            "• Neuro: Por Neurona de los Seres Vivos (pulso biológico).\n" +
                            "• Bin: Del código Binario (pulso artificial).\n" +
                            "• Principio: Un mismo ecosistema de pulsos eléctricos.",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = NeuroText
                )

                Divider(color = NeuroBorder)

                val manifestDetails = listOf(
                    Pair("Operator", "José Francisco Cantoriano Leyva"),
                    Pair("RFC Custody", "CALF8712186T5"),
                    Pair("ORCID", "0009-0007-6963-1205"),
                    Pair("KUMI Engine", "v2.0 (Master Clock 97.50 GHz)"),
                    Pair("Node IP", "10.ℵ₁.5.50 (San Quintín)"),
                    Pair("HEVC Pipeline", "7680x4320@60fps (Main 10)"),
                    Pair("Encryption", "AES-256-GCM / SRT"),
                    Pair("GCP Project", "cantoriano-leyvajf"),
                    Pair("Primary Bucket", "gs://kumi-ghost-alef-g6-000155"),
                    Pair("Evidence Bucket", "gs://kumi-ai-firmamento-evidencias"),
                    Pair("Node ID", "MX-SQ-3000 / SQ-3000_G6"),
                    Pair("Entropy Vector (ε)", "0.994 (ML-KEM-1024_Σ)"),
                    Pair("Hardware", "OPPO CPH2669 (Android 14)")
                )

                manifestDetails.forEach { (label, value) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = label, fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroText.copy(alpha = 0.7f))
                        Text(text = value, fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroText, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
