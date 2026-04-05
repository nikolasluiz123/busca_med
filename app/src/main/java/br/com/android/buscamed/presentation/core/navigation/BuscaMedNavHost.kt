package br.com.android.buscamed.presentation.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import br.com.android.buscamed.presentation.screen.capture.barcodeCaptureScreen
import br.com.android.buscamed.presentation.screen.capture.navigateToBarcodeCaptureScreen
import br.com.android.buscamed.presentation.screen.capture.navigateToPillPackCaptureScreen
import br.com.android.buscamed.presentation.screen.capture.navigateToPrescriptionCaptureScreen
import br.com.android.buscamed.presentation.screen.capture.pillPackCaptureScreen
import br.com.android.buscamed.presentation.screen.capture.prescriptionCaptureScreen
import br.com.android.buscamed.presentation.screen.home.homeScreen
import br.com.android.buscamed.presentation.screen.home.homeScreenRoute
import br.com.android.buscamed.presentation.screen.home.navigateToHomeScreen
import br.com.android.buscamed.presentation.screen.login.loginScreen
import br.com.android.buscamed.presentation.screen.login.loginScreenRoute
import br.com.android.buscamed.presentation.screen.pillpack.PillPackResultScreenArgs
import br.com.android.buscamed.presentation.screen.pillpack.navigateToPillPackResult
import br.com.android.buscamed.presentation.screen.pillpack.pillPackResultScreen
import br.com.android.buscamed.presentation.screen.prescription.PrescriptionResultScreenArgs
import br.com.android.buscamed.presentation.screen.prescription.navigateToPrescriptionResult
import br.com.android.buscamed.presentation.screen.prescription.prescriptionResultScreen
import br.com.android.buscamed.presentation.screen.registeruser.RegisterUserScreenArgs
import br.com.android.buscamed.presentation.screen.registeruser.navigateToRegisterUserScreen
import br.com.android.buscamed.presentation.screen.registeruser.registerUserScreen
import br.com.android.buscamed.presentation.screen.result.medicationDetailsScreen
import br.com.android.buscamed.presentation.screen.result.medicationListScreen
import br.com.android.buscamed.presentation.screen.result.navigateToMedicationDetails
import br.com.android.buscamed.presentation.screen.result.navigateToMedicationList
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

/**
 * Host de navegação principal do aplicativo BuscaMed.
 *
 * Esta função configura o gráfico de navegação do Compose, definindo as rotas,
 * as telas correspondentes e as transições entre elas.
 *
 * @param modifier Modificador de layout para o contêiner de navegação.
 * @param navController Controlador responsável por gerenciar a pilha de telas.
 */
@Composable
fun BuscaMedNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = getStartDestination(),
        modifier = modifier
    ) {
        loginScreen(
            onNavigateToHome = {
                navController.navigateToHomeScreen(
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(loginScreenRoute, inclusive = true)
                        .build()
                )
            },
            onNavigateToRegister = {
                navController.navigateToRegisterUserScreen(args = RegisterUserScreenArgs())
            }
        )

        registerUserScreen(
            onBackClick = navController::popBackStack,
            onSaveSuccess = {

            }
        )

        homeScreen(
            onNavigateToPrescriptionCapture = navController::navigateToPrescriptionCaptureScreen,
            onNavigateToPillPackCapture = navController::navigateToPillPackCaptureScreen,
            onNavigateToBarcodeCapture = navController::navigateToBarcodeCaptureScreen
        )

        pillPackCaptureScreen(
            onPillPackProcessed = { pillPack ->
                navController.navigateToPillPackResult(
                    args = PillPackResultScreenArgs(pillPack)
                )
            }
        )

        pillPackResultScreen(
            onBackClick = navController::popBackStack
        )

        prescriptionCaptureScreen(
            onPrescriptionProcessed = { prescription ->
                navController.navigateToPrescriptionResult(
                    args = PrescriptionResultScreenArgs(prescription)
                )
            }
        )

        prescriptionResultScreen(
            onBackClick = navController::popBackStack
        )

        medicationListScreen(
            onBackClick = navController::popBackStack,
            onMedicationClick = navController::navigateToMedicationDetails
        )

        medicationDetailsScreen(
            onBackClick = navController::popBackStack,
        )

        barcodeCaptureScreen(
            onNavigateToMedicationDetails = navController::navigateToMedicationDetails,
            onNavigateToMedicationList = navController::navigateToMedicationList
        )
    }
}

@Composable
private fun getStartDestination(): String {
    return if (Firebase.auth.currentUser != null) homeScreenRoute else loginScreenRoute
}
