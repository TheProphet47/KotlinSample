package com.krucha.kotlinsample.data.model

import androidx.room.*
import com.krucha.kotlinsample.data.room.DateTypeConverter
import com.krucha.kotlinsample.data.room.GenderTypeConverter
import java.util.*

@Entity(tableName = User.Table.NAME)
@TypeConverters(GenderTypeConverter::class, DateTypeConverter::class)
data class User(
    @ColumnInfo(name = Field.EMAIL) val email: String,
    @ColumnInfo(name = Field.NAME) val name: String,
    @ColumnInfo(name = Field.AGE) val age: Int,
    @ColumnInfo(name = Field.GENDER) val gender: Gender,
    @ColumnInfo(name = Field.BIRTHDAY) val birthday: Date,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Field.ID)
    val id: Long = 0
) {

    object Table {
        const val NAME = "users"
    }

    object Field {
        const val ID = "id"

        const val NAME = "name"
        const val EMAIL = "email"
        const val AGE = "age"
        const val GENDER = "gender"
        const val BIRTHDAY = "birthday"
    }

    enum class Gender {
        MAN, WOMAN
    }
}

