package com.ephotos.snackbar

import androidx.annotation.StringRes
import java.io.Serializable

sealed class Message : Serializable {
    data class AsResource(@StringRes val res: Int) : Serializable, Message()
    data class AsString(val message: String) : Serializable, Message()
}