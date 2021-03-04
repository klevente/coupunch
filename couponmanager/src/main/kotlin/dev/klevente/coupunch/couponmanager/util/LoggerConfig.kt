package dev.klevente.coupunch.couponmanager.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class LoggerConfig {
    @Bean
    @Scope("prototype")
    fun logger(injectionPoint: InjectionPoint): Logger = LoggerFactory.getLogger(
        injectionPoint.methodParameter?.containingClass // constructor
            ?: injectionPoint.field?.declaringClass // or field injection
    )
}