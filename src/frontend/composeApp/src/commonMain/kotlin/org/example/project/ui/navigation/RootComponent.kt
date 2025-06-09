package org.example.project.ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.router.stack.replaceCurrent
import org.koin.core.component.get
import kotlinx.serialization.Serializable
import org.example.project.ui.screens.auth.AuthViewModel
import org.example.project.ui.screens.forgotpassword.ForgotPasswordViewModel
import org.example.project.ui.screens.signup.SignUpViewModel
import org.example.project.ui.screens.splash.SplashViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.component.KoinComponent

class RootComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext, KoinComponent{

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
                    ,
                    onNavigateToForgotPassword = {
                        navigation.pushToFront(Configuration.ForgotPasswordScreen)
                    }
                )
            )
            is Configuration.SignUpScreen -> Child.SignUpScreen(
                get<SignUpViewModel>(parameters = {
                   parametersOf(context, {navigation.pushToFront(Configuration.AuthScreen)}, {navigation.replaceCurrent(Configuration.AuthScreen)})
               })
            )

            Configuration.ForgotPasswordScreen -> Child.ForgotPasswordScreen(
                get<ForgotPasswordViewModel>(parameters = {
                    parametersOf(
                        context,
                        { navigation.replaceCurrent(Configuration.AuthScreen) }
                    )
                })
            )
        }
    }

    sealed class Child {
        data class SplashScreen(val component: SplashViewModel) : Child()
        data class AuthScreen(val component: AuthViewModel) : Child()
        data class SignUpScreen(val component : SignUpViewModel) : Child()
        data class ForgotPasswordScreen(val component : ForgotPasswordViewModel) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object SplashScreen : Configuration()
        @Serializable
        data object AuthScreen: Configuration()
        @Serializable
        data object SignUpScreen : Configuration()
        @Serializable
        data object ForgotPasswordScreen : Configuration()
    }
}