package za.co.zalu.stream.springboot.eventhandler;


import com.launchdarkly.eventsource.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import com.launchdarkly.eventsource.EventHandler;


public class WikimediaChangesHandler implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesHandler.class);
    private final KafkaTemplate<String, String> KAFKA_TEMPLATE;
    private final String TOPIC;

    public WikimediaChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.KAFKA_TEMPLATE = kafkaTemplate;
        this.TOPIC = topic;
    }

    @Override
    public void onOpen() throws Exception {

    }

    @Override
    public void onClosed() throws Exception {

    }
    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        String messageData = messageEvent.getData();
        LOGGER.info("onMessage: {}", messageData);
        KAFKA_TEMPLATE.send(TOPIC, messageData);
    }

    @Override
    public void onComment(String s) throws Exception {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
