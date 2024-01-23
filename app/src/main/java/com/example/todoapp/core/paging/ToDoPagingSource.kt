package com.example.todoapp.core.paging

import android.net.http.HttpException
import android.net.http.NetworkException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.todoapp.data.model.ToDos
import com.example.todoapp.data.remote.ApiDataSource
import java.io.IOException

class ToDoPagingSource(
    private val apiService: ApiDataSource,
): PagingSource<Int, ToDos>(){
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ToDos> {
        return try {
            val currentPage = params.key ?: 1
            val limitPage = params.loadSize
            val data = apiService.limitAndSkipToDos(
                skip = currentPage,
                limit = limitPage
            )
            LoadResult.Page(
                data = data.body()!!.todos,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (data.body()!!.todos.isEmpty()) null else data.body()!!.todos.size + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: NetworkException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ToDos>): Int? {
        return state.anchorPosition
    }
}