import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import ktlib.domain.BookSubscriptionCancelCommand;
import ktlib.domain.BookSubscriptionCancelledEvent;

@Service
@RequiredArgsConstructor
public class SubscriptionAggregate {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "BookSubscriptionCancel", groupId = "group_id")
    public void handleCancelCommand(BookSubscriptionCancelCommand command) {
        System.out.println("Subscription cancelled: " + command.getSubscriptionId());

        BookSubscriptionCancelledEvent cancelledEvent = new BookSubscriptionCancelledEvent(
            command.getSubscriptionId(),
            command.getUserId()
        );

        kafkaTemplate.send("BookSubscriptionCancelled", cancelledEvent);
    }
}
