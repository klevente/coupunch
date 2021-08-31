package dev.klevente.coupunch.usermanager.user.qr

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import dev.klevente.coupunch.usermanager.user.User
import dev.klevente.coupunch.usermanager.user.UserService
import dev.klevente.coupunch.usermanager.util.toBufferedImage
import dev.klevente.coupunch.usermanager.util.toByteArray
import dev.klevente.coupunch.usermanager.util.uuid
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.awt.image.BufferedImage

@Service
@Transactional(readOnly = true)
class QrCodeServiceImpl(
    private val log: Logger,
    private val userService: UserService
) : QrActions {

    @Transactional
    override fun getQrCodeAsImage(userId: Long) =
        getQrImageAndGenerateIfNotExists(userService.getUser(userId))

    @Transactional
    override fun getQrCodeAsImageForCurrentUser() =
        getQrImageAndGenerateIfNotExists(userService.getCurrentUser())

    @Transactional
    override fun updateQrCodeFor(userId: Long): BufferedImage {
        log.info("Updating unique code for User $userId")

        val user = userService.getUser(userId)
        user.code = uuid()

        return generateQrCodeFor(user)
    }

    private fun getQrImageAndGenerateIfNotExists(user: User) =
        user.qr?.toBufferedImage() ?: generateQrCodeFor(user)

    private fun generateQrCodeFor(user: User): BufferedImage {
        log.info("Generating QR code for User $user.userId")

        val writer = QRCodeWriter()
        val matrix = writer.encode(user.code, BarcodeFormat.QR_CODE, 200, 200)
        val image =  MatrixToImageWriter.toBufferedImage(matrix)

        user.qr = image.toByteArray()

        log.info("Successfully generated QR code for user $user.userId")

        return image
    }
}