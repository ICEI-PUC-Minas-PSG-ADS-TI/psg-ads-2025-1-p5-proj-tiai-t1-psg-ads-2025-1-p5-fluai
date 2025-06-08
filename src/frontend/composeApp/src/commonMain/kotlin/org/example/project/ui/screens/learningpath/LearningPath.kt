package org.example.project.ui.screens.learningpath

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import frontend.composeapp.generated.resources.Res
import frontend.composeapp.generated.resources.learning_path
import frontend.composeapp.generated.resources.learning_path_banner_text
import frontend.composeapp.generated.resources.learning_path_test_card_text
import frontend.composeapp.generated.resources.learning_path_test_fluency_boost
import frontend.composeapp.generated.resources.learning_path_test_smart_challenges
import frontend.composeapp.generated.resources.learning_path_title
import org.example.project.theme.Blue
import org.example.project.theme.Light_Blue
import org.example.project.theme.Violet
import org.example.project.theme.Yellow
import org.example.project.ui.theme.PoppinsTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LearningPath(
    viewModel: LearningPathViewModel
){
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = stringResource(Res.string.learning_path_title), style = PoppinsTypography().h5, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 24.dp))
            Spacer(modifier = Modifier.height(32.dp))
            LearningPathBanner(modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(32.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                TestCards(color = Yellow, title = stringResource(Res.string.learning_path_test_smart_challenges)){
                    viewModel.onEvent(LearningPathEvent.GoToLevelingTest)
                }
                Spacer(modifier = Modifier.width(16.dp))
                TestCards(color = Light_Blue, title = stringResource(Res.string.learning_path_test_fluency_boost))
            }

        }
    }
}

@Composable
fun TestCards(color: Color, title: String, completed: Int? = 0, onClick: (() -> Unit?)? = null){
    Box(
        modifier = Modifier.height(190.dp).width(160.dp)
            .background(color, shape = RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, top = 24.dp)) {
            Text(
                text = title,
                style = PoppinsTypography().h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(text = stringResource(Res.string.learning_path_test_card_text), style = PoppinsTypography().body2)
            Text(
                text = completed.toString(),
                style = PoppinsTypography().h6,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier.align(Alignment.BottomEnd).padding(12.dp).size(40.dp).clip(CircleShape).background(Blue),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = {onClick?.invoke()}){
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun LearningPathBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.width(290.dp).height(160.dp).background(Violet, shape = RoundedCornerShape(12.dp)),
    ) {
        Image(
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .width(160.dp)
                .aspectRatio(270f / 202f)
            ,

            painter = painterResource(Res.drawable.learning_path),
            contentScale = ContentScale.Fit,
            contentDescription = "",
        )
        Text(
            text = stringResource(Res.string.learning_path_banner_text),
            style = PoppinsTypography().h5,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.TopStart).padding(top = 12.dp, start = 12.dp,end = 16.dp).width(200.dp),
            )
    }
}