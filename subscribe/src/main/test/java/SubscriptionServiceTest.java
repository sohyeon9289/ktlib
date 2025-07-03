import org.junit.Test;
import static org.junit.Assert.*;

public class SubscriptionServiceTest {

    @Test
    public void testCreateSubscription() {
        SubscriptionService service = new SubscriptionService();

        boolean result = service.BookSubscribed("user123", "book456");

        assertTrue(result);
    }
}
