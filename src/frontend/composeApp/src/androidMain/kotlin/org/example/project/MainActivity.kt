package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.retainedComponent
import org.example.project.ui.navigation.RootComponent
import org.example.project.ui.screens.signup.SuccessDialog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = retainedComponent {
            RootComponent(it)
        }
        setContent {
            App(root)
        }
    }
}