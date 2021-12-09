package com.ephotos.snackbar

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

class SnackbarAutoDismissDelegate : UserInteractionListener() {
    private var snackBar: Snackbar? = null

    fun observe(fragment: Fragment, events: SnackbarEvents) {
        events.showSnackBar.observe(fragment.viewLifecycleOwner, {
            it?.let { showSnackbar(fragment.requireView(), it) }
        })
        events.showGlobalSnackBar.observe(fragment.viewLifecycleOwner, {
            it?.let { showSnackbar(fragment.requireActivity().rootContentView, it) }
        })
        events.dismissSnackBar.observe(fragment.viewLifecycleOwner, {
            snackBar?.dismiss()
        })
    }

    override fun onUserInteracted(eventType: UserInteractionType) {
        snackBar?.dismiss()
    }

    private fun showSnackbar(view: View, snackbarData: SnackbarData) {
        snackBar = view.makeSnackbar(snackbarData).apply { show() }
    }
}

val Activity.rootContentView: View
    get() = window.decorView.findViewById(android.R.id.content)