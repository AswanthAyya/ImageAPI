package com.example.imageapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "welcome"
            ) {

                composable("welcome") {
                    WelcomeScreen {
                        navController.navigate("movies")
                    }
                }

                composable("movies") {
                    MovieListScreen(navController)
                }

                composable(
                    route = "details/{title}/{overview}/{poster}",
                    arguments = listOf(
                        navArgument("title") { type = NavType.StringType },
                        navArgument("overview") { type = NavType.StringType },
                        navArgument("poster") { type = NavType.StringType }
                    )
                ) { backStackEntry ->

                    val title =
                        backStackEntry.arguments?.getString("title") ?: ""

                    val overview =
                        backStackEntry.arguments?.getString("overview") ?: ""

                    val poster =
                        backStackEntry.arguments?.getString("poster") ?: ""

                    DetailScreen(title, overview, poster)
                }
            }
        }
    }
}