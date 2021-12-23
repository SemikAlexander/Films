package com.example.films.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.*
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.films.R
import com.google.android.material.bottomsheet.BottomSheetDialog

inline fun <reified A : Activity> Context.startActivity(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(this, A::class.java).apply(configIntent))
}

inline fun <reified A : Activity> Fragment.startActivity(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(requireContext(), A::class.java).apply(configIntent))
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()

fun Context.toast(@StringRes messageId: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, messageId, duration).show()

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(activity, message, duration).show()

fun Fragment.toast(@StringRes messageId: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(activity, messageId, duration).show()

fun showBottomSheetDialog(
    context: Context,
    view: View,
    editText: EditText,
    data: List<String>
) {
    val dialog = BottomSheetDialog(context)
    var sport = ""
    var currentValue = 0

    val btnClose = view.findViewById<TextView>(R.id.done)
    val btnNext = view.findViewById<ImageView>(R.id.next)
    val btnPrevious = view.findViewById<ImageView>(R.id.previous)

    val picker = view.findViewById<NumberPicker>(R.id.picker)

    btnClose.setOnClickListener {
        dialog.dismiss()
    }

    picker.minValue = 0
    picker.maxValue = data.size - 1
    picker.wrapSelectorWheel = false
    picker.displayedValues = data.toTypedArray()

    picker.isEnabled = false

    btnPrevious.isEnabled = false
    btnPrevious.setImageResource(R.drawable.ic_keyboard_arrow_left_enabled)

    picker.setOnValueChangedListener { _, _, newVal ->
        sport = data[newVal]
    }

    btnClose.setOnClickListener {
        editText.setText(sport)
        dialog.dismiss()
    }

    btnNext.setOnClickListener {
        currentValue++
        picker.value = currentValue
        sport = data[currentValue]

        if (currentValue != picker.maxValue)
            enableButtons(btnNext, btnPrevious)
        else {
            btnNext.isEnabled = false
            btnNext.setImageResource(R.drawable.ic_keyboard_arrow_left_enabled)
        }
    }

    btnPrevious.setOnClickListener {
        currentValue--
        picker.value = currentValue
        sport = data[currentValue]

        if (currentValue != picker.minValue)
            enableButtons(btnNext, btnPrevious)
        else {
            btnPrevious.isEnabled = false
            btnPrevious.setImageResource(R.drawable.ic_keyboard_arrow_left_enabled)
        }
    }

    dialog.setCancelable(false)
    dialog.setContentView(view)
    dialog.show()
}

private fun enableButtons(btnNext: ImageView, btnPrevious: ImageView) {
    btnNext.isEnabled = true
    btnNext.setImageResource(R.drawable.ic_keyboard_arrow_left)
    btnPrevious.isEnabled = true
    btnPrevious.setImageResource(R.drawable.ic_keyboard_arrow_left)
}