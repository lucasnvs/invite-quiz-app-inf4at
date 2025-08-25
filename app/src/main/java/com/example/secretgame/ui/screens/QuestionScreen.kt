package com.example.secretgame.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.secretgame.ui.theme.SecretgameTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswer: String
)

@Composable
fun QuestionScreen(
    question: Question,
    progress: Float,
    progressText: String,
    onAnswerSelected: (String, Boolean) -> Unit
) {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var isLocked by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFE8F5E9), Color(0xFFFFFFFF))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .shadow(2.dp, RoundedCornerShape(50)),
            color = Color(0xFF2E7D32),
            trackColor = Color.LightGray
        )
        Text(
            text = progressText,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.weight(1f))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = question.text,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20),
                textAlign = TextAlign.Center,
                lineHeight = 28.sp,
                modifier = Modifier.padding(24.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            question.options.chunked(2).forEach { rowOptions ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    rowOptions.forEach { option ->
                        val isCorrect = option == question.correctAnswer
                        val buttonColor =
                            if (selectedAnswer == option) {
                                if (isCorrect) Color(0xFF388E3C) else Color(0xFFD32F2F)
                            } else {
                                Color(0xFF2E7D32)
                            }

                        Button(
                            onClick = {
                                if (!isLocked) {
                                    selectedAnswer = option
                                    isLocked = true
                                    scope.launch {
                                        delay(500)
                                        onAnswerSelected(option, isCorrect)
                                        selectedAnswer = null
                                        isLocked = false
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(70.dp)
                                .shadow(4.dp, RoundedCornerShape(16.dp))
                                .alpha(if (isLocked && selectedAnswer != option) 0.5f else 1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = option,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun QuizScreenPreview() {
    SecretgameTheme {
        QuestionScreen(
            question = Question(
                text = "Em que ano a atual turma INF4AT começou o curso?",
                options = listOf("2021", "2022", "2023", "2024"),
                correctAnswer = "2022"
            ),
            progress = 0.5f,
            progressText = "1 / 10",
            onAnswerSelected = { _, _ -> }
        )
    }
}
