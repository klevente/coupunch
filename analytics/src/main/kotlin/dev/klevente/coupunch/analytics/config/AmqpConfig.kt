package dev.klevente.coupunch.analytics.config

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
    fun producerJackson2MessageConverter(): Jackson2JsonMessageConverter = Jackson2JsonMessageConverter()

    @Bean
    fun messageHandlerMethodFactory() = DefaultMessageHandlerMethodFactory()
        .apply {
            setMessageConverter(
                MappingJackson2MessageConverter().apply {
                    objectMapper.registerModule(ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
                }
            )
        }

    @Bean
    fun rabbitListenerConfigurer(messageHandlerMethodFactory: MessageHandlerMethodFactory) = RabbitListenerConfigurer {
        it.messageHandlerMethodFactory = messageHandlerMethodFactory
    }
}