package com.example.todoapp.presentation.screen.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.core.util.CommonUtil
import com.example.todoapp.core.util.StateHandler
import com.example.todoapp.data.model.AllToDosResponse
import com.example.todoapp.data.model.ToDos
import com.example.todoapp.data.repository.DataRepository
import com.example.todoapp.data.room.entity.ToDoData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _responseStatus = MutableStateFlow<StateHandler<MutableList<ToDos>>>(
        StateHandler.Loading()
    )
    val responseStatus = _responseStatus.asStateFlow()

    private val _loadNewStoriesResponse: MutableLiveData<StateHandler<MutableList<ToDoData>>> =
        MutableLiveData(StateHandler.Loading())
    val loadNewStoriesResponse: LiveData<StateHandler<MutableList<ToDoData>>> =
        _loadNewStoriesResponse

    init {
        getAllToDos()
    }
    fun getAllToDos(){


        viewModelScope.launch(ioDispatcher) {

            val response =  dataRepository.getNewStories()

            response.collect {

                it.onSuccess { allStoriesEntityList ->

                    println(" Stories from DB --> $allStoriesEntityList")

                    if (allStoriesEntityList.isNotEmpty()) {

                        _loadNewStoriesResponse.postValue(StateHandler.Success(allStoriesEntityList.toMutableList()))

                    } else {

                        dataRepository.getNewStories()

                    }

                }

                it.onFailure { throwable ->

                    _loadNewStoriesResponse.postValue(StateHandler.Error(message = throwable.localizedMessage))

                }

            }

        }

    }
}