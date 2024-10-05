package com.neyogiry.android.sample.pokedex.domain

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(val name: String, val url: String) : Parcelable {

    var averageColor: Color? = null

}