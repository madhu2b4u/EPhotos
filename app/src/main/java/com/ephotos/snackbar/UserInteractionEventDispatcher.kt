package com.ephotos.snackbar

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.core.view.doOnNextLayout
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.launch

class UserInteractionEventDispatcher {
    private val observerList = mutableListOf<UserInteractionListener>()

    fun setup(scope: LifecycleCoroutineScope, userInteractionViews: List<View>) {
        scope.launch {
            userInteractionViews.forEach { view ->
                setupTouchListener(view)
                if (view is EditText) {
                    setupAfterTextChangedListener(view)
                }
            }
        }
    }

    private fun setupAfterTextChangedListener(view: EditText) {
        view.doOnNextLayout {
            view.doAfterTextChanged { dispatchActionEvent(UserInteractionType.TextChanged) }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTouchListener(view: View) {
        view.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                dispatchActionEvent(UserInteractionType.MotionEvent(event))
            }
            false
        }
    }

    private fun dispatchActionEvent(eventType: UserInteractionType) {
        observerList.forEach {
            if (it.isActive()) {
                it.onUserInteracted(eventType)
            }
        }
    }

    fun addInteractionListener(listener: UserInteractionListener) {
        observerList.add(listener)
    }

    fun removeInteractionListener(listener: UserInteractionListener) {
        observerList.remove(listener)
    }
}

abstract class UserInteractionListener {

    private var isListenerActive = true

    fun isActive(): Boolean {
        return isListenerActive
    }

    fun setActive(active: Boolean) {
        isListenerActive = active
    }

    abstract fun onUserInteracted(eventType: UserInteractionType)
}

sealed class UserInteractionType {
    data class MotionEvent(val event: android.view.MotionEvent) : UserInteractionType()
    object TextChanged : UserInteractionType()
}