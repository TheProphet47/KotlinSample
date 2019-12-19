package com.krucha.kotlinsample.screen.detail.film.model

import android.net.Uri
import androidx.annotation.ArrayRes
import com.krucha.kotlinsample.R
import java.util.*

data class DisplayedDataEdit (
    val name: String? = null,
    val year: Int? = null,
    val currentGenre: List<String>? = null,
    val description: String? = null,
    val imageUri: Uri? = null,
    val determined: Determined = Determined()
) {
    data class Determined(val listYear: List<Int> = (Calendar.getInstance().get(Calendar.YEAR) downTo 1970).toList(),
                          @ArrayRes val listGenre: Int = R.array.arrayGenres)
}