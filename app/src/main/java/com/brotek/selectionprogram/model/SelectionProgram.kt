package com.brotek.selectionprogram.model


class SelectionProgram : ArrayList<SelectionProgramItem>()

data class SelectionProgramItem(
    val data_content: List<DataContent>,
    val data_pp: List<DataPp>,
    val hour: String,
    val isAutoAddVersion: String,
    val isPause: String,
    val minute: String,
    val programId: String,
    val remark: String,
    val setDate: String,
    val setName: String,
    val vendorTitle: String
)

data class DataContent(
    val pressure: String,
    val pressureTime: String,
    val thermo: String,
    val thermoTime: String
)

data class DataPp(
    val modelTitle: String,
    val typeTitle: String
)