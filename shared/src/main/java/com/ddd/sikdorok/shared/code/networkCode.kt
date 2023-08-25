package com.ddd.sikdorok.shared.code

import com.google.gson.annotations.SerializedName

enum class Tag {
    @SerializedName("C000300001") MORNING,
    @SerializedName("C000300002") LUNCH,
    @SerializedName("C000300003") DINER,
    @SerializedName("C000300004") SNACK
}

enum class Icon {
    @SerializedName("C000400001") NOTHING,
    @SerializedName("C000400002") RICE,
    @SerializedName("C000400003") NOODLE,
    @SerializedName("C000400004") SALAD,
    @SerializedName("C000400005") MEAT,
    @SerializedName("C000400006") BREAD,
    @SerializedName("C000400007") FAST_FOOD,
    @SerializedName("C000400008") SUSHI,
    @SerializedName("C000400009") CAKE
}
