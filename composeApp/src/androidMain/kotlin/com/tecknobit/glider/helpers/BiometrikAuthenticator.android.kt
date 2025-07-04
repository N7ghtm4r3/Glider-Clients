package com.tecknobit.glider.helpers

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE
import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
import androidx.biometric.BiometricManager.from
import androidx.biometric.BiometricPrompt
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tecknobit.equinoxcompose.components.ErrorUI
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi
import com.tecknobit.equinoxcore.utilities.ContextActivityProvider
import com.tecknobit.glider.helpers.BiometricPromptManager.BiometricResult.AuthenticationError
import com.tecknobit.glider.helpers.BiometricPromptManager.BiometricResult.AuthenticationFailed
import com.tecknobit.glider.helpers.BiometricPromptManager.BiometricResult.AuthenticationNotSet
import com.tecknobit.glider.helpers.BiometricPromptManager.BiometricResult.AuthenticationSuccess
import com.tecknobit.glider.helpers.BiometricPromptManager.BiometricResult.FeatureUnavailable
import com.tecknobit.glider.helpers.BiometricPromptManager.BiometricResult.HardwareUnavailable
import com.tecknobit.glider.ui.components.RetryButton
import com.tecknobit.glider.ui.theme.GliderTheme
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.enter_your_credentials_to_continue
import glider.composeapp.generated.resources.login_required
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.compose.resources.stringResource

@Composable
@FutureEquinoxApi
@Deprecated(
    message = "Will be created a standalone library which contains this api, will be redesigned to be general purpose" +
            "or will be integrated as component in Equinox"
)
actual fun BiometrikAuthenticator(
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
) {
    var retry by remember { mutableStateOf(false) }
    val biometricPromptManager = remember {
        BiometricPromptManager(ContextActivityProvider.getCurrentActivity() as AppCompatActivity)
    }
    val biometricResult by biometricPromptManager.promptResults.collectAsState(
        initial = null
    )
    LaunchedEffect(biometricResult) {
        if (biometricResult is AuthenticationNotSet)
            onSuccess()
    }
    if (biometricResult == null || retry) {
        biometricPromptManager.showBiometricPrompt(
            title = stringResource(Res.string.login_required),
            description = stringResource(Res.string.enter_your_credentials_to_continue)
        )
    }
    biometricResult?.let { result ->
        when (result) {
            AuthenticationSuccess, AuthenticationNotSet, HardwareUnavailable,
            FeatureUnavailable,
                -> onSuccess()
            else -> {
                AnimatedVisibility(
                    visible = !retry
                ) {
                    retry = false
                    GliderTheme {
                        ErrorUI(
                            containerModifier = Modifier
                                .fillMaxSize(),
                            retryContent = {
                                RetryButton(
                                    onRetry = { retry = !retry }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * The `BiometricPromptManager` class is useful to manage the biometric authentication
 *
 * @param activity: the activity from launch the biometric authentication
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@FutureEquinoxApi
// TODO: MOVE AND REDESIGN THIS API TO ACCEPTS OTHER authenticators AND TO BE MORE GENERAL PURPOSE
class BiometricPromptManager(
    private val activity: AppCompatActivity,
) {

    /**
     * `resultChannel` -> the channel where the result applied
     */
    private val resultChannel = Channel<BiometricResult>(
        capacity = UNLIMITED
    )

    /**
     * `promptResults` -> the prompts used to apply the result
     */
    val promptResults = resultChannel.receiveAsFlow()

    /**
     * Function to show the biometric authentication prompt
     *
     * @param title: the title of the prompt
     * @param description: the description of the prompt
     */
    fun showBiometricPrompt(
        title: String,
        description: String,
    ) {
        val manager = from(activity)
        val authenticators = BIOMETRIC_STRONG or DEVICE_CREDENTIAL
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setAllowedAuthenticators(authenticators)
        when (manager.canAuthenticate(authenticators)) {
            BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                resultChannel.trySend(HardwareUnavailable)
                return
            }

            BIOMETRIC_ERROR_NO_HARDWARE -> {
                resultChannel.trySend(FeatureUnavailable)
                return
            }

            BIOMETRIC_ERROR_NONE_ENROLLED -> {
                resultChannel.trySend(AuthenticationNotSet)
                return
            }

            else -> Unit
        }
        val prompt = BiometricPrompt(
            activity,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    resultChannel.trySend(AuthenticationError(errString.toString()))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    resultChannel.trySend(AuthenticationSuccess)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    resultChannel.trySend(AuthenticationFailed)
                }
            }
        )
        prompt.authenticate(promptInfo.build())
    }

    /**
     * The [BiometricResult] interface is used to manage the result of the biometric authentication
     */
    sealed interface BiometricResult {

        /**
         * The [HardwareUnavailable] result occurred when the hardware is not available
         */
        data object HardwareUnavailable : BiometricResult

        /**
         * The [FeatureUnavailable] result occurred when the feature is not available
         */
        data object FeatureUnavailable : BiometricResult

        /**
         * The [AuthenticationError] result occurred when the authentication failed
         *
         * @param error: the error occurred
         */
        data class AuthenticationError(val error: String) : BiometricResult

        /**
         * The [AuthenticationFailed] result occurred when the authentication failed
         */
        data object AuthenticationFailed : BiometricResult

        /**
         * The [AuthenticationSuccess] result occurred when the authentication has been successful
         */
        data object AuthenticationSuccess : BiometricResult

        /**
         * The [AuthenticationNotSet] result occurred when the authentication is not set
         */
        data object AuthenticationNotSet : BiometricResult

    }

}