package dev.klevente.coupunch.usermanager.user.qr

import java.awt.image.BufferedImage

interface QrActions {
    fun getQrCodeAsImage(userId: Long): BufferedImage

    fun getQrCodeAsImageForCurrentUser(): BufferedImage

    fun updateQrCodeFor(userId: Long): BufferedImage
}