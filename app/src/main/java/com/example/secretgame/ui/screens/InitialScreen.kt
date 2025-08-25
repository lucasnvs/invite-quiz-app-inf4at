package com.example.secretgame.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.secretgame.R
import com.example.secretgame.ui.theme.SecretgameTheme

@Composable
fun InitialScreen(
    onGameInitPress: () -> Unit,
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1B5E20),
            Color(0xFF66BB6A)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.TopStart)
                .offset((-40).dp, (-40).dp)
                .background(Color.White.copy(alpha = 0.1f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(220.dp)
                .align(Alignment.BottomEnd)
                .offset((40).dp, (40).dp)
                .background(Color.White.copy(alpha = 0.1f), CircleShape)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_turma),
                contentDescription = "Logo INF4AT",
                modifier = Modifier
                    .height(120.dp)
                    .padding(bottom = 8.dp)
            )

            Text(
                text = "QUIZ DA INF4AT 2025 🎓",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Teste seus conhecimentos e divirta-se! 🎉",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = { onGameInitPress() },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(6.dp, RoundedCornerShape(16.dp))
            ) {
                Text(
                    text = "Começar",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialScreenPreview() {
    SecretgameTheme {
        InitialScreen({})
    }
}
