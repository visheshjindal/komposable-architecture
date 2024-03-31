package com.toggl.komposable.sample.digibank

import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.architecture.WrapperAction
import com.toggl.komposable.extensions.combine
import com.toggl.komposable.extensions.createStore
import com.toggl.komposable.extensions.pullback
import com.toggl.komposable.sample.digibank.accounts.AccountDetailsAction
import com.toggl.komposable.sample.digibank.accounts.AccountDetailsReducer
import com.toggl.komposable.sample.digibank.accounts.AccountDetailsUIState
import com.toggl.komposable.sample.digibank.navigation.ScreenRoutes
import com.toggl.komposable.sample.digibank.portfolio.LoadPortfolioEffect
import com.toggl.komposable.sample.digibank.portfolio.PortfolioAction
import com.toggl.komposable.sample.digibank.portfolio.PortfolioReducer
import com.toggl.komposable.sample.digibank.portfolio.PortfolioUIState
import com.toggl.komposable.sample.digibank.profile.LoadProfileEffect
import com.toggl.komposable.sample.digibank.profile.ProfileAction
import com.toggl.komposable.sample.digibank.profile.ProfileReducer
import com.toggl.komposable.sample.digibank.profile.ProfileUIState
import com.toggl.komposable.sample.digibank.settings.SettingAction
import com.toggl.komposable.sample.digibank.transactions.LoadTransactionsEffect
import com.toggl.komposable.sample.digibank.transactions.TransactionsAction
import com.toggl.komposable.sample.digibank.transactions.TransactionsReducer
import com.toggl.komposable.sample.digibank.transactions.TransactionsUIState
import com.toggl.komposable.scope.DispatcherProvider
import com.toggl.komposable.scope.StoreScopeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

data class AppState(
    val currentRoutes: ScreenRoutes = ScreenRoutes.BottomBar,
    val showCurrency: Boolean = true,
    val portfolioUIState: PortfolioUIState = PortfolioUIState(),
    val accountDetailsUIState: AccountDetailsUIState = AccountDetailsUIState(),
    val transactionsUIState: TransactionsUIState = TransactionsUIState(),
    val profileUIState: ProfileUIState = ProfileUIState(),
)

sealed class GlobalAction {
    data object OnTapNavigationToProfile: GlobalAction()
    @WrapperAction
    data class PortfolioActions(val action: PortfolioAction) : GlobalAction()
    @WrapperAction
    data class AccountDetailsActions(val action: AccountDetailsAction) : GlobalAction()
    @WrapperAction
    data class TransactionsActions(val action: TransactionsAction) : GlobalAction()
    @WrapperAction
    data class SettingActions(val action: SettingAction) : GlobalAction()
    @WrapperAction
    data class ProfileActions(val action: ProfileAction) : GlobalAction()
}

val transactionsReducer = TransactionsReducer(LoadTransactionsEffect())
val accountDetailsReducer = AccountDetailsReducer()
val portfolioReducer = PortfolioReducer(LoadPortfolioEffect())
val profileReducer = ProfileReducer(LoadProfileEffect())
val globalReducerInstance = GlobalReducer()

val globalReducer: Reducer<AppState, GlobalAction> = combine(
    transactionsReducer.pullback(
        mapToLocalState = { it.transactionsUIState },
        mapToLocalAction = { (it as? GlobalAction.TransactionsActions)?.action },
        mapToGlobalState = { globalState, transactionsState -> globalState.copy(transactionsUIState = transactionsState) },
        mapToGlobalAction = { GlobalAction.TransactionsActions(it) }
    ),
    accountDetailsReducer.pullback(
        mapToLocalState = { it.accountDetailsUIState },
        mapToLocalAction = { (it as? GlobalAction.AccountDetailsActions)?.action },
        mapToGlobalState = { globalState, accountDetailsState -> globalState.copy(accountDetailsUIState = accountDetailsState) },
        mapToGlobalAction = { GlobalAction.AccountDetailsActions(it) }
    ),
    portfolioReducer.pullback(
        mapToLocalState = { it.portfolioUIState },
        mapToLocalAction = { (it as? GlobalAction.PortfolioActions)?.action },
        mapToGlobalState = { globalState, portfolioState -> globalState.copy(portfolioUIState = portfolioState) },
        mapToGlobalAction = { GlobalAction.PortfolioActions(it) }
    ),
    profileReducer.pullback(
        mapToLocalState = { it.profileUIState },
        mapToLocalAction = { (it as? GlobalAction.ProfileActions)?.action },
        mapToGlobalState = { globalState, profileState -> globalState.copy(profileUIState = profileState) },
        mapToGlobalAction = { GlobalAction.ProfileActions(it) }
    ),
    globalReducerInstance.pullback(
        mapToLocalState = { it },
        mapToLocalAction = { it },
        mapToGlobalState = { _, state -> state },
        mapToGlobalAction = { it })
)

/**
 * The dispatcher provider used by the app.
 * This is used to provide the correct dispatcher for the store.
 */
val dispatcherProvider = DispatcherProvider(
    io = Dispatchers.IO,
    computation = Dispatchers.Default,
    main = Dispatchers.Main,
)

/**
 * The coroutine scope used by the app.
 * This is used to provide the correct coroutine scope for the store.
 */
val coroutineScope = object : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = dispatcherProvider.main
}

val storeScopeProvider = StoreScopeProvider { coroutineScope }

/**
 * The store used by the app.
 * This is used to manage the state of the app.
 */
val appStore = createStore(
    initialState = AppState(),
    reducer = globalReducer,
    storeScopeProvider = storeScopeProvider,
    dispatcherProvider = dispatcherProvider,
)