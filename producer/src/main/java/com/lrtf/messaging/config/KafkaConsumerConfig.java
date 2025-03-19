package com.lrtf.messaging.config;

import java.util.Map;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String>
    kafkaListenerContainerFactory(KafkaProperties kafkaProperties) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(kafkaProperties));
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> consumerProps = kafkaProperties.buildConsumerProperties(null);
        return new DefaultKafkaConsumerFactory<>(consumerProps);
    }

}
