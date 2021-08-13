package dev.klevente.coupunch.usermanager.user.qr

import java.awt.image.BufferedImage

interface QrCodeService {
    fun getQrCodeAsImage(userId: Long): BufferedImage

    fun updateQrCodeFor(userId: Long): BufferedImage

    fun generateQrCodeFor(userId: Long): BufferedImage
}