package com.dipumba.ytsocialapp.android.common.util

import com.dipumba.ytsocialapp.common.util.Result
import kotlinx.coroutines.delay

interface PagingManager<Model>{
    suspend fun loadItems()
    fun reset()
}

class DefaultPagingManager<Model>(
    private inline val onRequest: suspend (page: Int) -> Result<List<Model>>,
    private inline val onSuccess: (items: List<Model>, page: Int) -> Unit,
    private inline val onError: (cause: String, page: Int) -> Unit,
    private inline val onLoadStateChange: (isLoading: Boolean) -> Unit
): PagingManager<Model>{

    private var currentPageNumber = Constants.INITIAL_PAGE_NUMBER
    private var isLoading = false

    override suspend fun loadItems() {
        if (isLoading) return
        isLoading = true
        onLoadStateChange(true)
        delay(3_000)

        val result = onRequest(currentPageNumber)
        isLoading = false
        onLoadStateChange(false)

        when(result){
            is Result.Error -> {
                onError(result.message ?: Constants.UNEXPECTED_ERROR_MESSAGE, currentPageNumber)
            }
            is Result.Success -> {
                onSuccess(result.data!!, currentPageNumber)
                currentPageNumber += 1
            }
        }
    }

    override fun reset() {
        currentPageNumber = Constants.INITIAL_PAGE_NUMBER
    }
}