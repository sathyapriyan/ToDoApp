package com.example.todoapp.presentation.screen.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.core.util.StateHandler
import com.example.todoapp.data.repository.DataRepository
import com.example.todoapp.data.room.entity.ToDoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UserViewModel @Inject constructor(
    @Named("ioDispatcher")
    private val ioDispatcher: CoroutineDispatcher,
    private val dataRepository: DataRepository
): ViewModel() {

    private val _loadToDosByUserId: MutableStateFlow<StateHandler<List<ToDoData>>> =
        MutableStateFlow(StateHandler.Loading())
    val loadToDosByUserId: StateFlow<StateHandler<List<ToDoData>>> =
        _loadToDosByUserId
    val totalListSize : StateFlow<Int> = dataRepository
        .observeCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(),5000)

    fun getToDoListByUserId(userId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            val response = dataRepository.getToDosByUserId(userId = userId) ?: emptyList()

            _loadToDosByUserId.emit(StateHandler.Success(response))

        }
    }
    fun saveToDo(inNetwork:Boolean,todo:ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.saveToDo(
                inNetwork =inNetwork,
                todo = todo
            )
        }
    }
    fun updateToDo(inNetwork:Boolean,todo:ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.updateToDo(
                inNetwork =inNetwork,
                todo = todo
            )
        }
    }
    fun deleteToDo(inNetwork:Boolean,todo:ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.deleteToDo(
                inNetwork =inNetwork,
                todo = todo
            )
        }
    }

}