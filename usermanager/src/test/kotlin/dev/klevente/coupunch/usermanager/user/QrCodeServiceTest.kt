package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.qr.QrCodeService
import dev.klevente.coupunch.usermanager.user.qr.QrCodeServiceImpl
import io.github.serpro69.kfaker.Faker
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger

@ExtendWith(MockitoExtension::class)
class QrCodeServiceTest {

    private lateinit var qrCodeService: QrCodeService

    @Mock
    private lateinit var log: Logger

    @Mock
    private lateinit var userService: UserService

    val faker = Faker()

    @BeforeEach
    fun setup() {
        qrCodeService = QrCodeServiceImpl(log, userService)
    }
}