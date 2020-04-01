package com.brotek.selectionprogram.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Selection")
data class Selection(
        @PrimaryKey var programId: String,
        var hour: String? = "",
        var isAutoAddVersion: String?= "",
        var isPause: String? ="",
        var minute: String? = "",
        var remark: String? = "",
        var setDate: String? = "",
        var setName: String? = "",
        var vendorTitle: String? = "",
        var data_pp : String? = "",
        var data_content : String? = ""
)

