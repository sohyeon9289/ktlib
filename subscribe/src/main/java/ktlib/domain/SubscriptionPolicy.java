package ktlib.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionPolicy {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // ❶ 신규 구독 → 포인트 차감 로직
    @KafkaListener(topics = "BookSubscriptionCreated", groupId = "group_id")
    public void handleBookSubscriptionCreated(BookSubscriptionCreatedEvent event) {

        // 실제 포인트 조회 로직을 구현하세요
        boolean pointSufficient = false; // 예시: 항상 부족하다고 가정

        if (!pointSufficient) {
            PointInsufficientEvent insufficientEvent = new PointInsufficientEvent(
                    event.getSubscriptionId(),
                    event.getUserId(),
                    "Insufficient points"
            );
            kafkaTemplate.send("PointInsufficient", insufficientEvent);
        }
    }

    // ❷ 포인트 부족 → 구독 취소 커맨드 발행
    @KafkaListener(topics = "PointInsufficient", groupId = "group_id")
    public void handlePointInsufficient(PointInsufficientEvent event) {

        BookSubscriptionCancelCommand cancelCommand = new BookSubscriptionCancelCommand(
                event.getSubscriptionId(),
                event.getUserId()
        );
        kafkaTemplate.send("BookSubscriptionCancel", cancelCommand);
    }
}