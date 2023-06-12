package me.frenz.backend

enum class Hand {
    ROCK,
    PAPER,
    SCISSORS,
    INVALID;

    override fun toString(): String {
        return name
    }

    companion object {
        fun fromString(s: String): Hand {
            return when (s.uppercase()) {
                "ROCK" -> ROCK
                "PAPER" -> PAPER
                "SCISSORS" -> SCISSORS
                else -> INVALID
            }
        }
    }
}
