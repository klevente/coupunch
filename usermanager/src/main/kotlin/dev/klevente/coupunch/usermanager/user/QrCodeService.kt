package dev.klevente.coupunch.usermanager.user

import java.awt.image.BufferedImage

interface QrCodeService {
    fun getQrCodeAsImage(userId: Long): BufferedImage

    fun generateQrCodeFor(userId: Long)
}