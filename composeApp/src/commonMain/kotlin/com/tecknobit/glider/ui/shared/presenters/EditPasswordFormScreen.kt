@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeApi::class)

package com.tecknobit.glider.ui.shared.presenters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.components.RetryButton
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.equinoxcompose.session.screens.EquinoxScreen
import com.tecknobit.equinoxcompose.session.sessionflow.SessionFlowContainer
import com.tecknobit.equinoxcompose.session.sessionflow.rememberSessionFlowState
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.equinoxcore.annotations.Wrapper
import com.tecknobit.glider.navigator
import com.tecknobit.glider.ui.shared.data.PasswordDetails
import com.tecknobit.glider.ui.shared.presentations.EditPasswordFormViewModel
import com.tecknobit.glider.ui.theme.GliderTheme
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.edit
import glider.composeapp.generated.resources.edit_password
import glider.composeapp.generated.resources.editing
import org.jetbrains.compose.resources.stringResource

/**
 * The `EditPasswordFormScreen` class is useful to display the forms allowing the user to insert the
 * new password data to edit a password
 *
 * @property viewModel The support viewmodel of the screen
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @param V generic type used to allow the use of own viewmodel in custom screens
 *
 * @see EquinoxNoModelScreen
 * @see EquinoxScreen
 * @see GliderScreenTab
 * @see PasswordFormScreen
 */
@Structure
abstract class EditPasswordFormScreen<V : EditPasswordFormViewModel>(
    viewModel: V,
) : PasswordFormScreen<V>(
    viewModel = viewModel,
    title = Res.string.edit_password
) {

    /**
     * `password` the password to edit
     */
    protected lateinit var password: State<PasswordDetails?>

    /**
     * Method to arrange the content of the screen to display
     */
    @Composable
    override fun ArrangeScreenContent() {
        GliderTheme {
            SessionFlowContainer(
                modifier = Modifier
                    .fillMaxSize(),
                state = viewModel.sessionFlowState,
                viewModel = viewModel,
                initialLoadingRoutineDelay = 500L,
                loadingRoutine = { password.value != null },
                content = {
                    CollectStatesAfterLoading()
                    Scaffold(
                        topBar = {
                            MediumTopAppBar(
                                colors = TopAppBarDefaults.mediumTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                navigationIcon = {
                                    IconButton(
                                        onClick = { navigator.goBack() }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBackIosNew,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                },
                                title = {
                                    Text(
                                        text = stringResource(title),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            )
                        },
                        snackbarHost = {
                            SnackbarHost(
                                hostState = viewModel.snackbarHostState!!
                            )
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(
                                    top = it.calculateTopPadding(),
                                    bottom = 16.dp
                                )
                                .padding(
                                    horizontal = 16.dp
                                )
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .navigationBarsPadding()
                                .imePadding(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            ScreenContent()
                        }
                    }
                },
                retryFailedFlowContent = {
                    RetryButton(
                        onRetry = {
                            viewModel.sessionFlowState.reload(
                                onReload = {
                                    viewModel.retrievePassword()
                                }
                            )
                        }
                    )
                }
            )
        }
    }

    /**
     * The custom content displayed in the tab
     */
    @Composable
    override fun ColumnScope.ScreenContent() {
        Form()
    }

    /**
     * Custom [PerformFormActionButton] used to edit the [password]
     */
    @Wrapper
    @Composable
    @NonRestartableComposable
    protected fun EditPasswordButton() {
        PerformFormActionButton(
            performActionText = Res.string.edit,
            performingActionText = Res.string.editing
        )
    }

    /**
     * Method invoked when the [ShowContent] composable has been started
     */
    override fun onStart() {
        super.onStart()
        viewModel.retrievePassword()
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    override fun CollectStates() {
        viewModel.tailError = remember { mutableStateOf(false) }
        viewModel.scopesError = remember { mutableStateOf(false) }
        performingPasswordOperation = viewModel.performingPasswordOperation.collectAsState(
            initial = false
        )
        password = viewModel.password.collectAsState()
        viewModel.sessionFlowState = rememberSessionFlowState()
    }

    /**
     * Method to collect or instantiate the states of the screen after a loading required to correctly
     * assign an initial value to the states
     */
    @Composable
    @RequiresSuperCall
    override fun CollectStatesAfterLoading() {
        viewModel.tail = remember { mutableStateOf(viewModel.password.value!!.tail) }
        viewModel.scopes = remember {
            mutableStateOf(viewModel.password.value!!.scopes.joinToString(","))
        }
    }

}