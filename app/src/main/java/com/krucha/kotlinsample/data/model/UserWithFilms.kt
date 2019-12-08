package com.krucha.kotlinsample.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithFilms (
    @Embedded val user: User,

    @Relation(entity = Film::class, parentColumn = User.Field.ID, entityColumn = Film.Field.USER_ID)
    val films: List<Film>
)