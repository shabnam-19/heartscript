package com.heartscript

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HeartscriptBackendApplication

fun main(args: Array<String>) {
	runApplication<HeartscriptBackendApplication>(*args)
}
