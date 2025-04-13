package org.example.project.ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.serialization.Serializable
import org.example.project.ui.screens.auth.AuthScreenComponent
import org.example.project.ui.screens.splash.SplashScreenComponent

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
               SplashScreenComponent(
                   componentContext = context,
                   onNavigateToAuthScreen = {
                       navigation.pushNew(Configuration.AuthScreen)
                   }
               )
            )
            is Configuration.AuthScreen -> Child.AuthScreen(
                AuthScreenComponent(context)
            )
        }
    }

    sealed class Child {
        data class SplashScreen(val component: SplashScreenComponent) : Child()
        data class AuthScreen(val component: AuthScreenComponent) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object SplashScreen : Configuration()
        @Serializable
        data object AuthScreen: Configuration()

    }
}