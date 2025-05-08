package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.DatabaseProvider
import org.example.project.data.database.AppDatabase
import org.example.project.data.database.local.user.UserLocalDataSource
import org.example.project.data.database.local.user.UserLocalDataSourceImpl
import org.example.project.data.networking.SignUpNetworking
import org.example.project.data.networking.SignUpNetworkingImpl
import org.example.project.data.repository.SignUpRepositoryImpl
import org.example.project.domain.repository.SignUpRepository
import org.example.project.domain.service.KtorApiClient
import org.example.project.domain.usecase.AuthUseCase
import org.example.project.domain.usecase.AuthUseCaseImpl
import org.example.project.domain.usecase.SignUpUseCase
import org.example.project.domain.usecase.SignUpUseCaseImpl
import org.example.project.ui.screens.auth.AuthViewModel
import org.example.project.ui.screens.signup.SignUpViewModel
import org.koin.dsl.module


val dataModules = module {
    single<AppDatabase> { DatabaseProvider.createDatabase(get()) }
    single { get<AppDatabase>().userDao() }
    single<UserLocalDataSource> { UserLocalDataSourceImpl(get()) }
    single<SignUpNetworking> { SignUpNetworkingImpl(httpClient = KtorApiClient.getClient("")) }
    single<SignUpRepository> { SignUpRepositoryImpl(get(), get()) }
    single<SignUpUseCase> { SignUpUseCaseImpl(get()) }

    single<AuthUseCase> {AuthUseCaseImpl()}

    factory { (componentContext: ComponentContext, onNavigateToAuth: () -> Unit, onNavigateToAuthBySignUp: () -> Unit) ->
        SignUpViewModel(
            componentContext = componentContext,
            signUpUseCase = get(),
            onNavigateToAuthBySignUp = onNavigateToAuthBySignUp,
            onNavigateToAuth = onNavigateToAuth
        )
    }

    factory { (componentContext: ComponentContext, onNavigateToSignUp: () -> Unit) ->
        AuthViewModel(
            componentContext = componentContext,
            authUseCase = get(),
            onNavigateToSignUp = onNavigateToSignUp
        )
    }
}

