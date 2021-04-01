package dev.klevente.coupunch.usermanager.user

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import dev.klevente.coupunch.usermanager.util.toBufferedImage
import dev.klevente.coupunch.usermanager.util.toByteArray
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.awt.image.BufferedImage

@Service
@Transactional(readOnly = true)
class QrCodeServiceImpl(
    private val log: Logger,
    private val userService: UserService
) : QrCodeService {

    override fun getQrCodeAsImage(userId: Long): BufferedImage {
        return getQrCodeFor(userId)?.toBufferedImage() ?: generateQrCodeFor(userId)

    }

    @Transactional
    override fun generateQrCodeFor(userId: Long): BufferedImage {
        log.info("Generating QR code for User $userId")
        val writer = QRCodeWriter()
        val matrix = writer.encode(userId.toString(), BarcodeFormat.QR_CODE, 200, 200)
        val image =  MatrixToImageWriter.toBufferedImage(matrix)

        val user = userService.getUser(userId)
        user.qr = image.toByteArray()

        log.info("Successfully generated QR code for user $userId")

        return image
    }

    private fun getQrCodeFor(userId: Long) = userService.getUser(userId).qr
}