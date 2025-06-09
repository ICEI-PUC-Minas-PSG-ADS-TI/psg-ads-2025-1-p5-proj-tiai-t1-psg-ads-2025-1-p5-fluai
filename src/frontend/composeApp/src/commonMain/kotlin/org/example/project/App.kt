package org.example.project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.example.project.ui.navigation.RootComponent
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.ui.screens.auth.AuthScreen
import org.example.project.ui.screens.forgotpassword.ForgotPasswordScreen
import org.example.project.ui.screens.signup.SignUpScreen
import org.example.project.ui.screens.splash.SplashScreen


@Composable
fun App(root : RootComponent){
    MaterialTheme {
        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ){ child ->
            when(val instance = child.instance){
                is RootComponent.Child.AuthScreen -> AuthScreen(instance.component)
                is RootComponent.Child.SplashScreen -> SplashScreen(instance.component)
                is RootComponent.Child.SignUpScreen -> SignUpScreen(instance.component)
                is RootComponent.Child.ForgotPasswordScreen -> ForgotPasswordScreen(instance.component)
            }
        }
    }
}







