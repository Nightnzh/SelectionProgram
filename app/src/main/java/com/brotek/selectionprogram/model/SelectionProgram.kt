package com.brotek.selectionprogram.model

class SelectionProgram : ArrayList<SelectionProgramItem>()

data class SelectionProgramItem(
    val data_content: List<Any>? = listOf(),
    val data_pp: List<DataPp>? = listOf(),
    val hour: String? = null,
    val isAutoAddVersion: String ? = "",
    val isPause: String,
    val minute: String? = "",
    val programId: String,
    val remark: String? = "",
    val setDate: String? = "",
    val setName: String? ="",
    val vendorTitle: String? = ""
)

data class DataPp(
    val modelTitle: String,
    val typeTitle: String
)