package za.co.zalu.stream.springboot.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic kafkaTopic() {
        return new NewTopic("zalu_wikimedia-recentchange", 3, (short) 1);
    }
}
