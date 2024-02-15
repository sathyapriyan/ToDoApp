package com.example.todoapp.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.todoapp.data.model.ToDos
import com.example.todoapp.data.remote.ApiDataSource

class RemotePagingSource(
    private val apiService: ApiDataSource,
): PagingSource<Int, ToDos>(){

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ToDos> {
        val position = params.key ?: STARTING_PAGE_INDEX

        //Retrofit will automatically make suspend functions main-safe so you can call them directly from Dispatchers.Main
        return try {
         //   val result = apiService.limitAndSkipToDos(position, params.loadSize)
            val result = apiService.limitAndSkipToDos(params.loadSize, position)
            println("test 15 --->limit  ${params.loadSize}")
            println("test 15 --->skip  $position")
            println("test 15 --->result limit  ${result.body()?.limit}")
            println("test 15 --->result skip  ${result.body()?.skip}")
            LoadResult.Page(
                data = result.body()!!.todos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (result.body()!!.todos.isEmpty()) null else position + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }
    override fun getRefreshKey(state: PagingState<Int, ToDos>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}