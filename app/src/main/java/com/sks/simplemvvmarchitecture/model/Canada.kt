package com.sks.simplemvvmarchitecture.model

/**
 * @author  Sumit Singh on 8/12/2020.
 */
data class Canada(
    var rows: List<Row>,
    var title: String
) {
    data class Row(
        var description: String,
        var imageHref: String,
        var title: String
    )
}