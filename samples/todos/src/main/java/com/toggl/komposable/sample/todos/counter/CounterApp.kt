package com.toggl.komposable.sample.todos.counter

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import com.toggl.komposable.architecture.ReduceResult
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.createStore
import com.toggl.komposable.extensions.withoutEffect
import com.toggl.komposable.sample.todos.dispatcherProvider
import com.toggl.komposable.sample.todos.storeScopeProvider

/**
 * We are building an Counter app using Jetpack Compose which will have two buttons
 * to increment and decrement the counter value.
 * It is going to have one text view to display the current counter value.
 */

sealed class CounterAction {
    data object Increment : CounterAction()
    data object Decrement : CounterAction()
}

data class CounterState(val value: Int = 0)

class CounterReducer : Reducer<CounterState, CounterAction> {
    override fun reduce(
        state: CounterState,
        action: CounterAction
    ): ReduceResult<CounterState, CounterAction> {
        return when (action) {
            CounterAction.Increment -> state.copy(value = state.value + 1).withoutEffect()
            CounterAction.Decrement -> state.copy(value = state.value - 1).withoutEffect()
        }
    }
}

val counterAppStore = createStore(
    initialState = CounterState(),
    reducer = CounterReducer(),
    storeScopeProvider = storeScopeProvider,
    dispatcherProvider = dispatcherProvider
)

@Composable
fun CounterApp() {
    val counterState by counterAppStore.state.collectAsState(initial = CounterState())
    Column(
        // Center the content
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        TextCounter(
            state = counterState,
            onIncrement = {
                counterAppStore.send(CounterAction.Increment)
            },
            onDecrement = {
                counterAppStore.send(CounterAction.Decrement)
            }
        )
    }
}

@Composable
fun TextCounter(state: CounterState, onIncrement: () -> Unit, onDecrement: () -> Unit) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = state.value.toString(), style = MaterialTheme.typography.displayLarge)
        Button(onClick = onIncrement) {
            Text(text = "Increment")
        }
        Button(onClick = onDecrement) {
            Text(text = "Decrement")
        }
    }
}