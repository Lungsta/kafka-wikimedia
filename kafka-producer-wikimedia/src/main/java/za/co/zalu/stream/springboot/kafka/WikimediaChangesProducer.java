package za.co.zalu.stream.springboot.kafka;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import za.co.zalu.stream.springboot.eventhandler.WikimediaChangesHandler;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);
    private final KafkaTemplate<String, String> KAFKA_TEMPLATE;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.KAFKA_TEMPLATE = kafkaTemplate;
    }

    /**
     * To read real time stream data from wikimedia, we use event source.
     *
     */
    public void send() {
        String topic = "zalu_wikimedia-recentchange";
        EventHandler eventHandler = new WikimediaChangesHandler(KAFKA_TEMPLATE, topic);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        try (EventSource eventSource = builder.build()) {
            eventSource.start();
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            LOGGER.error("error sending changes", e);
            throw new RuntimeException(e);
        }
    }


}
