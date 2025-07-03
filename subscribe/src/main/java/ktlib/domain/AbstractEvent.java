package ktlib.domain;

import lombok.Getter;
import java.time.Instant;

@Getter
public abstract class AbstractEvent {

    private final Instant occurredAt;

    protected AbstractEvent() {             // no‑arg
        this.occurredAt = Instant.now();
    }

    protected AbstractEvent(Object aggregate) { // with aggregate
        this();
        // 필요하면 aggregate 정보 활용
    }

    public void publishAfterCommit() {
        System.out.println("Event published: " + this);
        // 실제 메시지 발행 로직 연결 (Kafka 등)
    }
}
