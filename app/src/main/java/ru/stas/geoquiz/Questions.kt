package ru.stas.geoquiz

import androidx.annotation.StringRes

data class Questions(@StringRes val textResId: Int, val answer: Boolean)
