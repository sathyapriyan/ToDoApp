package com.example.todoapp.presentation.screen.user

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UserViewodell @Inject constructor(
    @Named("ioDispatcher")
    private val ioDispatcher: CoroutineDispatcher,
    private val dataRepository: DataRepository
): ViewModel()  {

}