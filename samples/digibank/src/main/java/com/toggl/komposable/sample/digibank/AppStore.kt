package com.toggl.komposable.sample.digibank

import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.combine
import com.toggl.komposable.extensions.createStore
import com.toggl.komposable.extensions.pullback
import com.toggl.komposable.sample.digibank.accounts.AccountDetailsAction
import com.toggl.komposable.sample.digibank.accounts.AccountDetailsReducer
import com.toggl.komposable.sample.digibank.accounts.AccountDetailsUIState
import com.toggl.komposable.sample.digibank.portfolio.LoadPortfolioEffect
import com.toggl.komposable.sample.digibank.portfolio.PortfolioAction
import com.toggl.komposable.sample.digibank.portfolio.PortfolioReducer
import com.toggl.komposable.sample.digibank.portfolio.PortfolioUIState
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
    val isLongPressBalanceEnabled: Boolean = false,
    val isLongPressPortfolioEnabled: Boolean = false,
    val showCurrency: Boolean = true,
    val portfolioUIState: PortfolioUIState,
    val accountDetailsUIState: AccountDetailsUIState,
    val transactionsUIState: TransactionsUIState
)

sealed class GlobalAction {
    data class PortfolioActions(val action: PortfolioAction) : GlobalAction()
    data class AccountDetailsActions(val action: AccountDetailsAction) : GlobalAction()
    data class TransactionsActions(val action: TransactionsAction) : GlobalAction()
}

val transactionsReducer = TransactionsReducer(LoadTransactionsEffect())
val accountDetailsReducer = AccountDetailsReducer()
val portfolioReducer = PortfolioReducer(LoadPortfolioEffect())

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
    )
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
    initialState = AppState(
        portfolioUIState = PortfolioUIState(),
        accountDetailsUIState = AccountDetailsUIState(),
        transactionsUIState = TransactionsUIState()
    ),
    reducer = globalReducer,
    storeScopeProvider = storeScopeProvider,
    dispatcherProvider = dispatcherProvider,
)