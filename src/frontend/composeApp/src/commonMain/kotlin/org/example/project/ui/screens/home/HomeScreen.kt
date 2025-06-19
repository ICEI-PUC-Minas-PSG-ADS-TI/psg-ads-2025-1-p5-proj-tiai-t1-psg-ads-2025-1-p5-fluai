package org.example.project.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
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
import frontend.composeapp.generated.resources.card_image_1
import frontend.composeapp.generated.resources.card_image_2
import frontend.composeapp.generated.resources.default_avatar
import frontend.composeapp.generated.resources.home_banner_subtitle
import frontend.composeapp.generated.resources.home_banner_title
import frontend.composeapp.generated.resources.home_learning_card_button
import frontend.composeapp.generated.resources.home_learning_card_title
import frontend.composeapp.generated.resources.home_learning_progress_text
import frontend.composeapp.generated.resources.home_study_plan_title
import frontend.composeapp.generated.resources.home_subtitle_label
import frontend.composeapp.generated.resources.meetup_image
import org.example.project.theme.Blue
import org.example.project.theme.Cyan
import org.example.project.theme.Light_Purple
import org.example.project.theme.Orange
import org.example.project.theme.Purple
import org.example.project.ui.components.dialog.showAlertDialog
import org.example.project.ui.theme.PoppinsTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val scrollState = rememberScrollState()
    val username = remember { viewModel.getName() }

    Column(
        modifier = Modifier.fillMaxSize().padding()
            .verticalScroll(scrollState)
    ) {
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
                        text = stringResource(Res.string.home_subtitle_label),
                        style = PoppinsTypography().subtitle1,
                        color = Color.White
                    )
                }
            },
            backgroundColor = Blue,
            actions = {
                IconButton(onClick = {}) {
                    Image(
                        painter = painterResource(Res.drawable.default_avatar),
                        contentDescription = "",
                        modifier = Modifier.padding(end = 16.dp).size(70.dp)
                    )
                }
            }
        )
        showAlertDialog(viewModel.showTestDialog, message = "Você precisa completar o teste inicial de nivelamento para continuar", onClick = {viewModel.onEvent(HomeEvent.GoToLevelingTest)})
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp)
        ) {
            item {
                LearningCard(
                    modifier = Modifier,
                    title = stringResource(Res.string.home_learning_card_title),
                    image = painterResource(Res.drawable.card_image_1),
                ) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(backgroundColor = Orange),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            text = stringResource(Res.string.home_learning_card_button),
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
            modifier = Modifier.padding(horizontal = 18.dp),
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            text = stringResource(Res.string.home_study_plan_title),
            style = PoppinsTypography().h6,
        )

        Spacer(modifier = Modifier.height(30.dp))
        LearningProgress()
        Spacer(modifier = Modifier.height(30.dp))
        HomeBanner()
        Spacer(modifier = Modifier.height(16.dp))
    }

}

@Composable
private fun HomeBanner() {
    Box(
        modifier = Modifier.fillMaxWidth().height(130.dp).padding(horizontal = 18.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = Light_Purple,
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(0.7f)) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 24.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.home_banner_title),
                            color = Purple,
                            style = PoppinsTypography().h4,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = stringResource(Res.string.home_banner_subtitle),
                            color = Purple,
                            style = PoppinsTypography().body2,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Image(
                    modifier = Modifier.weight(0.3f)
                        .padding(top = 24.dp, bottom = 24.dp, end = 16.dp),
                    painter = painterResource(Res.drawable.meetup_image),
                    contentDescription = "",
                )
            }
        }
    }
}

@Composable
fun LearningProgress(minutes: Int = 0, goalMinutes: Int = 0) {

    val progress = minutes.toFloat() / goalMinutes.toFloat()

    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp).background(Color.White)
        ) {
            Text(
                text = stringResource(Res.string.home_learning_progress_text),
                style = PoppinsTypography().body2,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.Start)
            )

            Spacer(Modifier.height(4.dp))
            Text(
                text = "${minutes}min",
                style = PoppinsTypography().h5,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Box(
                modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFF1EEFA))
            ) {
                Box(
                    Modifier.fillMaxHeight().fillMaxWidth(progress.coerceIn(0f, 1f)).background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFF1EEFA),
                                Color(0xFFFF7F50)
                            )
                        )
                    )
                )
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
    content: (@Composable () -> Unit)? = null
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

        Box(modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)) {
            content?.invoke()
        }
    }
}