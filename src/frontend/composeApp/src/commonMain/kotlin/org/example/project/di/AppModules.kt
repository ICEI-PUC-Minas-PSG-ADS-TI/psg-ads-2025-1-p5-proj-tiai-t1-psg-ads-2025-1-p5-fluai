package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.data.networking.SignUpNetworking
import org.example.project.data.networking.SignUpNetworkingImpl
import org.example.project.data.repository.SignUpRepositoryImpl
import org.example.project.domain.repository.SignUpRepository
import org.example.project.domain.service.KtorApiClient
import org.example.project.domain.usecase.SignUpUseCase
import org.example.project.domain.usecase.SignUpUseCaseImpl
import org.example.project.ui.screens.signup.SignUpViewModel
import org.example.project.ui.screens.signup.model.UserDataValidator
import org.koin.dsl.module


val dataModules = module {
    single<SignUpNetworking>{ SignUpNetworkingImpl(httpClient = KtorApiClient.httpClient) }
    single<SignUpRepository>{ SignUpRepositoryImpl(get()) }
    single<SignUpUseCase>{ SignUpUseCaseImpl(get()) }
    factory { UserDataValidator() }

    factory { (componentContext: ComponentContext, onNavigateToAuth: () -> Unit, onNavigateToAuthBySignUp: () -> Unit) ->
        SignUpViewModel(
            userDataValidator = get(),
            componentContext = componentContext,
            signUpUseCase = get(),
            onNavigateToAuthBySignUp = onNavigateToAuthBySignUp,
            onNavigateToAuth = onNavigateToAuth
        )
    }
}

