package com.brotek.selectionprogram.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Selection")
data class Selection(
        @PrimaryKey var programId: Int,
        var hour: Int,
        var isAutoAddVersion: Int,
        var isPause: Int,
        var minute: Int,
        var remark: String,
        var setDate: String,
        var setName: String,
        var vendorTitle: String,
        var pressure: String,
        var pressureTime: String,
        var thermo: String,
        var thermoTime: String,
        var modelTitle: String,
        var sn: String,
        var typeTitle: String
)

