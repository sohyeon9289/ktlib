package ktlib.domain;

import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.util.Date;  // ← 이거 중요


@Data
@ToString
@NoArgsConstructor
public class CanceldBookSubscribe extends AbstractEvent {

    // 도메인 필드
    private Long subscriptionId;
    private Long bookId;
    private Long userId;
    private Date subscribedAt;
    private Date expiredAt;
    private Date paymentAt;
    private Boolean paymentSuccess;
    private String reason;

    // 생성자 1: reason 포함
    public CanceldBookSubscribe(SubscribeSu aggregate, String reason) {
        super(aggregate);
        this.subscriptionId = aggregate.getSubscriptionId();
        this.bookId        = aggregate.getBookId();
        this.userId        = aggregate.getUserId();
        this.subscribedAt  = aggregate.getSubscribedAt();
        this.expiredAt     = aggregate.getExpiredAt();
        this.paymentAt     = aggregate.getPaymentAt();
        this.paymentSuccess= aggregate.getPaymentSuccess();
        this.reason        = reason;
    }

    // 생성자 2: reason 없이 기본값으로 빈 문자열 넘김
    public CanceldBookSubscribe(SubscribeSu aggregate) {
        this(aggregate, "");
    }
}
