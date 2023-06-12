package me.frenz.backend

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Game(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long?
)
