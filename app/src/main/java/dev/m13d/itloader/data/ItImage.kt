package dev.m13d.itloader.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItImage(
    val id: Int,
    val url: String,
): Parcelable
