package com.example.secretgame.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun FinalScreen(
    onContinue: () -> Unit
) {
    val texts = listOf(
        "🎉 Parabéns! Você completou o quiz da INF4AT 2025!",
        "\uD83D\uDC49 Queremos te contar um segredo:",
        "Você foi escolhido para ser nosso Paraninfo! \uD83E\uDD73\uD83C\uDF93\uD83D\uDC9A",
        "E agora você irá responder a última pergunta. Cuidado, ela é a mais importante!!!"
    )

    var visibleCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        texts.forEachIndexed { i, _ ->
            delay(1600)
            visibleCount = i + 1
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        texts.forEachIndexed { index, text ->
            AnimatedVisibility(
                visible = visibleCount > index,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
            ) {
                Column {
                    Text(
                        text = text,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32),
                        lineHeight = 30.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        AnimatedVisibility(
            visible = visibleCount == texts.size,
            enter = fadeIn() + scaleIn(),
        ) {
            Button(
                onClick = { onContinue() },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B5E20),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Entrar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}