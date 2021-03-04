package dev.klevente.coupunch.usermanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserManagerApplication

fun main(args: Array<String>) {
	runApplication<UserManagerApplication>(*args)
}
