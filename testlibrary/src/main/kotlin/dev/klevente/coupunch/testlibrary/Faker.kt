package dev.klevente.coupunch.testlibrary

import io.github.serpro69.kfaker.Faker
import kotlin.random.Random

private val whitespace = "\\s".toRegex()

private val random = Random(System.currentTimeMillis())

private fun String.removeWhitespace() = replace(whitespace, "")

fun Faker.username() = name.name().removeWhitespace()

fun Faker.email() = internet.safeEmail()

fun Faker.password() = "${dcComics.title()}${lorem.punctuation()}${address.buildingNumber()}".removeWhitespace()

fun Faker.randomChars() = device.serial()

fun Faker.randomLong() = random.nextLong(1, 100)

fun Faker.randomInt() = random.nextInt(1, 100)

fun Faker.randomDouble() = random.nextDouble(1.0, 1000.0)