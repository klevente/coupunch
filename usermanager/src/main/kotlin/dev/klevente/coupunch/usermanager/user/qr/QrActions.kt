package dev.klevente.coupunch.usermanager.user.qr

import java.awt.image.BufferedImage
import javax.servlet.http.HttpServletResponse

interface QrActions {
    fun getQrCodeAsImage(userId: Long): BufferedImage

    fun getQrCodeAsImageForCurrentUser(): BufferedImage

    fun updateQrCodeFor(userId: Long)

    fun updateQrCodeForCurrentUser()

    fun exportQrForCurrentUser(response: HttpServletResponse)
}