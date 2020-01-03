package me.sedlar.scoop_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ScoopApplication

fun main(args: Array<String>) {
    runApplication<ScoopApplication>(*args)
}
