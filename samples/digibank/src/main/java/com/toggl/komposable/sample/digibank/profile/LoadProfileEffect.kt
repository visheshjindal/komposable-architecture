package com.toggl.komposable.sample.digibank.profile

import com.toggl.komposable.sample.digibank.data.datasource.Repository
import com.toggl.komposable.sample.digibank.data.datasource.RepositoryImpl
import com.toggl.komposable.utils.SuspendEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadProfileEffect(private val repository: Repository = RepositoryImpl) :
    SuspendEffect<ProfileAction>() {
    override suspend fun execute(): ProfileAction? = withContext(Dispatchers.IO) {
        val result = repository.fetchUserDetails()
        result.fold(
            onSuccess = { ProfileAction.OnProfileLoadedSuccessful(it) },
            onFailure = { ProfileAction.OnProfileLoadedFailed(it) }
        )
    }

}