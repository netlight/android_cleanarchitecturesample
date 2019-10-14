package com.netlight.cleanarchitecturesample.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.netlight.cleanarchitecturesample.R
import com.netlight.cleanarchitecturesample.presentation.viewmodel.TasksViewModel
import kotlinx.android.synthetic.main.fragment_create_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateTaskFragment : DialogFragment() {

    private val vm: TasksViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_create_task, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        taskText.addTextChangedListener {
            createButton.isEnabled = it?.toString()?.isNotBlank() ?: false
        }

        createButton.setOnClickListener {
            vm.create(taskText.text.toString())
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()

        dialog?.window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
    }

    companion object {
        const val TAG = "CreateTaskFragment"

        fun newInstance(): CreateTaskFragment {
            return CreateTaskFragment()
        }
    }
}