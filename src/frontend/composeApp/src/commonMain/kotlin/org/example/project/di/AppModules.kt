package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.example.project.AuthDataSource
import org.example.project.AuthDataSourceImpl
import org.example.project.DatabaseProvider
import org.example.project.data.database.AppDatabase
import org.example.project.data.database.local.user.UserLocalDataSource
import org.example.project.data.database.local.user.UserLocalDataSourceImpl
import org.example.project.data.networking.HomeNetworking
import org.example.project.data.networking.HomeNetworkingImpl
import org.example.project.data.networking.LevelingTestNetworking
import org.example.project.data.networking.LevelingTestNetworkingImpl
import org.example.project.data.networking.SignUpNetworking
import org.example.project.data.networking.SignUpNetworkingImpl
import org.example.project.data.repository.AuthRepositoryImpl
import org.example.project.data.repository.ForgotPasswordRepositoryImpl
import org.example.project.data.repository.HomeRepositoryImpl
import org.example.project.data.repository.LevelingTestRepositoryImpl
import org.example.project.data.repository.SessionRepositoryImpl
import org.example.project.data.repository.SignUpRepositoryImpl
import org.example.project.domain.model.AuthData
import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.repository.ForgotPasswordRepository
import org.example.project.domain.repository.HomeRepository
import org.example.project.domain.repository.LevelingTestRepository
import org.example.project.domain.repository.SessionRepository
import org.example.project.domain.repository.SignUpRepository
import org.example.project.domain.service.KtorApiClient
import org.example.project.domain.usecase.AuthUseCase
import org.example.project.domain.usecase.AuthUseCaseImpl
import org.example.project.domain.usecase.ForgotPasswordUseCase
import org.example.project.domain.usecase.ForgotPasswordUseCaseImpl
import org.example.project.domain.usecase.HomeUseCase
import org.example.project.domain.usecase.HomeUseCaseImpl
import org.example.project.domain.usecase.LevelingTestUseCase
import org.example.project.domain.usecase.LevelingTestUseCaseImpl
import org.example.project.domain.usecase.SignUpUseCase
import org.example.project.domain.usecase.SignUpUseCaseImpl
import org.example.project.ui.screens.auth.AuthViewModel
import org.example.project.ui.screens.fluencyboost.FluencyBoostViewModel
import org.example.project.ui.screens.forgotpassword.ForgotPasswordViewModel
import org.example.project.ui.screens.home.HomeViewModel
import org.example.project.ui.screens.learningpath.LearningPathViewModel
import org.example.project.ui.screens.levelingtest.LevelingTestViewModel
import org.example.project.ui.screens.resetpassword.ResetPasswordViewModel
import org.example.project.ui.screens.signup.SignUpViewModel
import org.example.project.ui.screens.splash.SplashViewModel
import org.example.project.ui.screens.useraccount.UserAccountViewModel
import org.koin.dsl.module


val dataModules = module {
    single<AuthDataSource> {AuthDataSourceImpl()}
    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }

    single<AppDatabase> { DatabaseProvider.createDatabase()}
    single { get<AppDatabase>().userDao() }
    single<UserLocalDataSource> { UserLocalDataSourceImpl(get()) }

    single<HomeNetworking> { HomeNetworkingImpl(httpClient = KtorApiClient.getClient("")) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<HomeUseCase> { HomeUseCaseImpl(get()) }



    single<SignUpNetworking> { SignUpNetworkingImpl(httpClient = KtorApiClient.getClient("")) }
    single<SignUpRepository> { SignUpRepositoryImpl(get(), get()) }
    single<SignUpUseCase> { SignUpUseCaseImpl(get()) }

    single<SessionRepository> { SessionRepositoryImpl(get(), get()) }


    single<ForgotPasswordUseCase> {ForgotPasswordUseCaseImpl(get())}
    single<ForgotPasswordRepository> {ForgotPasswordRepositoryImpl(get())}
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    single<AuthUseCase> {AuthUseCaseImpl(get())}
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    single<LevelingTestUseCase> {LevelingTestUseCaseImpl(get())}
    single<LevelingTestRepository> {LevelingTestRepositoryImpl(get())}
    single<LevelingTestNetworking> {LevelingTestNetworkingImpl(httpClient = KtorApiClient.getClient(""))}


    factory { (componentContext: ComponentContext, onNavigateToAuth: () -> Unit, onNavigateToAuthBySignUp: () -> Unit) ->
        SignUpViewModel(
            componentContext = componentContext,
            signUpUseCase = get(),
            onNavigateToAuthBySignUp = onNavigateToAuthBySignUp,
            onNavigateToAuth = onNavigateToAuth
        )
    }


    factory { (componentContext: ComponentContext, onNavigateToAuth: () -> Unit, onNavigateToSignUp: () -> Unit, onNavigateToHome: (AuthData) -> Unit) ->
        SplashViewModel(
            componentContext = componentContext,
            onNavigateToSignUp = onNavigateToSignUp,
            onNavigateToAuthScreen = onNavigateToAuth,
            onNavigateToHome = onNavigateToHome,
            sessionRepository = get()
            )
    }

    factory { (componentContext: ComponentContext, onNavigateToSignUp: () -> Unit, onNavigateToHome: (AuthData) -> Unit, onNavigateToForgotPasswordScreen: () -> Unit) ->
        AuthViewModel(
            componentContext = componentContext,
            authUseCase = get(),
            onNavigateToSignUp = onNavigateToSignUp,
            onNavigationToHome = onNavigateToHome,
            onNavigateToForgotPasswordScreen = onNavigateToForgotPasswordScreen
        )
    }

    factory { (componentContext: ComponentContext, onNavigateBack: () -> Unit, onNavigateToResetLinkScreen: (String) -> Unit, forgotPasswordUseCase: () -> Unit) ->
        ForgotPasswordViewModel(
            componentContext = componentContext,
            onNavigateBack = onNavigateBack,
            onNavigateToResetLinkScreen = onNavigateToResetLinkScreen,
            forgotPasswordUseCase = get()
        )
    }

    factory { (componentContext: ComponentContext, oobCode: String, onPasswordResetSuccess: () -> Unit) ->
        ResetPasswordViewModel(
            componentContext = componentContext,
            oobCode = oobCode,
            onPasswordResetSuccess = onPasswordResetSuccess,
            resetPasswordUseCase = get()
        )
    }


    factory { (componentContext: ComponentContext, authData : AuthData, navigateToLevelingTest : (AuthData) -> Unit, secondsToAdd : Int) ->
        HomeViewModel(
            componentContext = componentContext,
            homeUseCase = get(),
            userLocalDataSource = get(),
            authData = authData,
            navigateToLevelingTest = navigateToLevelingTest,
            secondsToAdd = secondsToAdd
        )
    }

    factory { (componentContext: ComponentContext, onNavigateToSplashScreen : () -> Unit) ->
        UserAccountViewModel(
            componentContext = componentContext,
            onLogout = onNavigateToSplashScreen,
            sessionRepository = get()
        )
    }

    factory { (componentContext: ComponentContext, authData: AuthData, onNavigateToLevelingTest : (AuthData) -> Unit, onNavigateToFluencyBoost : (AuthData) -> Unit) ->
        LearningPathViewModel(
            componentContext = componentContext,
            authData = authData,
            userLocalDataSource = get(),
            onNavigateToLevelingTest = onNavigateToLevelingTest,
            onNavigateToFluencyBoost = onNavigateToFluencyBoost
        )
    }

    factory { (componentContext: ComponentContext, authData: AuthData, onNavigateToHome : (AuthData, Int) -> Unit) ->
        LevelingTestViewModel(
            componentContext = componentContext,
            homeUseCase = get(),
            userLocalDataSource = get(),
            levelingTestUseCase = get(),
            authData = authData,
            onNavigateToHome = onNavigateToHome,
        )
    }

    factory { (componentContext: ComponentContext, authData: AuthData, onNavigateToHome : (AuthData, Int) -> Unit) ->
        FluencyBoostViewModel(
            componentContext = componentContext,
            authData = authData,
            userLocalDataSource = get(),
            levelingTestUseCase = get(),
            navigateToHome = onNavigateToHome
        )
    }
}