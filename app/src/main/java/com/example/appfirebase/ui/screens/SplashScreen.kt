package com.example.appfirebase.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.appfirebase.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinish: () -> Unit) {
    var start by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        start = true
        delay(1800)
        onFinish()
    }

    Box(
        Modifier.fillMaxSize().background(CarbonBlack),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PcbLogo(animate = start)
            Spacer(Modifier.height(24.dp))
            Text("COMPONENTES",
                style = MaterialTheme.typography.headlineLarge,
                color = PcbGreenLight)
            Text("ELETRÔNICOS",
                style = MaterialTheme.typography.titleLarge,
                color = CopperGold)
        }
    }
}

@Composable
fun PcbLogo(animate: Boolean) {
    val progress by animateFloatAsState(
        targetValue = if (animate) 1f else 0f,
        animationSpec = tween(1500)
    )

    Canvas(modifier = Modifier.size(140.dp)) {
        val w = size.width
        val h = size.height
        // Chip central
        drawRect(
            color = PcbGreen,
            topLeft = Offset(w*0.3f, h*0.3f),
            size = androidx.compose.ui.geometry.Size(w*0.4f, h*0.4f)
        )
        // Trilhas (pinos) animadas
        val pins = 3
        for (i in 0 until pins) {
            val y = h*0.35f + (h*0.3f / pins) * i
            // esquerda
            drawLine(CopperGold, Offset(0f, y), Offset(w*0.3f, y),
                strokeWidth = 4f, cap = StrokeCap.Round, alpha = progress)
            // direita
            drawLine(CopperGold, Offset(w*0.7f, y), Offset(w, y),
                strokeWidth = 4f, cap = StrokeCap.Round, alpha = progress)
        }
    }
}