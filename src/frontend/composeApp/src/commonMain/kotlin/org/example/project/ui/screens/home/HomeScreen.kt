package org.example.project.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import frontend.composeapp.generated.resources.Res
import frontend.composeapp.generated.resources.account
import frontend.composeapp.generated.resources.card_image_1
import frontend.composeapp.generated.resources.card_image_2
import frontend.composeapp.generated.resources.course
import frontend.composeapp.generated.resources.default_avatar
import frontend.composeapp.generated.resources.home
import frontend.composeapp.generated.resources.meetup_image
import org.example.project.theme.Blue
import org.example.project.theme.Cyan
import org.example.project.theme.Light_Purple
import org.example.project.theme.Orange
import org.example.project.theme.Purple
import org.example.project.ui.theme.PoppinsTypography
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(
    viewModel : HomeViewModel
) {
    val selectedIndex = remember { mutableStateOf(1) }
    val scrollState = rememberScrollState()
    val username = remember { viewModel.getName() }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.height(200.dp),
                title = {
                    Column {
                        Text(
                            "Olá, $username",
                            style = PoppinsTypography().h5,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        Text(
                            "Vamos começar!",
                            style = PoppinsTypography().subtitle1,
                            color = Color.White
                        )
                    }
                },
                backgroundColor = Blue,
                actions = {
                    IconButton(onClick = {}){
                        Image(painter = painterResource(Res.drawable.default_avatar), contentDescription = "", modifier = Modifier.padding(end = 16.dp).size(70.dp))
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(selectedIndex = selectedIndex.value, onItemSelected = {selectedIndex.value = it})
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 18.dp).verticalScroll(scrollState)
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    LearningCard(
                        modifier = Modifier,
                        title = "O que você quer aprender?",
                        image = painterResource(Res.drawable.card_image_1),
                    ){
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(backgroundColor = Orange),
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(
                                text = "Começar",
                                style = PoppinsTypography().button,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                item {
                    LearningCard(
                        image = painterResource(Res.drawable.card_image_2),
                        imageAlignment = Alignment.Center,
                    )
                }
            }
            Text(
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                text = "Plano de estudo",
                style = PoppinsTypography().h6,
            )

            Spacer(modifier = Modifier.height(30.dp))
            LearningProgress()
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(130.dp)
            ) {
                Card(modifier = Modifier.fillMaxSize(), backgroundColor = Color(Light_Purple), shape = RoundedCornerShape(12.dp)) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.weight(0.7f)) {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 24.dp)
                            ) {
                                Text(text = "FluAI", color = Color(Purple), style = PoppinsTypography().h4, fontWeight = FontWeight.SemiBold)
                                Text(text = "Domine o inglês com IA!", color = Color(Purple), style = PoppinsTypography().body2, fontWeight = FontWeight.Medium)
                            }
                        }
                        Image(
                            modifier = Modifier.weight(0.3f).padding(top = 24.dp, bottom = 24.dp, end = 16.dp),
                            painter = painterResource(Res.drawable.meetup_image),
                            contentDescription = "",
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun BottomBar(selectedIndex : Int, onItemSelected : (Int) -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val items = listOf("Trilha", "Home", "Conta")
        items.forEachIndexed{ index, label ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable{
                    (onItemSelected(index))
                }
            ) {
                Icon(
                    painter = painterResource(when(label){
                        "Trilha" -> Res.drawable.course
                        "Home" -> Res.drawable.home
                        "Conta" -> Res.drawable.account
                        else -> Res.drawable.home
                    }),
                    contentDescription = label,
                    tint = if (index == selectedIndex) Blue else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = label,
                    style = PoppinsTypography().caption,
                    color = if (index == selectedIndex) Blue else Color.Gray,
                    modifier = Modifier.padding(if(label == "Home") PaddingValues(top = 6.dp, bottom = 6.dp) else PaddingValues())
                )
            }

        }
    }
}

@Composable
fun LearningProgress(minutes : Int = 0, goalMinutes : Int = 0){

    val progress = minutes.toFloat() / goalMinutes.toFloat()

    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp).background(Color.White)
        ) {
            Text(
                text = "Aprendizado de hoje",
                style = PoppinsTypography().body2,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.Start)
            )

            Spacer(Modifier.height(4.dp))
            Text(text = "${minutes}min", style = PoppinsTypography().h5, color = Color.Black, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 4.dp))
            Box(modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(50.dp)).background(Color(0xFFF1EEFA))){
                Box(Modifier.fillMaxHeight().fillMaxWidth(progress.coerceIn(0f,1f)).background(brush = Brush.horizontalGradient(colors = listOf(Color(0xFFF1EEFA), Color(0xFFFF7F50)))))
            }
        }
    }
}

@Composable
fun LearningCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    image: Painter,
    imageAlignment: Alignment = Alignment.CenterEnd,
    content : (@Composable () -> Unit)? = null
) {
    Box(
        modifier = Modifier.padding(top = 30.dp, bottom = 40.dp)
            .background(Cyan, shape = RoundedCornerShape(12.dp))
            .width(250.dp).height(150.dp)
    ) {
        Image(
            modifier = modifier.padding(top = 6.dp).align(imageAlignment).size(130.dp),
            painter = image,
            contentDescription = "",
        )

        title?.let {
            Text(
                text = it,
                style = PoppinsTypography().subtitle1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, start = 12.dp).align(Alignment.TopStart)
                    .fillMaxWidth()
            )
        }

        Box(modifier = Modifier.align(Alignment.BottomStart).padding(8.dp),) {
            content?.invoke()
        }



    }
}