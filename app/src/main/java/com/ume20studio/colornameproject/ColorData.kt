package com.ume20studio.colornameproject

data class ColorData(
    var Yomi: String,
    var Kaki: String,
    var Red: Int,
    var Green: Int,
    var Blue: Int
)

data class ColorResult(
    var Kinji: Double,
    var Kaki: String,
    var Yomi: String,
    var Red: Int,
    var Green: Int,
    var Blue: Int,
    var Code: String
)