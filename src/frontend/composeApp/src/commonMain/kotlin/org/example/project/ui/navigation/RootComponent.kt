package org.example.project.ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.pushToFront
import kotlinx.serialization.Serializable
import org.example.project.ui.screens.auth.AuthViewModel
import org.example.project.ui.screens.signup.SignUpViewModel
import org.example.project.ui.screens.splash.SplashViewModel

class RootComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext{

    private val navigation = StackNavigation<Configuration>()
    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.SplashScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config : Configuration,
        context: ComponentContext
    ): Child{
        return when(config){
            Configuration.SplashScreen -> Child.SplashScreen(
               SplashViewModel(
                   componentContext = context,
                   onNavigateToAuthScreen = {
                       navigation.pushNew(Configuration.AuthScreen)
                   },
                   onNavigateToSignUp = {
                       navigation.pushNew(Configuration.SignUpScreen)
                   }
               )
            )
            is Configuration.AuthScreen -> Child.AuthScreen(
                AuthViewModel(
                    componentContext = context,
                    onNavigateToSignUp = {
                        navigation.pushToFront(Configuration.SignUpScreen)
                    }
                )
            )
            is Configuration.SignUpScreen -> Child.SignUpScreen(
                SignUpViewModel(
                    componentContext = context,
                    onNavigateToAuth = {
                        navigation.pushToFront(Configuration.AuthScreen)
                    }
                )
            )
        }
    }

    sealed class Child {
        data class SplashScreen(val component: SplashViewModel) : Child()
        data class AuthScreen(val component: AuthViewModel) : Child()
        data class SignUpScreen(val component : SignUpViewModel) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object SplashScreen : Configuration()
        @Serializable
        data object AuthScreen: Configuration()
        @Serializable
        data object SignUpScreen : Configuration()
    }
}