package com.netlight.cleanarchitecturesample.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.netlight.cleanarchitecturesample.R
import com.netlight.cleanarchitecturesample.domain.entity.Answer.Maybe
import com.netlight.cleanarchitecturesample.domain.entity.Answer.No
import com.netlight.cleanarchitecturesample.domain.entity.Answer.Yes
import com.netlight.cleanarchitecturesample.presentation.adapter.TasksAdapter
import com.netlight.cleanarchitecturesample.presentation.viewmodel.TasksViewModel
import kotlinx.android.synthetic.main.fragment_tasks_overview.*
import kotlinx.android.synthetic.main.fragment_tasks_overview.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TasksFragment : Fragment() {
    private val viewModel: TasksViewModel by viewModel()
    private lateinit var adapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_tasks_overview, container, false)

        adapter = TasksAdapter(viewModel)
        view.tasks_list.adapter = adapter

        view.add_task_fab.setOnClickListener {
            fragmentManager?.let {
                CreateTaskFragment.newInstance()
                    .show(it, CreateTaskFragment.TAG)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            no_tasks_group.isVisible = it.isEmpty()
        })

        viewModel.answers.observe(viewLifecycleOwner, Observer { answer ->
            fragmentManager?.let { fm ->
                val uri = when (answer) {
                    is Yes -> answer.imageUrl
                    is No -> answer.imageUrl
                    Maybe -> "android.resource://${context?.packageName}/${R.drawable.maybe}"
                }.toUri()

                GifFragment.newInstance(uri)
                    .show(fm, GifFragment.TAG)
            }
        })
    }
}