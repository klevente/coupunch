package dev.klevente.coupunch.analytics.config

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory

@Configuration
class AmqpConfig {
    @Bean
    fun analyticsTopicExchange(
        @Value("\${amqp.exchange.analytics}") name: String
    ): TopicExchange = ExchangeBuilder
            .topicExchange(name)
            .durable(true)
            .build()

    @Bean
    fun redeemQueue(
        @Value("\${amqp.queue.checkout}") name: String
    ): Queue = QueueBuilder
        .durable(name)
        .build()

    @Bean
    fun binding(
        redeemQueue: Queue,
        analyticsExchange: TopicExchange
    ): Binding = BindingBuilder
        .bind(redeemQueue)
        .to(analyticsExchange)
        .with("analytics.checkout")

    @Bean
    fun producerJackson2MessageConverter(builder: Jackson2ObjectMapperBuilder) = Jackson2JsonMessageConverter(
        jacksonObjectMapper().registerModule(JavaTimeModule())
    )

    @Bean
    fun messageHandlerMethodFactory() = DefaultMessageHandlerMethodFactory()
        .apply {
            setMessageConverter(
                MappingJackson2MessageConverter().apply {
                    objectMapper.registerModules(
                        ParameterNamesModule(JsonCreator.Mode.PROPERTIES),
                        JavaTimeModule()
                    )
                }
            )
        }

    @Bean
    fun rabbitListenerConfigurer(messageHandlerMethodFactory: MessageHandlerMethodFactory) = RabbitListenerConfigurer {
        it.messageHandlerMethodFactory = messageHandlerMethodFactory
    }
}