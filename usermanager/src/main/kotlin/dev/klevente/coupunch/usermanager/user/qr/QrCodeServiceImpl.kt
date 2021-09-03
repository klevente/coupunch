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
import java.io.ByteArrayInputStream
import javax.servlet.http.HttpServletResponse

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
    override fun updateQrCodeFor(userId: Long) {
        val user = userService.getUser(userId)
        updateQrCodeFor(user)
    }

    @Transactional
    override fun updateQrCodeForCurrentUser() {
        val currentUser = userService.getCurrentUser()
        updateQrCodeFor(currentUser)
    }

    override fun exportQrForCurrentUser(response: HttpServletResponse) {
        val user = userService.getCurrentUser()
        val qrImage = getQrImageAndGenerateIfNotExists(user)

        response.setHeader("Content-Disposition", "attachment; filename=\"coupunch_${user.username}_qr.png\"")
        qrImage
            .toByteArray()
            .run { ByteArrayInputStream(this) }
            .copyTo(response.outputStream)
    }

    private fun getQrImageAndGenerateIfNotExists(user: User) =
        user.qr?.toBufferedImage() ?: generateQrCodeFor(user)

    private fun updateQrCodeFor(user: User) {
        log.info("Updating unique code for User ${user.id}")

        user.code = uuid()

        generateQrCodeFor(user)
    }

    private fun generateQrCodeFor(user: User): BufferedImage {
        log.info("Generating QR code for User $user.userId")

        val writer = QRCodeWriter()
        val matrix = writer.encode(user.code, BarcodeFormat.QR_CODE, 300, 300)
        val image =  MatrixToImageWriter.toBufferedImage(matrix)

        user.qr = image.toByteArray()

        log.info("Successfully generated QR code for user $user.userId")

        return image
    }
}