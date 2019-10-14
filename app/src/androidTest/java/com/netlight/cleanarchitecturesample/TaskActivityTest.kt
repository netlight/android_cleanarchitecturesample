package com.netlight.cleanarchitecturesample

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.netlight.cleanarchitecturesample.domain.entity.Answer
import com.netlight.cleanarchitecturesample.domain.entity.Item
import com.netlight.cleanarchitecturesample.presentation.activity.MainActivity
import com.netlight.cleanarchitecturesample.presentation.viewmodel.TasksViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TaskActivityTest {

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java, false, false)

    private val viewModel: TasksViewModel = mockk()

    private val tasks = MutableLiveData<List<Item>>()
    private val answers = MutableLiveData<Answer>()

    private val testModule: Module = module(override = true) {
        viewModel { viewModel }
    }

    @Before
    fun setup() {
        loadKoinModules(testModule)

        every { viewModel.onCleared() } just Runs
        every { viewModel.answers } returns answers
        every { viewModel.tasks } returns tasks
    }

    @Test
    fun creationOfTaskCallsViewModel() {
        every { viewModel.create(any()) } just Runs

        val title = "Hello world"
        rule.launchActivity(Intent())

        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withHint(R.string.task_title)).perform(typeText(title))
        onView(withText(R.string.create_task)).perform(click())

        verify { viewModel.create(title) }
    }

    @Test
    fun createTaskButtonIsDisableWhenInputTextIsBlank() {
        rule.launchActivity(Intent())

        onView(withId(R.id.add_task_fab)).perform(click())
        onView(withText(R.string.create_task)).check(matches(not(isEnabled())))
        onView(withHint(R.string.task_title)).perform(typeText("   "))
        onView(withText(R.string.create_task)).check(matches(not(isEnabled())))
        onView(withHint(R.string.task_title)).perform(typeText("a"))
        onView(withText(R.string.create_task)).check(matches(isEnabled()))
    }

    @Test
    fun deletionOfTaskCallsViewModel() {
        every { viewModel.delete(any()) } just Runs

        val item = Item(1, "Hello world")
        tasks.postValue(listOf(item))
        rule.launchActivity(Intent())

        onView(withId(R.id.checkBox)).perform(click())

        verify { viewModel.delete(item) }
    }
}
