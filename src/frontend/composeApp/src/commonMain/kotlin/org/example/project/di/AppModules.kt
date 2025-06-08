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
import org.example.project.data.networking.LevelingTestNetworking
import org.example.project.data.networking.LevelingTestNetworkingImpl
import org.example.project.data.networking.SignUpNetworking
import org.example.project.data.networking.SignUpNetworkingImpl
import org.example.project.data.repository.AuthRepositoryImpl
import org.example.project.data.repository.LevelingTestRepositoryImpl
import org.example.project.data.repository.SessionRepositoryImpl
import org.example.project.data.repository.SignUpRepositoryImpl
import org.example.project.domain.model.AuthData
import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.repository.LevelingTestRepository
import org.example.project.domain.repository.SessionRepository
import org.example.project.domain.repository.SignUpRepository
import org.example.project.domain.service.KtorApiClient
import org.example.project.domain.usecase.AuthUseCase
import org.example.project.domain.usecase.AuthUseCaseImpl
import org.example.project.domain.usecase.LevelingTestUseCase
import org.example.project.domain.usecase.LevelingTestUseCaseImpl
import org.example.project.domain.usecase.SignUpUseCase
import org.example.project.domain.usecase.SignUpUseCaseImpl
import org.example.project.ui.screens.auth.AuthViewModel
import org.example.project.ui.screens.home.HomeViewModel
import org.example.project.ui.screens.learningpath.LearningPathViewModel
import org.example.project.ui.screens.levelingtest.LevelingTestViewModel
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


    single<SignUpNetworking> { SignUpNetworkingImpl(httpClient = KtorApiClient.getClient("")) }
    single<SignUpRepository> { SignUpRepositoryImpl(get(), get()) }
    single<SignUpUseCase> { SignUpUseCaseImpl(get()) }

    single<SessionRepository> { SessionRepositoryImpl(get(), get()) }


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

    factory { (componentContext: ComponentContext, onNavigateToSignUp: () -> Unit, onNavigateToHome: (AuthData) -> Unit) ->
        AuthViewModel(
            componentContext = componentContext,
            authUseCase = get(),
            onNavigateToSignUp = onNavigateToSignUp,
            onNavigationToHome = onNavigateToHome
        )
    }

    factory { (componentContext: ComponentContext, authData : AuthData) ->
        HomeViewModel(
            componentContext = componentContext,
            authData = authData,
        )
    }

    factory { (componentContext: ComponentContext, onNavigateToSplashScreen : () -> Unit) ->
        UserAccountViewModel(
            componentContext = componentContext,
            onLogout = onNavigateToSplashScreen,
            sessionRepository = get()
        )
    }

    factory { (componentContext: ComponentContext, onNavigateToLevelingTest : () -> Unit) ->
        LearningPathViewModel(
            componentContext = componentContext,
            onNavigateToLevelingTest = onNavigateToLevelingTest
        )
    }

    factory { (componentContext: ComponentContext) ->
        LevelingTestViewModel(
            componentContext = componentContext,
            levelingTestUseCase = get()
        )
    }
}

