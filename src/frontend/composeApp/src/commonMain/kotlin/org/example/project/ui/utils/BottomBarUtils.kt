package org.example.project.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import frontend.composeapp.generated.resources.Res
import frontend.composeapp.generated.resources.account
import frontend.composeapp.generated.resources.course
import frontend.composeapp.generated.resources.home
import org.example.project.theme.Blue
import org.example.project.ui.navigation.BottomBarController
import org.example.project.ui.theme.PoppinsTypography
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomBar(controller: BottomBarController, onItemClick: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val selectedIndex by controller.selectedIndex


        val items = listOf("Trilha", "Home", "Conta")
        items.forEachIndexed { index, label ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    controller.select(index)
                    onItemClick(index)
                }
            ) {
                Icon(
                    painter = painterResource(
                        when (label) {
                            "Trilha" -> Res.drawable.course
                            "Home" -> Res.drawable.home
                            "Conta" -> Res.drawable.account
                            else -> Res.drawable.home
                        }
                    ),
                    contentDescription = label,
                    tint = if (index == selectedIndex) Blue else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = label,
                    style = PoppinsTypography().caption,
                    color = if (index == selectedIndex) Blue else Color.Gray,
                    modifier = Modifier.padding(
                        if (label == "Home") PaddingValues(
                            top = 6.dp,
                            bottom = 6.dp
                        ) else PaddingValues()
                    )
                )
            }

        }
    }
}