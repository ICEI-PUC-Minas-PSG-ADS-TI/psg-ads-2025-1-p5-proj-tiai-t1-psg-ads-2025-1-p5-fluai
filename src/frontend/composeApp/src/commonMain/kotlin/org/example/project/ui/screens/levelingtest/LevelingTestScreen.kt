package org.example.project.ui.screens.levelingtest

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import frontend.composeapp.generated.resources.Res
import frontend.composeapp.generated.resources.educator
import org.example.project.theme.Blue
import org.example.project.ui.components.dialog.showErrorDialog
import org.example.project.ui.components.dialog.showSuccessDialog
import org.example.project.ui.components.loading.LoadingComponent
import org.example.project.ui.screens.fluencyboost.ActivityGenerationScreen
import org.example.project.ui.state.rememberUiCommonState
import org.example.project.ui.theme.PoppinsTypography
import org.jetbrains.compose.resources.painterResource

@Composable
fun LevelingTest(
    viewModel: LevelingTestViewModel
) {

    val uiState = rememberUiCommonState()

    DisposableEffect(Unit){
        onDispose {
            viewModel.onExit()
        }
    }

    LaunchedEffect(Unit){
        viewModel.getQuestion()
    }

    val message = remember { mutableStateOf("") }

    val generating = remember { mutableStateOf(false) }


    LaunchedEffect(Unit){
        viewModel.levelingTestResult.collect { result ->
            when(result){
                is LevelingTestResult.Success -> {
                    generating.value = false
                    uiState.showCircularProgressBar.value = false
                }

                is LevelingTestResult.Loading -> uiState.showCircularProgressBar.value = true

                is LevelingTestResult.Error -> {
                    uiState.showCircularProgressBar.value = false
                    uiState.isDisplayDialogError.value = true
                    uiState.errorMessage.value = result.message
                }

                is LevelingTestResult.Completed -> {
                    generating.value = false
                    uiState.showCircularProgressBar.value = false
                    message.value = result.message
                    uiState.isDisplaySuccessDialog.value = true
                }

                is LevelingTestResult.Generating -> {
                    uiState.showCircularProgressBar.value = false
                    uiState.isDisplayDialogError.value = false
                    generating.value = true
                }
                else -> Unit
            }
        }
    }

    if (generating.value){
        ActivityGenerationScreen()
    }else{
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (viewModel.questions.isNotEmpty()){
                    TopAppBar(currentQuestions = viewModel.currentQuestionIndex + 1 , viewModel.questions.size){
                        viewModel.onEvent(LevelingTestEvent.GoToHome)
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
                            viewModel.onEvent(LevelingTestEvent.GoToHome)
                        }
                    }
                }
            }
        ) { paddingValues ->
            showErrorDialog(isDisplayDialogError = uiState.isDisplayDialogError, errorMessage = uiState.errorMessage.value)
            showSuccessDialog(isDisplaySuccessDialog = uiState.isDisplaySuccessDialog, message = message.value){
                viewModel.onEvent(LevelingTestEvent.GoToHome)
            }
            LoadingComponent(uiState.showCircularProgressBar.value)
            if (!uiState.showCircularProgressBar.value && viewModel.questions.isNotEmpty()){
                val currentQuestion = viewModel.questions[viewModel.currentQuestionIndex]
                QuestionContent(
                    question = currentQuestion.question,
                    options = currentQuestion.optionList(),
                    correctAnswer = currentQuestion.answer,
                    onOptionSelected = { formattedAnswer ->
                        viewModel.onEvent(LevelingTestEvent.SubmitAnswer(formattedAnswer))
                    },
                    paddingValues = paddingValues
                )
            }
        }
    }
}


@Composable
fun QuestionContent(
    question: String,
    options: List<String>,
    correctAnswer: String,
    onOptionSelected: (String) -> Unit,
    paddingValues: PaddingValues
) {
    val selectedOption = remember(question) { mutableStateOf<String?>(null) }
    val hasAnswered = remember(question) { mutableStateOf(false) }

    LaunchedEffect(hasAnswered.value) {
        if (hasAnswered.value) {
            kotlinx.coroutines.delay(1000)
            val formatted = formatAnswer(question, selectedOption.value!!)
            onOptionSelected(formatted)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        LevelingTestImage()
        Spacer(modifier = Modifier.height(32.dp))
        LevelingTestQuestion(
            question = question,
            options = options,
            selectedOption = selectedOption.value,
            correctAnswer = correctAnswer,
            onOptionSelected = { answer ->
                if (!hasAnswered.value) {
                    selectedOption.value = answer
                    hasAnswered.value = true
                }
            }
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LevelingTestQuestion(question: String, options : List<String>, selectedOption : String?, correctAnswer: String, onOptionSelected : (String) -> Unit){


    Text(text = question, style = PoppinsTypography().h6, color = Color.Black, fontWeight = FontWeight.Normal, modifier = Modifier.padding(horizontal = 16.dp))
    Spacer(modifier = Modifier.height(24.dp))
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        options.forEachIndexed { index, answer ->

            val isAnswered = selectedOption != null
            val isUserSelected = selectedOption == answer
            val isCorrect = isAnswered && answer == correctAnswer
            val isWrong = isAnswered && isUserSelected && answer != correctAnswer

            AnswerButton(
                text = answer,
                onClick = {
                    onOptionSelected(answer)
                },
                isCorrectAnswer = isCorrect,
                isWrongAnswer = isWrong,
                isSelected = isUserSelected
            )
        }
    }
}

fun formatAnswer(question: String, answer: String) : String{
    return "$question → $answer"
}

@Composable
fun AnswerButton(text : String, onClick : () -> Unit, isCorrectAnswer: Boolean = false, isWrongAnswer: Boolean = false, isSelected: Boolean = false){

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        label = "ScaleAnimation"
    )

    val backgroundColor = when {
        isCorrectAnswer -> Color(0xFF31CD63)
        isWrongAnswer -> Color(0xFFFF6B6B)
        isSelected -> Color(0xFFD3D3D3)
        else -> Color(0xFFEFEFFF)
    }

    Box(
        modifier = Modifier
        .clip(RoundedCornerShape(22))
            .graphicsLayer(scaleX = scale, scaleY = scale)
        .background(backgroundColor)
        .border(1.dp, Color(0xFF8888AA), RoundedCornerShape(22))
        .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ){
        Row{
            Text(
                text = text,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                style = PoppinsTypography().button
            )
        }
    }
}

@Composable
fun TopAppBar(currentQuestions: Int, totalQuestions: Int, onClose : () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        ProgressBar(currentQuestion = currentQuestions, totalQuestions = totalQuestions, onClose = onClose)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Finish the sentence",
            style = PoppinsTypography().h6,
            color = Blue,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LevelingTestImage(){
    Box(
        modifier = Modifier.fillMaxWidth().height(220.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .align(alignment = Alignment.Center)
            ,

            painter = painterResource(Res.drawable.educator),
            contentScale = ContentScale.Fit,
            contentDescription = "",
        )
    }
}



@Composable
fun ProgressBar(
    currentQuestion : Int,
    totalQuestions : Int,
    onClose : () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClose){
            Icon(Icons.Default.Close, contentDescription = "Fechar", tint = Color.Black)
        }
        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.LightGray)
        ){

            val progress = currentQuestion.toFloat() / totalQuestions

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Blue)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$currentQuestion/$totalQuestions",
            style = PoppinsTypography().caption,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
    }
}