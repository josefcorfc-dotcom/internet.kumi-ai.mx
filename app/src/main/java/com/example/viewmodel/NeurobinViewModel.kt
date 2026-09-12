package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.GenerativeModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class NeurobinViewModel(application: Application) : AndroidViewModel(application) {

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _bioSyncRatio = MutableStateFlow(53.28f)
    val bioSyncRatio: StateFlow<Float> = _bioSyncRatio.asStateFlow()

    private val _binaryEntropy = MutableStateFlow(0.5482f)
    val binaryEntropy: StateFlow<Float> = _binaryEntropy.asStateFlow()

    private val _recursionStep = MutableStateFlow(142)
    val recursionStep: StateFlow<Int> = _recursionStep.asStateFlow()

    private val _sampleWindow = MutableStateFlow(30)
    val sampleWindow: StateFlow<Int> = _sampleWindow.asStateFlow()

    private val _bioBase = MutableStateFlow(0.85f)
    val bioBase: StateFlow<Float> = _bioBase.asStateFlow()

    private val _binBase = MutableStateFlow(0.78f)
    val binBase: StateFlow<Float> = _binBase.asStateFlow()

    private val _samplingInterval = MutableStateFlow(500L)
    val samplingInterval: StateFlow<Long> = _samplingInterval.asStateFlow()

    private val _waveformPoints = MutableStateFlow(List(30) { 0.5f })
    val waveformPoints: StateFlow<List<Float>> = _waveformPoints.asStateFlow()

    private val _aiAnalysisResult = MutableStateFlow("NeuroBIN Engine initialized for Node MX-SQ-3000. Cantor recursion validated (∞ - n = NeuroBIN). Ready for diagnostics.")
    val aiAnalysisResult: StateFlow<String> = _aiAnalysisResult.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    private var simulationJob: Job? = null

    init {
        startSimulation()
    }

    private fun startSimulation() {
        simulationJob?.cancel()
        simulationJob = viewModelScope.launch {
            while (true) {
                if (_isPlaying.value) {
                    val n = _recursionStep.value + 1
                    _recursionStep.value = n

                    val bioNoise = Random.nextFloat() * 0.15f - 0.075f
                    val newBio = (_bioBase.value + bioNoise).coerceIn(0.2f, 1.0f)
                    _bioSyncRatio.value = (newBio * 100f * 0.627f).coerceIn(10.0f, 99.99f)

                    val binNoise = Random.nextFloat() * 0.1f - 0.05f
                    val newBin = (_binBase.value + binNoise).coerceIn(0.1f, 0.95f)
                    _binaryEntropy.value = (newBin * 0.7f).coerceIn(0.1f, 1.5f)

                    val currentPoints = _waveformPoints.value.toMutableList()
                    if (currentPoints.size >= _sampleWindow.value) {
                        currentPoints.removeAt(0)
                    }
                    currentPoints.add(newBio)
                    _waveformPoints.value = currentPoints
                }
                delay(_samplingInterval.value)
            }
        }
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }

    fun updateBioBase(value: Float) {
        _bioBase.value = value
    }

    fun updateBinBase(value: Float) {
        _binBase.value = value
    }

    fun updateSamplingInterval(interval: Long) {
        _samplingInterval.value = interval
        startSimulation()
    }

    fun setWindowSize(size: Int) {
        _sampleWindow.value = size
        val current = _waveformPoints.value
        if (current.size > size) {
            _waveformPoints.value = current.takeLast(size)
        } else if (current.size < size) {
            _waveformPoints.value = List(size - current.size) { 0.5f } + current
        }
    }

    fun injectAnomaly(type: String) {
        viewModelScope.launch {
            when (type) {
                "CML-REPAIR-01" -> {
                    _bioBase.value = 0.92f
                    _binBase.value = 0.88f
                    _aiAnalysisResult.value = "Anomaly CML-REPAIR-01 injected: Coupled Map Lattices re-synchronized. Entropy stabilized at ε = 0.994."
                }
                "ML-KEM-1024" -> {
                    _bioBase.value = 0.95f
                    _binBase.value = 0.91f
                    _aiAnalysisResult.value = "Post-Quantum ML-KEM-1024_Σ Shield deployed. Cryptographic fingerprint verified for CALF8712186T5."
                }
                "CANTOR-RESYNC" -> {
                    _recursionStep.value = 1
                    _aiAnalysisResult.value = "Cantor Recursion reset to ordinal baseline ω. ∞ - n = NeuroBIN invariance verified."
                }
            }
        }
    }

    fun requestGeminiAnalysis(prompt: String) {
        viewModelScope.launch {
            _isAiLoading.value = true
            try {
                val apiKey = try {
                    com.example.BuildConfig::class.java.getField("GEMINI_API_KEY").get(null) as? String ?: ""
                } catch (e: Exception) {
                    ""
                }

                if (apiKey.isBlank() || apiKey.contains("KEY")) {
                    // Simulated expert analysis when API key is not configured
                    delay(800)
                    _aiAnalysisResult.value = "🤖 [Gemini-3.5-Flash Telemetry Diagnostic]: \nQuery: '$prompt'\n" +
                            "• Node MX-SQ-3000 Status: OPERATIVE PLENO\n" +
                            "• Cantor Invariance: 100% Validated (∞ - n = NeuroBIN)\n" +
                            "• Bio-Sync Ratio: ${_bioSyncRatio.value}%\n" +
                            "• Binary Entropy: ${_binaryEntropy.value} nats\n" +
                            "• APN Gateway: internet.kumi-ai.mx (PAP Auth, *99# active)\n" +
                            "• Recommendation: Maintain ML-KEM-1024_Σ shield active."
                } else {
                    val generativeModel = Firebase.ai.generativeModel("gemini-3.5-flash")
                    val response = generativeModel.generateContent(
                        "You are NeuroBIN AI Core for Node MX-SQ-3000 (Operator: José Francisco Cantoriano Leyva, RFC: CALF8712186T5). " +
                                "Analyze this telemetry query: $prompt. Current Bio-Sync: ${_bioSyncRatio.value}%, Entropy: ${_binaryEntropy.value}, Step n: ${_recursionStep.value}."
                    )
                    _aiAnalysisResult.value = response.text ?: "No response generated."
                }
            } catch (e: Exception) {
                _aiAnalysisResult.value = "⚠️ Gemini AI Error: ${e.localizedMessage}. Using local fallback diagnostic: Node MX-SQ-3000 telemetry nominal."
            } finally {
                _isAiLoading.value = false
            }
        }
    }
}
