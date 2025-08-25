import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

data class ConfettiParticle(
    var x: Float,
    var y: Float,
    val color: Color,
    val size: Float,
    val speedY: Float
)

@Composable
fun InviteScreen(
    onYesClick: () -> Unit = {},
) {
    var noButtonOffsetX by remember { mutableStateOf(0.dp) }
    var noButtonOffsetY by remember { mutableStateOf(0.dp) }
    var currentMessage by remember { mutableStateOf<String?>(null) }
    var showConfetti by remember { mutableStateOf(false) }
    var navigateToCamera by remember { mutableStateOf(false) }

    LaunchedEffect(showConfetti) {
        if(showConfetti) {
            kotlinx.coroutines.delay(2000)
            navigateToCamera = true
            onYesClick()
        }
    }

    val funnyMessages = listOf(
        "Ops! Parece que você não consegue dizer NÃO 😜",
        "Ei, o SIM está logo ali 👉",
        "Não aceitamos não como resposta!",
        "Só o SIM leva à felicidade 🎉",
        "Esse NÃO não cola, bora pro SIM 😎",
        "\uD83D\uDEAB 404 - Resposta inválida"
    )

    val confettiParticles = remember { mutableStateListOf<ConfettiParticle>() }

    if (showConfetti) {
        if (confettiParticles.isEmpty()) {
            repeat(100) {
                confettiParticles.add(
                    ConfettiParticle(
                        x = Random.nextFloat() * 400f,
                        y = -10f,
                        color = Color(
                            Random.nextFloat(),
                            Random.nextFloat(),
                            Random.nextFloat(),
                            1f
                        ),
                        size = Random.nextFloat() * 8 + 4,
                        speedY = Random.nextFloat() * 5 + 2
                    )
                )
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (!navigateToCamera) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "VOCÊ ACEITA SER NOSSO PARANINFO???",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 20.dp)
                )

                Text(
                    text = "Depois de tantos momentos bons em aula, queremos te fazer um convite especial. " +
                            "Você aceita ser nosso PARANINFO na formatura? " +
                            "Sua presença vai ser nosso ponto alto!",
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 20.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                showConfetti = true
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF388E3C),
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = "SIM", fontSize = 18.sp)
                        }

                        Button(
                            onClick = {
                                noButtonOffsetX = Random.nextInt(-150, 150).dp
                                noButtonOffsetY = Random.nextInt(-200, 200).dp
                                currentMessage = funnyMessages.random()
                            },
                            modifier = Modifier
                                .offset(x = noButtonOffsetX, y = noButtonOffsetY)
                                .weight(1f)
                                .height(60.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD32F2F),
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = "NÃO", fontSize = 18.sp)
                        }
                    }
                }

                currentMessage?.let {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1976D2),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        } else {
            CameraScreen()
        }

        if (showConfetti) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                confettiParticles.forEach { particle ->
                    drawCircle(
                        color = particle.color,
                        radius = particle.size,
                        center = Offset(particle.x, particle.y)
                    )
                    particle.y += particle.speedY
                    if (particle.y > size.height) {
                        particle.y = -10f
                        particle.x = Random.nextFloat() * size.width
                    }
                }
            }
        }
    }
}

@Composable
fun CameraScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "📸 Hora de tirar a foto com a turma!", fontSize = 24.sp)
    }
}
