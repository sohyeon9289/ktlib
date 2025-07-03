package ktlib.domain;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSubscriptionCancelledEvent {
    private String subscriptionId;
    private String userId;
}
