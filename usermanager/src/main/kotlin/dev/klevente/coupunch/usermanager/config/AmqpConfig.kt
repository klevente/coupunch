package dev.klevente.coupunch.usermanager.config

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
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
    fun companyTopicExchange(
        @Value("\${amqp.exchange.company}") name: String
    ): TopicExchange = ExchangeBuilder
        .topicExchange(name)
        .durable(true)
        .build()

    @Bean
    fun companyQueue(
        @Value("\${amqp.queue.company}") name: String
    ): Queue = QueueBuilder
        .durable(name)
        .build()

    @Bean
    fun companyUpdateBinding(
        @Qualifier("companyQueue") companyQueue: Queue,
        @Qualifier("companyTopicExchange") companyTopicExchange: TopicExchange
    ): Binding = BindingBuilder
        .bind(companyQueue)
        .to(companyTopicExchange)
        .with("company.update")

    @Bean
    fun customerTopicExchange(
        @Value("\${amqp.exchange.customer}") name: String
    ): TopicExchange = ExchangeBuilder
        .topicExchange(name)
        .durable(true)
        .build()

    @Bean
    fun customerQueue(
        @Value("\${amqp.queue.customer}") name: String
    ): Queue = QueueBuilder
        .durable(name)
        .build()

    @Bean
    fun customerAddBinding(
        @Qualifier("customerQueue") customerQueue: Queue,
        @Qualifier("customerTopicExchange") customerTopicExchange: TopicExchange
    ): Binding = BindingBuilder
        .bind(customerQueue)
        .to(customerTopicExchange)
        .with("customer.add")

    @Bean
    fun producerJackson2MessageConverter(): Jackson2JsonMessageConverter = Jackson2JsonMessageConverter()

    @Bean
    fun messageHandlerMethodFactory(): DefaultMessageHandlerMethodFactory = DefaultMessageHandlerMethodFactory()
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