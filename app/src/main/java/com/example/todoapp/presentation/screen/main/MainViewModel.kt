package com.example.todoapp.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.core.util.StateHandler
import com.example.todoapp.data.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    @Named("ioDispatcher")
    private val ioDispatcher: CoroutineDispatcher,
    private val dataRepository: DataRepository,
): ViewModel() {

    private val _responseStatus = MutableStateFlow<StateHandler<String>>(
        StateHandler.Loading()
    )
    val responseStatus = _responseStatus.asStateFlow()

    init {
        getAllToDos()
    }

    private fun getAllToDos() {
        viewModelScope.launch(ioDispatcher) {
            _responseStatus.emit(StateHandler.Loading())
            delay(200L)
            val responseState = dataRepository.getAllToDos()
            when (responseState) {
                is StateHandler.Success -> {
                    _responseStatus.emit(StateHandler.Success(data = "Master data downloaded"))
                }
                is StateHandler.Error -> {
                    _responseStatus.emit(StateHandler.Error(message = "Master data download failed. Try again."))
                }
                else -> {}
            }

        }
    }
    private fun getSingleToDo() {
        viewModelScope.launch(ioDispatcher) {
            _responseStatus.emit(StateHandler.Loading())
            delay(200L)
            val responseState = dataRepository.getSingleToDo(id= 1)
            when (responseState) {
                is StateHandler.Success -> {
                    _responseStatus.emit(StateHandler.Success(data = "Master data downloaded"))
                }
                is StateHandler.Error -> {
                    _responseStatus.emit(StateHandler.Error(message = "Master data download failed. Try again."))
                }
                else -> {}
            }

        }
    }

}