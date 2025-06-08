package org.example.project

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import org.example.project.ui.navigation.RootComponent
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.ui.screens.auth.AuthScreen
import org.example.project.ui.screens.home.HomeScreen
import org.example.project.ui.screens.learningpath.LearningPath
import org.example.project.ui.screens.levelingtest.LevelingTest
import org.example.project.ui.screens.signup.SignUpScreen
import org.example.project.ui.screens.splash.SplashScreen
import org.example.project.ui.screens.useraccount.UserAccount
import org.example.project.ui.utils.BottomBar

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App(rootComponent: RootComponent){
    val childStack = rootComponent.childStack.subscribeAsState()
    val currentChild = childStack.value.active.instance

    val showBottomBar = when(currentChild){
        is RootComponent.Child.HomeScreen,
        is RootComponent.Child.LearningPath,
        is RootComponent.Child.UserAccount -> true
        else -> false
    }

    Scaffold(bottomBar = {
        if (showBottomBar){
            BottomBar(
                controller = rootComponent.bottomBarController,
                onItemClick = {index -> rootComponent.navigateTo(index)}
            )
        }
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            AnimatedContent(
                targetState = currentChild,
                transitionSpec = {
                    slideInHorizontally { width -> width } + fadeIn() with
                    slideOutHorizontally { width -> -width } + fadeOut()
                }
            ) { targetChild ->
                when(targetChild){
                    is RootComponent.Child.SplashScreen -> SplashScreen(targetChild.component)
                    is RootComponent.Child.AuthScreen -> AuthScreen(targetChild.component)
                    is RootComponent.Child.SignUpScreen -> SignUpScreen(targetChild.component)
                    is RootComponent.Child.HomeScreen -> HomeScreen(targetChild.component)
                    is RootComponent.Child.UserAccount -> UserAccount(targetChild.component)
                    is RootComponent.Child.LearningPath -> LearningPath(targetChild.component)
                    is RootComponent.Child.LevelingTest -> LevelingTest(targetChild.component)
                }
            }

        }
    }
}






