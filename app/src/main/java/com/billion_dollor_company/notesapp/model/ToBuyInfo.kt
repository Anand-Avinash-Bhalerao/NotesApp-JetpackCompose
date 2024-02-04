package com.billion_dollor_company.notesapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.billion_dollor_company.notesapp.util.Constants
import java.util.UUID


@Entity(tableName = "to_buy_table")
@RequiresApi(Build.VERSION_CODES.O)
data class ToBuyInfo(
    @PrimaryKey
    val uid: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "to_buy_name")
    val name: String,

    @ColumnInfo(name = "to_buy_status")
    var status: Boolean = Constants.ToBuy.NOT_COMPLETED,

    @ColumnInfo(name = "to_buy_quantity")
    var quantity: String = "",
)