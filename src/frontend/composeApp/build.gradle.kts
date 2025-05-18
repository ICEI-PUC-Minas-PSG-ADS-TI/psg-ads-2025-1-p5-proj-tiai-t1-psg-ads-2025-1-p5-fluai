import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.googleGms)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerializatin)
    id("com.google.devtools.ksp") version libs.versions.ksp.get()
}

if (System.getenv("CI") != "true") {
    android {
        compileSdk = 34
        // ...
    }
}


kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {

        androidMain.dependencies {
            implementation(libs.firebase.auth.ktx)
            implementation(libs.kotlinx.coroutines.play.services)
            implementation(libs.google.firebase.auth)
            implementation(compose.preview)
            implementation(libs.koin.android)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
            implementation(compose.runtime)
            implementation(libs.androidx.material)
            implementation(libs.decompose)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(compose.runtime)
            implementation(libs.koin.core)
        }
        commonMain.dependencies {
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.common)
            implementation(libs.gitlive.firebase.app)
            implementation(libs.firebase.firestore)
            implementation(libs.decompose)
            implementation(libs.material.icons.extended)
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.decompose.extensions.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
            implementation(libs.koin.coroutines)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.json)
            implementation(libs.ktor.client)
            implementation(libs.ktor.negotiation)
            implementation(libs.ktor.logging)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlinx.coroutines.core)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        jniLibs {
            keepDebugSymbols.add("**/*.so")
        }
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("debug") {
            ndk {
                debugSymbolLevel = "none"
            }
        }
        getByName("release") {
            isMinifyEnabled = false
            ndk {
                debugSymbolLevel = "none"
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.room.compiler)
    add("kspAndroid", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    debugImplementation(compose.uiTooling)
}

