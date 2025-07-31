package com.example.universityapp.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.universityapp.data.api.RetrofitInstance
import com.example.universityapp.data.repository.UniversityRepository
import com.example.universityapp.ui.screens.HomeScreen
import com.example.universityapp.ui.screens.TimerScreen
import com.example.universityapp.ui.screens.UniversityListScreen
import com.example.universityapp.ui.screens.WebViewScreen
import com.example.universityapp.ui.universities.viewmodel.UniversityViewModel
import com.example.universityapp.ui.universities.viewmodel.ViewModelFactory
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val universityRepository = UniversityRepository(RetrofitInstance.api)
    val universityViewModelFactory = ViewModelFactory(universityRepository)

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("timer") {
            TimerScreen()
        }

        composable("university_list") {
            val universityViewModel: UniversityViewModel =
                viewModel(factory = universityViewModelFactory)
            UniversityListScreen(
                viewModel = universityViewModel,
                onUniversityClick = { url ->
                    val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                    navController.navigate("webview/$encodedUrl")
                }
            )
        }

        composable(
            route = "webview/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            WebViewScreen(url = url)
        }
    }
}