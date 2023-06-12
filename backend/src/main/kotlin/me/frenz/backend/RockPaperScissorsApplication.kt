package me.frenz.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
class RockpaperscissorsApplication

fun main(args: Array<String>) {
    runApplication<RockpaperscissorsApplication>(*args)
}
