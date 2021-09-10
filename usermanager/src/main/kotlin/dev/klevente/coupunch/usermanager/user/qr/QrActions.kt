package dev.klevente.coupunch.usermanager.user.qr

import dev.klevente.coupunch.usermanager.user.qr.dto.QrRedeemRequest
import java.awt.image.BufferedImage
import javax.servlet.http.HttpServletResponse

interface QrActions {
    fun getQrCodeAsImage(userId: Long): BufferedImage

    fun getQrCodeAsImageForCurrentUser(): BufferedImage

    fun updateQrCodeFor(userId: Long)

    fun updateQrCodeForCurrentUser()

    fun exportQrForCurrentUser(response: HttpServletResponse)

    fun getQrCodeWithRedeemedCouponsForCurrentUser(request: QrRedeemRequest): BufferedImage
}