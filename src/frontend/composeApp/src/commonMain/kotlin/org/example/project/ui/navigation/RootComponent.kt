package org.example.project.ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.router.stack.replaceCurrent
import org.koin.core.component.get
import kotlinx.serialization.Serializable
import org.example.project.domain.model.AuthData
import org.example.project.ui.screens.auth.AuthViewModel
import org.example.project.ui.screens.forgotpassword.ForgotPasswordViewModel
import org.example.project.ui.screens.home.HomeViewModel
import org.example.project.ui.screens.resetpassword.ResetPasswordViewModel
import org.example.project.ui.screens.signup.SignUpViewModel
import org.example.project.ui.screens.splash.SplashViewModel
import org.example.project.ui.screens.useraccount.UserAccountViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.component.KoinComponent

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, KoinComponent {

    private val navigation = StackNavigation<Configuration>()
    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.SplashScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun logout(){
        navigation.navigate { listOf(Configuration.SplashScreen) }
        currentAuthData = null
        bottomBarController.select(1)
    }


    private var currentAuthData: AuthData? = null

    val bottomBarController = BottomBarController()

    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {
            Configuration.SplashScreen -> Child.SplashScreen(
                get<SplashViewModel>(parameters = {
                    parametersOf(
                        context,
                        { navigation.pushNew(Configuration.AuthScreen)},
                        { navigation.pushNew(Configuration.SignUpScreen)},
                        {  authData: AuthData ->
                            currentAuthData = authData
                            navigation.replaceCurrent(Configuration.HomeScreen(authData = authData))}
                    )
                })
            )

            is Configuration.AuthScreen -> Child.AuthScreen(
                get<AuthViewModel>(parameters = {
                    parametersOf(
                        context,
                        { navigation.pushToFront(Configuration.SignUpScreen) },
                        { authData: AuthData ->
                            currentAuthData = authData
                            navigation.replaceCurrent(
                                Configuration.HomeScreen(authData)
                            )
                        },
                        { navigation.pushNew(Configuration.ForgotPasswordScreen) }
                    )
                })
            )

            is Configuration.SignUpScreen -> Child.SignUpScreen(
                get<SignUpViewModel>(parameters = {
                    parametersOf(
                        context,
                        { navigation.pushToFront(Configuration.AuthScreen) },
                        { navigation.replaceCurrent(Configuration.AuthScreen) })
                })
            )

            is Configuration.HomeScreen ->
                Child.HomeScreen(
                get<HomeViewModel>(parameters = {
                    parametersOf(
                        context,
                        config.authData,
                    )
                })
            )

            is Configuration.UserAccount -> Child.UserAccount(
                get<UserAccountViewModel>(parameters = {
                    parametersOf(
                        context,
                        ::logout
                    )
                })
            )

            is Configuration.ForgotPasswordScreen -> Child.ForgotPasswordScreen(
                get<ForgotPasswordViewModel>(parameters = {
                    parametersOf(
                        context,
                        { navigation.pop() },
                        { _: String -> println("Reset link recebido!") }
                    )
                })
            )

            is Configuration.ResetPasswordScreen -> Child.ResetPasswordScreen(
                get<ResetPasswordViewModel>(parameters = {
                    parametersOf(
                        context,
                        config.resetLink,
                        { navigation.replaceCurrent(Configuration.AuthScreen) }
                    )
                })
            )
        }
    }



    fun navigateTo(index: Int){
        bottomBarController.select(index)
        when(index){
            1 ->  navigation.replaceCurrent(Configuration.HomeScreen(authData = currentAuthData!!))
            2 -> navigation.replaceCurrent(Configuration.UserAccount)
        }
    }



    sealed class Child {
        data class SplashScreen(val component: SplashViewModel) : Child()
        data class AuthScreen(val component: AuthViewModel) : Child()
        data class SignUpScreen(val component: SignUpViewModel) : Child()
        data class HomeScreen(val component: HomeViewModel) : Child()
        data class UserAccount(val component: UserAccountViewModel) : Child()
        data class ForgotPasswordScreen(val component: ForgotPasswordViewModel) : Child()
        data class ResetPasswordScreen(val component: ResetPasswordViewModel) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object SplashScreen : Configuration()

        @Serializable
        data object AuthScreen : Configuration()

        @Serializable
        data object SignUpScreen : Configuration()

        @Serializable
        data class HomeScreen(val authData: AuthData) : Configuration()

        @Serializable
        data object UserAccount : Configuration()

        @Serializable
        data object ForgotPasswordScreen : Configuration()

        @Serializable
        data class ResetPasswordScreen(val resetLink: String) : Configuration()
    }
}