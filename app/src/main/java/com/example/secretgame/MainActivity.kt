package com.example.secretgame

import InviteScreen
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.secretgame.ui.screens.FinalScreen
import com.example.secretgame.ui.screens.InitialScreen
import com.example.secretgame.ui.screens.Question
import com.example.secretgame.ui.screens.QuestionScreen
import com.example.secretgame.ui.theme.SecretgameTheme

class MainActivity : ComponentActivity() {

    private lateinit var soundPool: SoundPool
    private var soundCorrect: Int = 0
    private var soundWrong: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        soundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .build()

        soundCorrect = soundPool.load(this, R.raw.correct, 1)
        soundWrong = soundPool.load(this, R.raw.wrong, 1)

        enableEdgeToEdge()
        setContent {
            SecretgameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizApp(
                        modifier = Modifier.padding(innerPadding),
                        playCorrectSound = { playSound(soundCorrect) },
                        playWrongSound = { playSound(soundWrong) }
                    )
                }
            }
        }
    }

    private fun playSound(soundId: Int) {
        soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}

sealed class Screen {
    object Initial : Screen()
    data class Quiz(val currentIndex: Int) : Screen()
    object Final : Screen()
    object Invite : Screen()
}

@Composable
fun QuizApp(
    modifier: Modifier,
    playCorrectSound: () -> Unit,
    playWrongSound: () -> Unit
) {
    val questions = listOf(
        Question(
            text = "Em que ano a atual turma INF4AT começou o curso?",
            options = listOf("2021", "2022", "2023", "2024"),
            correctAnswer = "2022"
        ),
        Question(
            text = "Quem é nosso professor favorito?",
            options = listOf("Rafael", "Lucas Neves", "Felipe Thomas", "Gabriel"),
            correctAnswer = "Felipe Thomas"
        ),
        Question(
            text = "Qual truque de mágica foi ensinado pelo professor?",
            options = listOf("Truque de moedas","Truque de cartas", "Truque de lenços", "Nenhuma das anteriores"),
            correctAnswer = "Truque de cartas"
        ),
        Question(
            text = "Se a INF4AT tivesse um prêmio de “melhor professor do ano”, quem ganharia?",
            options = listOf("Felipe Thomas", "Fábio", "Fellipe (Química)", "Outro professor"),
            correctAnswer = "Felipe Thomas"
        ),
        Question(
            text = "Quem é a turma mais animada da escola?",
            options = listOf("INF4AT", "INF4AT", "INF4AT", "INF4AT"),
            correctAnswer = "INF4AT"
        ),
        Question(
            text = "Quem ajudava a turma com conselhos, dúvidas e até choros?",
            options = listOf("Gabriel", "Heloísa", "Daniel", "Felipe Thomas"),
            correctAnswer = "Felipe Thomas"
        ),
        Question(
            text = "Qual é o ambiente favorito da turma pelo câmpus?",
            options = listOf("Aulas de PDM", "Quadra", "Cantina", "Biblioteca"),
            correctAnswer = "Aulas de PDM"
        ),
        Question(
            text = "Quem era o “pacificador oficial” da turma, sempre aparando brigas e rindo junto?",
            options = listOf("Isabela", "Gabriel", "Felipe Thomas", "Pedro"),
            correctAnswer = "Felipe Thomas"
        ),
        Question(
            text = "Quem da turma sempre arrumava confusão, mas fazia todo mundo rir?",
            options = listOf("Pedro e Vítor", "Gabriel e Lucas Neves", "Felipe Thomas", "Michelã"),
            correctAnswer = "Gabriel e Lucas Neves"
        ),
    )

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Initial) }

    when (val screen = currentScreen) {
        is Screen.Initial -> {
            InitialScreen(
                onGameInitPress = {
                    currentScreen = Screen.Quiz(0)
                }
            )
        }

        is Screen.Quiz -> {
            val index = screen.currentIndex
            QuestionScreen(
                question = questions[index],
                progress = (index + 1).toFloat() / questions.size.toFloat(),
                progressText = "${index + 1} / ${questions.size}",
                onAnswerSelected = { _, isCorrect ->
                    if (isCorrect) playCorrectSound() else playWrongSound()
                    if (index < questions.lastIndex) {
                        currentScreen = Screen.Quiz(index + 1)
                    } else {
                        currentScreen = Screen.Final
                    }
                }
            )
        }

        is Screen.Final -> {
            FinalScreen(
                onContinue = {
                    currentScreen = Screen.Invite
                }
            )
        }

        is Screen.Invite -> {
            InviteScreen(
//                onYesClick = {
////                    currentScreen = Screen.Initial
//                }
            )
        }
    }
}
