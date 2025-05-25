package org.example.project.ui.screens.levelingtest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import frontend.composeapp.generated.resources.Res
import frontend.composeapp.generated.resources.educator
import org.example.project.theme.Blue
import org.example.project.ui.theme.PoppinsTypography
import org.jetbrains.compose.resources.painterResource

@Composable
fun LevelingTest() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val answers = listOf("on vacation", "in vacation")

            Spacer(modifier = Modifier.height(60.dp))
            LevelingTestImage()
            Spacer(modifier = Modifier.height(32.dp))
            LevelingTestQuestion(question = "Did you know that in the USA people say “____________”, but in the UK people normally say “on holiday”?", answers = answers, {})
        }
    }
}


@Composable
fun LevelingTestQuestion(question: String, answers : List<String>, onOptionSelected : (String) -> Unit){
    Text(text = question, style = PoppinsTypography().h6, color = Color.Black, fontWeight = FontWeight.Normal, modifier = Modifier.padding(horizontal = 16.dp))
    Spacer(modifier = Modifier.height(24.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        answers.forEach {
            AnswerButton(text = it, onClick = {})
        }
    }
}

@Composable
fun AnswerButton(text : String, onClick : () -> Unit){
    Box(
        modifier = Modifier
        .clip(RoundedCornerShape(50))
        .background(Color(0xFFEFEFFF))
        .border(1.dp, Color(0xFF8888AA), RoundedCornerShape(50))
        .padding(horizontal = 20.dp, vertical = 12.dp)
        .clickable {
            onClick()
        }
    ){
        Text(text = text, color = Color.Black, style = PoppinsTypography().button)
    }
}

@Composable
fun TopAppBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        ProgressBar(currentQuestion = 0, totalQuestions = 0, onClose = {})

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
        IconButton(onClick = {}){
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