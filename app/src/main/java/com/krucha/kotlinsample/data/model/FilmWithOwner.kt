package com.krucha.kotlinsample.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class FilmWithOwner (
    @Embedded val film: Film,

    @Relation(entity = User::class, parentColumn = Film.Field.USER_ID,
        entityColumn = User.Field.ID, projection = [User.Field.NAME])
    val owner: String?
)