package com.example.todoapp.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.todoapp.data.room.dao.ToDoDataDao
import com.example.todoapp.data.room.entity.ToDoData

class LocalPagingSource(
    private val dao: ToDoDataDao
) : PagingSource<Int, ToDoData>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ToDoData> {
        val position = params.key ?: STARTING_PAGE_INDEX

        //Retrofit will automatically make suspend functions main-safe so you can call them directly from Dispatchers.Main
        return try {

           // val result = dao.getAllToDoList(params.loadSize, position)

            println("test 15 db--->limit  ${params.loadSize}")
            println("test 15 db--->skip  $position")
            val result = dao.getPagedList(params.loadSize, position)
            println("test 15 db--->result  $result")


            LoadResult.Page(
                data = result,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (result.isEmpty()) null else position + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }
    override fun getRefreshKey(state: PagingState<Int, ToDoData>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}