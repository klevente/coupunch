package dev.klevente.coupunch.couponmanager.config

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory

@Configuration
class AmqpConfig {
    @Bean
    fun userTopicExchange(
        @Value("\${amqp.exchange.user}") name: String
    ): TopicExchange = ExchangeBuilder
        .topicExchange(name)
        .durable(true)
        .build()

    @Bean
    fun couponManagerQueue(
        @Value("\${amqp.queue.couponmanager}") name: String
    ): Queue = QueueBuilder
        .durable(name)
        .build()

    @Bean
    fun userUpdateBinding(
        couponManagerQueue: Queue,
        @Qualifier("userTopicExchange") userExchange: TopicExchange
    ): Binding = BindingBuilder
        .bind(couponManagerQueue)
        .to(userExchange)
        .with("user.update")

    @Bean
    fun companyTopicExchange(
        @Value("\${amqp.exchange.company}") name: String
    ): TopicExchange = ExchangeBuilder
        .topicExchange(name)
        .durable(true)
        .build()

    @Bean
    fun customerTopicExchange(
        @Value("\${amqp.exchange.customer}") name: String
    ): TopicExchange = ExchangeBuilder
        .topicExchange(name)
        .durable(true)
        .build()

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