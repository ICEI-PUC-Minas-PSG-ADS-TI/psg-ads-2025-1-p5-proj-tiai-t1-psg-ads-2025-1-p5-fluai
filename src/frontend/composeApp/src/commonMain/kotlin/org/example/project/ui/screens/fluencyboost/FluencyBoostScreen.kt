package org.example.project.ui.screens.fluencyboost


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.background
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.theme.Dark_Blue
import org.example.project.theme.Light_Blue
import org.example.project.theme.Light_Gray
import org.example.project.ui.components.dialog.showSuccessDialog
import org.example.project.ui.screens.levelingtest.TopAppBar
import org.example.project.ui.screens.levelingtest.formatAnswer
import org.example.project.ui.state.rememberUiCommonState
import org.example.project.ui.theme.PoppinsTypography

@Composable
fun FluencyBoostScreen(
    viewModel: FluencyBoostViewModel
) {

    LaunchedEffect(Unit){
        viewModel.getQuestion()
    }

    val uiState = rememberUiCommonState()

    LaunchedEffect(Unit){
        viewModel.fluencyBoostResult.collect { result ->
            when(result){
                is FluencyBoostResult.Success -> {
                    uiState.showCircularProgressBar.value = false
                }

                is FluencyBoostResult.Loading -> uiState.showCircularProgressBar.value = true

                is FluencyBoostResult.Error -> {
                    uiState.showCircularProgressBar.value = false
                    uiState.isDisplayDialogError.value = true
                    uiState.errorMessage.value = result.message
                }

                is FluencyBoostResult.Completed -> {
                    uiState.showCircularProgressBar.value = false
                    uiState.isDisplaySuccessDialog.value = true
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                currentQuestions = viewModel.currentQuestionIndex + 1,
                totalQuestions = viewModel.questions.size
            ){
                viewModel.onEvent(FluencyBoostEvent.GoToHome)
            }
        }
    ) {

        showSuccessDialog(isDisplaySuccessDialog = uiState.isDisplaySuccessDialog, message = "Lição concluída!"){
            viewModel.onEvent(FluencyBoostEvent.GoToHome)
        }
        if (viewModel.questions.isNotEmpty()){
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

@Composable
fun QuestionsContent(
    question: String,
    options : List<String>,
    onOptionSelected: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Spacer(modifier = Modifier.height(32.dp))

        FluencyBoostQuestion(question = question, options = options, onOptionSelected = onOptionSelected)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FluencyBoostQuestion(
    question : String,
    options : List<String>,
    onOptionSelected : (String) -> Unit
) {

    Text(
        text = question,
        style = PoppinsTypography().h6,
        color = Dark_Blue,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(modifier = Modifier.height(60.dp))

    FlowColumn {
        options.forEachIndexed{ index, answer ->
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
fun AnswerButton(modifier : Modifier = Modifier, text: String, letter: String, onClick : () -> Unit) {
    Button(
        modifier = modifier.fillMaxWidth().height(60.dp),
        colors = ButtonDefaults.buttonColors(Light_Blue),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick) {
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