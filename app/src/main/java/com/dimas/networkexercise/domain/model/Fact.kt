package com.dimas.networkexercise.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Fact(
    val facts: List<String>
): Parcelable
