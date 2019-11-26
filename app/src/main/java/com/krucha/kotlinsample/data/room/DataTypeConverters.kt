package com.krucha.kotlinsample.data.room

import androidx.room.TypeConverter
import com.krucha.kotlinsample.data.model.User
import java.util.*

class DateTypeConverter {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }
}


class GenderTypeConverter {

    @TypeConverter
    fun fromGender(gender: User.Gender) : Int {
        return gender.ordinal
    }

    @TypeConverter
    fun toGender(ordinal: Int) : User.Gender {
        return User.Gender.values()[ordinal]
    }
}