package dev.klevente.coupunch.couponmanager.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

interface AuthenticationFacade {
    val authentication: Authentication
    // val user: AuthUser
    // val userId: Long
    val remoteAddress: String
    val authInfo: String
}

@Component
class AuthenticationFacadeImpl : AuthenticationFacade {
    override val authentication: Authentication
        get() = SecurityContextHolder.getContext().authentication
    // override val user: AuthUser
    //    get() = authentication.principal as AuthUser
    // override val userId: Long
    //    get() = user.getId()
    override val remoteAddress: String
        get() = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request.remoteAddr
    override val authInfo: String
    //    get() = "(User: $userId, ${user.username}, ${user.authorities} from $remoteAddress)"
        get() = "User: $authentication from $remoteAddress"
}