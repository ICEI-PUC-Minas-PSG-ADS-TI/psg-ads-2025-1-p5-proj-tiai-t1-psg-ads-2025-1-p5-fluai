package org.example.project.ui.components.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import org.example.project.theme.Blue

@Composable
fun LoadingComponent(isLoading : Boolean){
    if (isLoading){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(0.3f))
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent()
                    }
                }
            }
        ){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = Blue,
            )
        }
    }
}