package com.netlight.cleanarchitecturesample.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.netlight.cleanarchitecturesample.domain.entity.Answer
import com.netlight.cleanarchitecturesample.domain.entity.Item
import com.netlight.cleanarchitecturesample.domain.logger.Logger
import com.netlight.cleanarchitecturesample.domain.usecase.CreateItemUseCase
import com.netlight.cleanarchitecturesample.domain.usecase.DeleteItemUseCase
import com.netlight.cleanarchitecturesample.domain.usecase.GetAnswerUseCase
import com.netlight.cleanarchitecturesample.domain.usecase.GetItemsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TasksViewModel(
    private val getItemsUseCase: GetItemsUseCase,
    private val createItemUseCase: CreateItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val getAnswerUseCase: GetAnswerUseCase,
    private val logger: Logger
) : ViewModel(), CoroutineScope {

    private val _tasks = MutableLiveData<List<Item>>()
    val tasks: LiveData<List<Item>> = _tasks

    private val _answers = MutableLiveData<Answer>()
    val answers: LiveData<Answer> = _answers

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            logger.e("TasksViewModel", "Error on coroutine", throwable)
        }

    init {
        launch {
            getItemsUseCase.execute()
                .catch { logger.d("TasksViewModel", "$it") }
                .collect { _tasks.postValue(it) }
        }
    }

    public override fun onCleared() {
        coroutineContext.cancel()
    }

    fun getAnswer() {
        launch {
            val answer = getAnswerUseCase.execute()
            _answers.postValue(answer)
        }
    }

    fun create(description: String) {
        launch {
            createItemUseCase.execute(description)
        }
    }

    fun delete(item: Item) {
        launch {
            deleteItemUseCase.execute(item)
        }
    }
}