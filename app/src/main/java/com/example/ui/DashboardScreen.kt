package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.NeurobinViewModel

@Composable
fun DashboardScreen(viewModel: NeurobinViewModel) {
    val isPlaying by viewModel.isPlaying.collectAsState()
    val bioSyncRatio by viewModel.bioSyncRatio.collectAsState()
    val binaryEntropy by viewModel.binaryEntropy.collectAsState()
    val recursionStep by viewModel.recursionStep.collectAsState()
    val sampleWindow by viewModel.sampleWindow.collectAsState()
    val bioBase by viewModel.bioBase.collectAsState()
    val binBase by viewModel.binBase.collectAsState()
    val samplingInterval by viewModel.samplingInterval.collectAsState()
    val waveformPoints by viewModel.waveformPoints.collectAsState()

    var showSmooth by remember { mutableStateOf(true) }
    var showGradient by remember { mutableStateOf(true) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeuroBg)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Status Row & Quick Info
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(if (isPlaying) NeuroGreen else NeuroRed, CircleShape)
                ) {
                    if (isPlaying) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(1.dp, NeuroGreen, CircleShape)
                        )
                    }
                }
                Text(
                    text = if (isPlaying) "NODE ACTIVE (MX-SQ-3000)" else "STREAM PAUSED",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isPlaying) NeuroGreen else NeuroRed
                )
            }
            Surface(
                color = NeuroCard,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, NeuroBorder)
            ) {
                Text(
                    text = "∞ - n = NeuroBIN",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = NeuroCyan,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // KUMI Engine v2.0 Status Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = NeuroCard),
            border = BorderStroke(1.dp, NeuroCyan.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "KUMI ENGINE v2.0 // 97.50 GHz",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeuroCyan
                    )
                    Surface(
                        color = NeuroCyan.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "IP: 10.ℵ₁.5.50",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            color = NeuroCyan,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = "Operator: J.F. Cantoriano Leyva (CALF8712186T5) • San Quintín",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = NeuroText.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Stream rows
                val streams = listOf(
                    Triple("Kumi ai.mx", "HEVC 8K@60fps", "Stable"),
                    Triple("Google Cloud", "SRT AES-256", "0.048ms"),
                    Triple("YouTube", "Multimodal API", "Verified"),
                    Triple("META ENT", "Gateway SRT", "0.115ms")
                )

                streams.forEach { (entity, proto, lat) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "• $entity", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroText)
                        Text(text = proto, fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroCyan)
                        Text(text = lat, fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroGreen)
                    }
                }
            }
        }

        // Top 4 Metrics Grid
        val gridItems = listOf(
            Triple("BIO-SYNC RATIO", "${String.format("%.2f", bioSyncRatio)}%", NeuroGreen),
            Triple("BINARY ENTROPY", "${String.format("%.4f", binaryEntropy)} nats", NeuroCyan),
            Triple("RECURSION STEP (n)", String.format("%06d", recursionStep), NeuroPurple),
            Triple("CARDINAL INVARIANCE", "∞ Conservado", NeuroAmber)
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            for (i in gridItems.indices step 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = gridItems[i].first,
                        value = gridItems[i].second,
                        accentColor = gridItems[i].third
                    )
                    if (i + 1 < gridItems.size) {
                        MetricCard(
                            modifier = Modifier.weight(1f),
                            title = gridItems[i + 1].first,
                            value = gridItems[i + 1].second,
                            accentColor = gridItems[i + 1].third
                        )
                    }
                }
            }
        }

        // Oscilloscope Waveform Canvas Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = NeuroCard),
            border = BorderStroke(1.dp, NeuroBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ShowChart, contentDescription = null, tint = NeuroGreen, modifier = Modifier.size(18.dp))
                        Text(
                            text = "PULSE SYNCHRONIZATION WAVEFORM",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeuroGreen
                        )
                    }
                    Text(
                        text = "Window: $sampleWindow pts",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        color = NeuroText
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Canvas Container
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(NeuroBg, RoundedCornerShape(12.dp))
                        .border(1.dp, NeuroBorder, RoundedCornerShape(12.dp))
                        .padding(8.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val width = size.width
                        val height = size.height
                        val points = waveformPoints

                        if (points.size > 1) {
                            val stepX = width / (points.size - 1)
                            val path = Path()
                            val fillPath = Path()

                            fillPath.moveTo(0f, height)

                            points.forEachIndexed { index, value ->
                                val x = index * stepX
                                val y = height - (value * height * 0.8f + height * 0.1f)
                                if (index == 0) {
                                    path.moveTo(x, y)
                                    fillPath.lineTo(x, y)
                                } else {
                                    if (showSmooth && index > 0) {
                                        val prevX = (index - 1) * stepX
                                        val prevY = height - (points[index - 1] * height * 0.8f + height * 0.1f)
                                        val cx = (prevX + x) / 2f
                                        path.cubicTo(cx, prevY, cx, y, x, y)
                                        fillPath.cubicTo(cx, prevY, cx, y, x, y)
                                    } else {
                                        path.lineTo(x, y)
                                        fillPath.lineTo(x, y)
                                    }
                                }
                            }

                            fillPath.lineTo(width, height)
                            fillPath.close()

                            if (showGradient) {
                                drawPath(
                                    path = fillPath,
                                    brush = Brush.verticalGradient(
                                        colors = listOf(NeuroGreen.copy(alpha = 0.35f), NeuroGreen.copy(alpha = 0.0f)),
                                        startY = 0f,
                                        endY = height
                                    )
                                )
                            }

                            drawPath(
                                path = path,
                                color = NeuroGreen,
                                style = Stroke(width = 3.dp.toPx())
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Canvas Options
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Checkbox(
                                checked = showSmooth,
                                onCheckedChange = { showSmooth = it },
                                colors = CheckboxDefaults.colors(checkedColor = NeuroGreen)
                            )
                            Text("Bezier", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroText)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Checkbox(
                                checked = showGradient,
                                onCheckedChange = { showGradient = it },
                                colors = CheckboxDefaults.colors(checkedColor = NeuroGreen)
                            )
                            Text("Fill", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroText)
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(20, 30, 50).forEach { size ->
                            OutlinedButton(
                                onClick = { viewModel.setWindowSize(size) },
                                shape = RoundedCornerShape(6.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                border = BorderStroke(1.dp, if (sampleWindow == size) NeuroGreen else NeuroBorder)
                            ) {
                                Text("$size", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = if (sampleWindow == size) NeuroGreen else NeuroText)
                            }
                        }
                    }
                }
            }
        }

        // Cantorian Simulation Injector Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = NeuroCard),
            border = BorderStroke(1.dp, NeuroBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "CANTORIAN SIMULATION INJECTOR",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeuroCyan
                )

                // Bio Base Slider
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Biological Pulse Baseline", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroText)
                        Text(String.format("%.2f", bioBase), fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroGreen, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = bioBase,
                        onValueChange = { viewModel.updateBioBase(it) },
                        valueRange = 0.5f..1.0f,
                        colors = SliderDefaults.colors(thumbColor = NeuroGreen, activeTrackColor = NeuroGreen)
                    )
                }

                // Bin Base Slider
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Binary Pulse Baseline", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroText)
                        Text(String.format("%.2f", binBase), fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroCyan, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = binBase,
                        onValueChange = { viewModel.updateBinBase(it) },
                        valueRange = 0.4f..1.0f,
                        colors = SliderDefaults.colors(thumbColor = NeuroCyan, activeTrackColor = NeuroCyan)
                    )
                }

                // Anomaly Trigger Buttons
                Text(text = "Trigger System Anomaly", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = NeuroText)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.injectAnomaly("CML-REPAIR-01") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = NeuroPurple),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("CML Repair", fontFamily = FontFamily.Monospace, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { viewModel.injectAnomaly("ML-KEM-1024") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = NeuroCyan, contentColor = NeuroBg),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("ML-KEM Shield", fontFamily = FontFamily.Monospace, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { viewModel.injectAnomaly("CANTOR-RESYNC") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = NeuroGreen, contentColor = NeuroBg),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Resync", fontFamily = FontFamily.Monospace, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun MetricCard(modifier: Modifier = Modifier, title: String, value: String, accentColor: androidx.compose.ui.graphics.Color) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = NeuroCard),
        border = BorderStroke(1.dp, NeuroBorder)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = title,
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                color = NeuroText.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                fontFamily = FontFamily.Monospace,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
        }
    }
}
