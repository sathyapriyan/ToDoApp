package com.example.todoapp.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.todoapp.core.util.StateHandler
import com.example.todoapp.data.model.ToDos
import com.example.todoapp.data.repository.DataRepository
import com.example.todoapp.data.room.entity.ToDoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    @Named("ioDispatcher")
    private val ioDispatcher: CoroutineDispatcher,
    private val dataRepository: DataRepository
): ViewModel() {

    private val _loadCompliedToDoResponse: MutableStateFlow<StateHandler<List<ToDoData>>> =
        MutableStateFlow(StateHandler.Loading())

    val loadCompliedToDoResponse: StateFlow<StateHandler<List<ToDoData>>> =
        _loadCompliedToDoResponse

    private val _loadInCompliedToDoResponse: MutableStateFlow<StateHandler<List<ToDoData>>> =
        MutableStateFlow(StateHandler.Loading())

    val loadInCompliedToDoResponse: StateFlow<StateHandler<List<ToDoData>>> =
        _loadInCompliedToDoResponse

    val totalListSize : StateFlow<Int> = dataRepository
        .observeCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(),5000)

    private val _loadToDosByUserId: MutableStateFlow<Map<Int,List<ToDoData>>> = MutableStateFlow(emptyMap())
    val loadToDosByUserId: StateFlow<Map<Int,List<ToDoData>>> =
        _loadToDosByUserId




    fun getCompletedToDos(inNetwork:Boolean){


        viewModelScope.launch(ioDispatcher) {

            val response =  dataRepository.getCompliedToDo(inNetwork = inNetwork)

            response.collect {

                it.onSuccess { allStoriesEntityList ->


                    if (allStoriesEntityList.isNotEmpty()) {

                        _loadCompliedToDoResponse.emit(StateHandler.Success(allStoriesEntityList))
                        getInCompletedToDos()
                        getUserIdList()
                    } else {

                        dataRepository.getInCompliedToDo()

                    }

                }

                it.onFailure { throwable ->

                    _loadCompliedToDoResponse.emit(StateHandler.Error(message = throwable.localizedMessage))

                }

            }
        }

    }
    private fun getInCompletedToDos(){


        viewModelScope.launch(ioDispatcher) {

            val response =  dataRepository.getInCompliedToDo()

            response.collect {

                it.onSuccess { allStoriesEntityList ->

                    println(" Stories from DB --> $allStoriesEntityList")

                    if (allStoriesEntityList.isNotEmpty()) {

                        _loadInCompliedToDoResponse.emit(StateHandler.Success(allStoriesEntityList))

                    } else {

                        dataRepository.getInCompliedToDo()

                    }

                }

                it.onFailure { throwable ->

                    _loadInCompliedToDoResponse.emit(StateHandler.Error(message = throwable.localizedMessage))

                }

            }

        }

    }
    private fun getUserIdList(){
        viewModelScope.launch(Dispatchers.IO) {

            val response = dataRepository.getUserIdList()
            println("response  ---3> $response")

            val userToDos = mutableMapOf<Int, List<ToDoData>>()

            response.forEach {
                userToDos[it] = dataRepository.getToDosByUserId(userId = it) ?: emptyList()
            }
            _loadToDosByUserId.emit(userToDos)

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
    val getAllToDoRemote : Flow<PagingData<ToDos>> =  dataRepository.getAllToDoData().cachedIn(viewModelScope)
    val getAllToDoLocal : Flow<PagingData<ToDoData>> = dataRepository.getAllToDoDataLocal().cachedIn(viewModelScope)

}