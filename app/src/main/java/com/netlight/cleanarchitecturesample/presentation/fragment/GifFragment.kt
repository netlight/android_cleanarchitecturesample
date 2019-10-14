package com.netlight.cleanarchitecturesample.presentation.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import coil.api.load
import com.netlight.cleanarchitecturesample.R
import kotlinx.android.synthetic.main.fragment_answer.view.*

class GifFragment : DialogFragment() {

    private lateinit var gifUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = arguments?.getString(KEY_GIF_URI)
            ?: throw Exception("An uri is needed for this fragment to display. Use the newInstance() method for this.")
        gifUri = Uri.parse(url)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_answer, container)
        view.image.load(gifUri) {
            listener(onSuccess = { _, _ ->
                dialog?.window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
            })
        }
        view.image.setOnClickListener { dismiss() }

        return view
    }

    companion object {
        const val TAG = "GifFragment"
        private const val KEY_GIF_URI = "gif_uri"

        fun newInstance(uri: Uri) = GifFragment().apply {
            arguments = bundleOf(KEY_GIF_URI to uri.toString())
        }
    }
}