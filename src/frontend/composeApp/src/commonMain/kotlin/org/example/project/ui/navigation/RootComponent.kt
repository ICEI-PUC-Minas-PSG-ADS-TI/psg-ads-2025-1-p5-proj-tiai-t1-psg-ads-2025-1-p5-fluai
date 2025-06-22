package org.example.project.ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.router.stack.replaceCurrent
import org.koin.core.component.get
import kotlinx.serialization.Serializable
import org.example.project.domain.model.AuthData
import org.example.project.ui.screens.auth.AuthViewModel
import org.example.project.ui.screens.fluencyboost.FluencyBoostViewModel
import org.example.project.ui.screens.home.HomeViewModel
import org.example.project.ui.screens.learningpath.LearningPathViewModel
import org.example.project.ui.screens.levelingtest.LevelingTestViewModel
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

    val currentConfig = childStack.value.active.configuration

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
                        })
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
                        { authData : AuthData->
                            currentAuthData = authData
                            navigation.replaceCurrent(Configuration.LevelingTest(authData))
                        },
                        config.secondsToAdd
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

            is Configuration.LearningPath -> Child.LearningPath(
                get<LearningPathViewModel>(parameters = {
                    parametersOf(
                        context,
                        config.authData,
                        { authData : AuthData ->
                            currentAuthData = authData
                            navigation.replaceCurrent(Configuration.LevelingTest(authData = authData))
                        },
                        { authData : AuthData ->
                            currentAuthData = authData
                            navigation.replaceCurrent(Configuration.FluencyBoost(authData = authData))
                        }
                    )
                })
            )

            is Configuration.LevelingTest -> Child.LevelingTest(
                get<LevelingTestViewModel>(parameters = {
                    parametersOf(
                        context,
                        config.authData,
                        { authData : AuthData, seconds : Int ->
                            currentAuthData = authData
                            bottomBarController.select(1)
                            navigation.replaceCurrent(Configuration.HomeScreen(authData = authData, seconds))
                        }
                    )
                })
            )

            is Configuration.FluencyBoost -> Child.FluencyBoost(
                get<FluencyBoostViewModel>(parameters = {
                    parametersOf(
                        context,
                        config.authData,
                        { authData : AuthData, seconds : Int ->
                            currentAuthData = authData
                            bottomBarController.select(1)
                            navigation.replaceCurrent(Configuration.HomeScreen(authData = authData, seconds))
                        },
                        { authData : AuthData ->
                            currentAuthData = authData
                            bottomBarController.select(1)
                            navigation.replaceCurrent(Configuration.FluencyBoost(authData = authData))
                        }
                    )
                })
            )
        }
    }



    fun navigateTo(index: Int){
        bottomBarController.select(index)
        when(index){
            0 -> navigation.replaceCurrent(Configuration.LearningPath(authData = currentAuthData!!))
            1 ->  navigation.replaceCurrent(Configuration.HomeScreen(authData = currentAuthData!!, secondsToAdd = 0))
            2 -> navigation.replaceCurrent(Configuration.UserAccount)
        }
    }



    sealed class Child {
        data class SplashScreen(val component: SplashViewModel) : Child()
        data class AuthScreen(val component: AuthViewModel) : Child()
        data class SignUpScreen(val component: SignUpViewModel) : Child()
        data class HomeScreen(val component: HomeViewModel) : Child()
        data class UserAccount(val component: UserAccountViewModel) : Child()
        data class LearningPath(val component : LearningPathViewModel): Child()
        data class LevelingTest(val component: LevelingTestViewModel): Child()
        data class FluencyBoost(val component: FluencyBoostViewModel): Child()
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
        data class HomeScreen(val authData: AuthData, val secondsToAdd : Int = 0) : Configuration()

        @Serializable
        data object UserAccount : Configuration()

        @Serializable
        data class LearningPath(val authData: AuthData) : Configuration()

        @Serializable
        data class LevelingTest(val authData: AuthData) : Configuration()

        @Serializable
        data class FluencyBoost(val authData: AuthData) : Configuration()

    }
}