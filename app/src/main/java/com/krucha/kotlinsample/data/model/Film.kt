package com.krucha.kotlinsample.data.model

import androidx.room.*
import com.krucha.kotlinsample.data.room.StringListConverter

@TypeConverters(StringListConverter::class)
@Entity(tableName = Film.Table.NAME,
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = [User.Field.ID],
        childColumns = [Film.Field.USER_ID],
        onDelete = ForeignKey.NO_ACTION)
    ])
data class Film(
    @ColumnInfo(name = Field.NAME) val name: String? = null,
    @ColumnInfo(name = Field.YEAR) val year: Int? = null,
    @ColumnInfo(name = Field.DESCRIPTION) val description: String? = null,
    @ColumnInfo(name = Field.IMAGE_PATH) val imagePath: String? = null,
    @ColumnInfo(name = Field.GENRES) val genres: List<String>? = null,

    @ColumnInfo(name = Field.USER_ID) val userId: String? = null,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Field.ID)
    val id: Long = 0
) {

    object Table {
        const val NAME = "films"
    }

    object Field {
        const val ID = "id"

        const val NAME = "name"
        const val YEAR = "year"
        const val GENRES = "genres"
        const val DESCRIPTION = "description"
        const val IMAGE_PATH = "image_path"
        const val USER_ID = "user_id"
    }
}