package uk.ac.stir.cs.unitconverter.database

import androidx.room.Entity

/**
 * The template for each entry
 */

@Entity(tableName = "conversion_table", primaryKeys = ["unit1", "unit2"])
data class ConversionEntry(
    val unit1: String,
    val unit2: String,
    val formula: String,
    val category: String,
    val custom: String
)


