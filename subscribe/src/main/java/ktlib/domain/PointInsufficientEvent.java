package ktlib.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointInsufficientEvent {
    private String subscriptionId;
    private String userId;
    private String reason;
}
