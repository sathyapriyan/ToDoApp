package com.example.todoapp.data.room.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.todoapp.data.room.db.DatabaseToDo
import com.example.todoapp.data.room.entity.ToDoData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
object ToDoDataDaoTest {

    private lateinit var databaseToDo: DatabaseToDo
    private lateinit var toDoDataDao: ToDoDataDao
    @Before
    fun setUp(){
        databaseToDo= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseToDo::class.java).allowMainThreadQueries().build()
        toDoDataDao= databaseToDo.toDoDataDao()

    }

    @After
    fun tearDown(){
        databaseToDo.close()
    }

    @Test
    fun saveData()= runBlocking {
        val toDoData=ToDoData(
            serialNumber = 0,
            id = 0,
            todo = "",
            completed = false,
            userId = 0,
            isDeleted = false,
            deletedOn = ""
        )
        toDoDataDao.saveData(toDoData)

        val getAllToDoList= toDoDataDao.getAllToDoList().getOrElse(index = 0, defaultValue = { toDoData })

       assertThat(getAllToDoList)
    }

}


