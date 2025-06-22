package org.example.project.ui.screens.useraccount

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import frontend.composeapp.generated.resources.Res
import frontend.composeapp.generated.resources.default_avatar
import frontend.composeapp.generated.resources.user_account_edit_profile_text
import frontend.composeapp.generated.resources.user_account_logout_text
import frontend.composeapp.generated.resources.user_account_title
//import frontend.composeapp.generated.resources.default_avatar
import org.example.project.ui.theme.PoppinsTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserAccount(
    viewModel: UserAccountViewModel
){
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 30.dp)
        ) {
            Text(text = stringResource(Res.string.user_account_title), style = PoppinsTypography().h5, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.fillMaxWidth().align(Alignment.Start))
            Spacer(modifier = Modifier.size(16.dp))
            IconButton(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),  onClick = {}){
                Image(painter = painterResource(Res.drawable.default_avatar), contentDescription = "", modifier = Modifier.padding(end = 16.dp).size(100.dp))
            }
            Spacer(modifier = Modifier.size(30.dp))
            TextButton(onClick = {}){ Text(text = stringResource(Res.string.user_account_edit_profile_text), style = PoppinsTypography().subtitle1, fontWeight = FontWeight.SemiBold,  color = Color.Black, modifier = Modifier.fillMaxWidth()) }
            Spacer(modifier = Modifier.size(16.dp))
            TextButton(onClick = {viewModel.onEvent(UserAccountEvent.Logout)}){ Text(text = stringResource(Res.string.user_account_logout_text), style = PoppinsTypography().subtitle1, fontWeight = FontWeight.SemiBold, color = Color.Black, modifier = Modifier.fillMaxWidth()) }
        }
    }
}