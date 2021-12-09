package com.ephotos.snackbar

import androidx.annotation.StringRes

data class SnackbarData internal constructor(
    val message: Message,
    val action: SnackbarAction?,
    val displayDurationInMillis: Int? = null,
    val alertType: SnackbarAlertType = SnackbarAlertType.MINOR_POSITIVE_INFORMATION
) {
    class Builder {
        private lateinit var message: Message
        private var action: SnackbarAction? = null
        private var displayDurationInMillis: Int? = null
        private var alertType: SnackbarAlertType? = null

        fun setMessage(message: Message) = apply { this.message = message }

        fun setAction(action: SnackbarAction) = apply { this.action = action }

        fun setDisplayDurationInMillis(length: Int) =
            apply { this.displayDurationInMillis = length }

        fun setAlertType(alertType: SnackbarAlertType) = apply { this.alertType = alertType }

        fun build(): SnackbarData {
            if (!::message.isInitialized) throw InvalidMessageException()
            if (alertType == null) throw InvalidAlertTypeException()

            return SnackbarData(
                message = message,
                action = action,
                displayDurationInMillis = displayDurationInMillis,
                alertType = alertType!!
            )
        }
    }
}

class InvalidMessageException : java.lang.Exception()
class InvalidAlertTypeException : java.lang.Exception()

data class SnackbarAction(
    @StringRes val titleRes: Int,
    val actionCallback: (() -> Unit)? = null
) {
    //  This equals method is specifically to ignore actionCallback
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SnackbarAction

        if (titleRes != other.titleRes) return false

        return true
    }

    override fun hashCode(): Int {
        return titleRes
    }
}

enum class SnackbarAlertType {
    MINOR_NORMAL_INFORMATION,
    MAJOR_POSITIVE_INFORMATION,
    MINOR_POSITIVE_INFORMATION,
    MAJOR_ALERT,
    MINOR_ALERT
}


