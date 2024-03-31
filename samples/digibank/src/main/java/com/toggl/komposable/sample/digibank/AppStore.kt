package com.toggl.komposable.sample.digibank

import com.toggl.komposable.extensions.createStore
import com.toggl.komposable.sample.digibank.portfolio.PortfolioReducer
import com.toggl.komposable.sample.digibank.portfolio.PortfolioState
import com.toggl.komposable.scope.DispatcherProvider
import com.toggl.komposable.scope.StoreScopeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

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
    initialState = PortfolioState(),
    reducer = PortfolioReducer(),
    storeScopeProvider = storeScopeProvider,
    dispatcherProvider = dispatcherProvider,
)