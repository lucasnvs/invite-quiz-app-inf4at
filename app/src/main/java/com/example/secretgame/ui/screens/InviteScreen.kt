import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun InviteScreen() {
    var noButtonOffsetX by remember { mutableStateOf(0.dp) }
    var noButtonOffsetY by remember { mutableStateOf(0.dp) }
    var currentMessage by remember { mutableStateOf<String?>(null) }
    var showCelebration by remember { mutableStateOf(false) }

    val funnyMessages = listOf(
        "😜 Ops! Parece que você não consegue dizer NÃO!",
        "👉 O SIM tá logo ali...",
        "🚫 Não aceitamos não como resposta!",
        "🎉 Só o SIM leva à felicidade!",
        "😎 Esse NÃO não cola, bora pro SIM!",
        "❌ 404 - Resposta inválida"
    )

    if (showCelebration) {
        CelebrationScreen()
    } else {
        InviteContent(
            onYesClick = { showCelebration = true },
            onNoClick = {
                noButtonOffsetX = Random.nextInt(-150, 150).dp
                noButtonOffsetY = Random.nextInt(-200, 200).dp
                currentMessage = funnyMessages.random()
            },
            noButtonOffsetX = noButtonOffsetX,
            noButtonOffsetY = noButtonOffsetY,
            currentMessage = currentMessage
        )
    }
}

@Composable
fun InviteContent(
    onYesClick: () -> Unit,
    onNoClick: () -> Unit,
    noButtonOffsetX: Dp,
    noButtonOffsetY: Dp,
    currentMessage: String?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1B5E20),
                        Color(0xFF66BB6A)
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "🎓 VOCÊ ACEITA SER NOSSO PARANINFO??? 🎓",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Depois de tantos momentos bons em aula, queremos te fazer um convite especial.\n\n" +
                        "Você aceita ser nosso PARANINFO na formatura?\n\n" +
                        "Sua presença vai ser nosso ponto alto! 🎉",
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onYesClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(65.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF43A047),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "SIM 🎉", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onNoClick,
                    modifier = Modifier
                        .offset(x = noButtonOffsetX, y = noButtonOffsetY)
                        .weight(1f)
                        .height(65.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "NÃO 🚫", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }

            currentMessage?.let {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = it,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1565C0),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun CelebrationScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = EaseInOutBack),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scaleAnim"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    listOf(
                        Color(0xFF4CAF50),
                        Color(0xFF1B5E20)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "🎊 PARABÉNS!!! 🎊\n\nVocê agora é nosso PARANINFO! 🥳",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(32.dp)
                .scale(scale)
        )
    }

//    Button(
//        onClick = { },
//        shape = RoundedCornerShape(16.dp),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = Color(0xFF2E7D32),
//            contentColor = Color.White
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(60.dp)
//            .shadow(6.dp, RoundedCornerShape(16.dp))
//    ) {
//        Text(
//            text = "Voltar ao início",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold
//        )
//    }
}
