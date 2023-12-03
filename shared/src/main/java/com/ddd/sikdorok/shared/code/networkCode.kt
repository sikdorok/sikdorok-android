package com.ddd.sikdorok.shared.code

import com.google.gson.annotations.SerializedName

enum class Tag(val code: String) {
    @SerializedName("C000300001")
    MORNING("C000300001"),

    @SerializedName("C000300002")
    LUNCH("C000300002"),

    @SerializedName("C000300003")
    DINNER("C000300003"),

    @SerializedName("C000300004")
    SNACK("C000300004");

    companion object {
        fun findTag(code: String): Tag {
            return values().firstOrNull { it.code == code } ?: Tag.MORNING
        }
    }
}

enum class Icon(val code: String) {
    @SerializedName("C000400001")
    NOTHING("C000400001"),

    @SerializedName("C000400002")
    RICE("C000400002"),

    @SerializedName("C000400003")
    NOODLE("C000400003"),

    @SerializedName("C000400004")
    SALAD("C000400004"),

    @SerializedName("C000400005")
    MEAT("C000400005"),

    @SerializedName("C000400006")
    BREAD("C000400006"),

    @SerializedName("C000400007")
    FAST_FOOD("C000400007"),

    @SerializedName("C000400008")
    SUSHI("C000400008"),

    @SerializedName("C000400009")
    CAKE("C000400009");

    companion object {
        fun findIcon(code: String): Icon {
            return values().firstOrNull { it.code == code } ?: Icon.NOTHING
        }
    }
}
