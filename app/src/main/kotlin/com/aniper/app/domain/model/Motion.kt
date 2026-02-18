package com.aniper.app.domain.model

enum class Motion(val displayName: String) {
    IDLE("가만히 있기"),
    WALK_LEFT("좌측 이동"),
    WALK_RIGHT("우측 이동"),
    CLICK("클릭"),
    GRAB("잡기"),
    FALL("떨어지기"),
    LAND("착지");

    companion object {
        fun fromOrdinal(ordinal: Int): Motion? = values().getOrNull(ordinal)
    }
}
