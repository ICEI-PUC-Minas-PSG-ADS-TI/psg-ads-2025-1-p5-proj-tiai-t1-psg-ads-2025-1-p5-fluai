package org.example.project.ui.screens.fluencyboost


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.example.project.theme.Dark_Blue
import org.example.project.theme.Light_Blue
import org.example.project.theme.Light_Gray
import org.example.project.ui.components.dialog.showErrorDialog
import org.example.project.ui.components.dialog.showSuccessDialog
import org.example.project.ui.components.loading.LoadingComponent
import org.example.project.ui.screens.levelingtest.TopAppBar
import org.example.project.ui.screens.levelingtest.formatAnswer
import org.example.project.ui.state.rememberUiCommonState
import org.example.project.ui.theme.PoppinsTypography
import kotlinx.datetime.Clock
import org.example.project.theme.Blue

@Composable
fun FluencyBoostScreen(
    viewModel: FluencyBoostViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.getQuestion()
    }

    val uiState = rememberUiCommonState()
    val message = remember { mutableStateOf("") }

    val generating = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fluencyBoostResult.collect { result ->
            when (result) {
                is FluencyBoostResult.Success -> {
                    generating.value = false
                }

                is FluencyBoostResult.Loading -> uiState.showCircularProgressBar.value = true

                is FluencyBoostResult.Error -> {
                    generating.value = false
                    uiState.showCircularProgressBar.value = false
                    uiState.isDisplayDialogError.value = true
                    uiState.errorMessage.value = result.message
                }

                is FluencyBoostResult.Completed -> {
                    uiState.showCircularProgressBar.value = false
                    message.value = result.message
                    uiState.isDisplaySuccessDialog.value = true
                }

                is FluencyBoostResult.Generating -> {
                    uiState.showCircularProgressBar.value = false
                    generating.value = true
                }

                else -> Unit
            }
        }
    }

    if (generating.value) {
        ActivityGenerationScreen()
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (viewModel.questions.isNotEmpty()){
                    TopAppBar(
                        currentQuestions = viewModel.currentQuestionIndex + 1,
                        totalQuestions = viewModel.questions.size
                    ) {
                        viewModel.onEvent(FluencyBoostEvent.GoToHome)
                    }
                }else{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(vertical = 12.dp)
                    ) {
                        TopAppBar(
                            currentQuestions = 0,
                            totalQuestions = 0
                        ){
                            viewModel.onEvent(FluencyBoostEvent.GoToHome)
                        }
                    }
                }
            }
        ) {
            showErrorDialog(
                isDisplayDialogError = uiState.isDisplayDialogError,
                errorMessage = uiState.errorMessage.value
            )
            showSuccessDialog(
                isDisplaySuccessDialog = uiState.isDisplaySuccessDialog,
                message = message.value
            ) {
                viewModel.onEvent(FluencyBoostEvent.GoToHome)
            }

            if (viewModel.questions.isNotEmpty()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    LoadingComponent(isLoading = uiState.showCircularProgressBar.value)
                    val currentIndex = viewModel.questions[viewModel.currentQuestionIndex]
                    QuestionsContent(
                        question = currentIndex.question,
                        options = currentIndex.optionList(),
                        onOptionSelected = { formattedAnswer ->
                            viewModel.onEvent(FluencyBoostEvent.SubmitAnswer(formattedAnswer))
                        }
                    )
                }
            }

        }
    }

}


@Composable
fun ActivityGenerationScreen() {
    val steps = listOf(
        "Analisando seu nível",
        "Selecionando temas",
        "Criando questões",
        "Revisando conteúdo",
        "Preparando atividades"
    )
    val currentStep = remember { mutableStateOf(0) }
    val progress = remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        steps.forEachIndexed { index, _ ->
            currentStep.value = index
            repeat(10) { i ->
                progress.floatValue = (index * 10 + i) / 50f
                delay(1000)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = steps[currentStep.value],
            style = PoppinsTypography().h6,
            modifier = Modifier.padding(horizontal = 32.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        LinearProgressIndicator(
            progress = progress.floatValue,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(8.dp),
            color = Blue
        )

        Spacer(modifier = Modifier.height(8.dp))

        val tips = listOf(
            "Dica: Pratique 15 minutos por dia para melhores resultados",
            "Sabia que revisar após 24h aumenta a retenção em 70%?",
            "Conjugue verbos enquanto espera para melhorar seu inglês"
        )

        Text(
            text = tips[(Clock.System.now().toEpochMilliseconds() / 5000 % tips.size).toInt()],
            style = PoppinsTypography().caption,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun QuestionsContent(
    question: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Spacer(modifier = Modifier.height(32.dp))
        FluencyBoostQuestion(
            question = question,
            options = options,
            onOptionSelected = onOptionSelected
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FluencyBoostQuestion(
    question: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {

    Text(
        text = question,
        style = PoppinsTypography().h6,
        color = Dark_Blue,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(modifier = Modifier.height(60.dp))

    FlowColumn {
        options.forEachIndexed { index, answer ->
            AnswerButton(
                modifier = Modifier.padding(bottom = 32.dp),
                text = answer,
                letter = "${'a' + index}",
                onClick = {
                    val formatted = formatAnswer(question, answer)
                    onOptionSelected(formatted)
                }
            )
        }
    }
}

@Composable
fun AnswerButton(modifier: Modifier = Modifier, text: String, letter: String, onClick: () -> Unit) {
    Button(
        modifier = modifier.fillMaxWidth().height(60.dp),
        colors = ButtonDefaults.buttonColors(Light_Blue),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier.size(36.dp).clip(CircleShape).background(color = Light_Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = letter,
                    style = PoppinsTypography().body1,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = PoppinsTypography().button,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically)
            )
        }
    }
}