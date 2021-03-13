package dev.klevente.coupunch.usermanager.util

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

fun BufferedImage.toByteArray(): ByteArray {
    val outputStream = ByteArrayOutputStream()
    ImageIO.write(this, "png", outputStream)
    return outputStream.toByteArray()
}

fun ByteArray.toBufferedImage(): BufferedImage {
    val inputStream = ByteArrayInputStream(this)
    return ImageIO.read(inputStream)
}