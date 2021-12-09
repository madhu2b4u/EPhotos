package com.ephotos.snackbar

import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.widget.TextViewCompat
import com.ephotos.R
import com.google.android.material.snackbar.Snackbar


fun View.makeErrorSnackbar(error: Message): Snackbar {
    val data = SnackbarData.Builder()
        .setMessage(error)
        .setAction(SnackbarAction(R.string.dismiss))
        .setAlertType(SnackbarAlertType.MAJOR_ALERT)
        .build()
    return makeSnackbar(data)
}

fun View.makeGeneralErrorSnackbar(): Snackbar {
    return makeSnackbar(snackBarGeneralErrorData())
}

fun View.makeNetworkErrorSnackbar(): Snackbar {
    val data = SnackbarData.Builder()
        .setMessage(Message.AsResource(R.string.network_error_message))
        .setAction(SnackbarAction(R.string.ok_text))
        .setAlertType(SnackbarAlertType.MAJOR_ALERT)
        .build()
    return makeSnackbar(data)
}

fun snackBarGeneralErrorData(): SnackbarData {
    return SnackbarData.Builder()
        .setMessage(Message.AsResource(R.string.somethingWentWrong))
        .setAction(SnackbarAction(R.string.dismiss))
        .setAlertType(SnackbarAlertType.MAJOR_ALERT)
        .build()
}

fun View.makeSnackbar(data: SnackbarData): Snackbar {
    val message = snackBarMessage(context, data)
    return Snackbar.make(
        this,
        message,
        data.displayDurationInMillis ?: snackbarDuration(data.alertType)
    ).apply {
        setContentClickDismiss()
        decorateSnackBar(this, data)
        data.action?.let { action -> setAction(action) }
    }
}

private fun Snackbar.setAction(action: SnackbarAction) {
    setAction(view.resources.getString(action.titleRes)) { action.actionCallback?.invoke() }
}

fun View.showSnackbar(data: SnackbarData) {
    makeSnackbar(data).show()
}

private fun snackBarMessage(
    context: Context,
    data: SnackbarData
): String {
    return when (data.message) {
        is Message.AsResource -> context.getString(data.message.res)
        is Message.AsString -> data.message.message
    }
}

private fun snackbarDuration(alertType: SnackbarAlertType) = when (alertType) {
    SnackbarAlertType.MAJOR_POSITIVE_INFORMATION,
    SnackbarAlertType.MAJOR_ALERT -> Snackbar.LENGTH_INDEFINITE
    SnackbarAlertType.MINOR_POSITIVE_INFORMATION,
    SnackbarAlertType.MINOR_NORMAL_INFORMATION,
    SnackbarAlertType.MINOR_ALERT -> Snackbar.LENGTH_LONG
}

fun decorateSnackBar(snackbar: Snackbar, data: SnackbarData) {
    snackbar.apply {
        updateTextStyling()

        val colorsForSnackBar = colorsForSnackbar(context, data)
        val background: Int = colorsForSnackBar.first
        val colorOnSnackbar: Int = colorsForSnackBar.second

        // set colors
        setBackgroundTint(background)
        setTextColor(colorOnSnackbar)
        setActionTextColor(colorOnSnackbar)
    }
}

private fun colorsForSnackbar(context: Context, data: SnackbarData): Pair<Int, Int> {
    val background: Int
    val colorOnSnackbar: Int
    when (data.alertType) {
        SnackbarAlertType.MINOR_POSITIVE_INFORMATION,
        SnackbarAlertType.MAJOR_POSITIVE_INFORMATION -> {
            background = TypedValue().toColor(context, R.attr.colorPositive)
            colorOnSnackbar = TypedValue().toColor(context, R.attr.colorOnPositive)
        }
        SnackbarAlertType.MAJOR_ALERT,
        SnackbarAlertType.MINOR_ALERT -> {
            background = TypedValue().toColor(context, R.attr.colorError)
            colorOnSnackbar = TypedValue().toColor(context, R.attr.colorOnError)
        }
        SnackbarAlertType.MINOR_NORMAL_INFORMATION -> {
            // inverse - needs to be light for dark mode, dark for light mode
            background = TypedValue().toColor(context, R.attr.colorOnSurface)
            colorOnSnackbar = TypedValue().toColor(context, R.attr.colorSurface)
        }
    }.exhaustive

    return Pair(background, colorOnSnackbar)
}


private fun Snackbar.updateTextStyling() {
    val textView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    val textAppearanceResID = context.resolveStyledAttributeRes(R.attr.textAppearanceBody1)
    TextViewCompat.setTextAppearance(textView, textAppearanceResID)
    textView.maxLines = 5
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        textView.setLineSpacing(
            context.resources.getFloat(R.dimen.text_view_line_spacing_extra_0),
            context.resources.getFloat(R.dimen.text_view_line_spacing_multiplier_standard)
        )
    }
}

private fun Snackbar.setContentClickDismiss() {
    view.setOnClickListener {
        dismiss()
    }
}

fun TypedValue.toColor(context: Context, @AttrRes attrRes: Int): Int {
    context.theme.resolveAttribute(attrRes, this, true)
    return data
}

fun TypedValue.readFloatDimen(context: Context, @DimenRes res: Int): Float {
    context.resources.getValue(res, this, true)
    return float
}

@DrawableRes
fun TypedValue.toDrawableRes(context: Context, @AttrRes attrRes: Int): Int {
    context.theme.resolveAttribute(attrRes, this, true)
    return resourceId
}

val <T> T.exhaustive: T
    get() = this

fun Context.resolveStyledAttributeRes(@AttrRes attrRes: Int): Int {
    val attrs = intArrayOf(attrRes)
    val ta = obtainStyledAttributes(attrs)
    val resId = ta.getResourceId(0, 0)
    ta.recycle()

    return resId
}
