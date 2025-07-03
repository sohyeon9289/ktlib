package ktlib.domain;

import java.time.LocalDate;
import java.util.*;
import ktlib.domain.*;
import ktlib.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorRegistered extends AbstractEvent {

    private Long authorId;
    private String authorName;
    private String phoneNumber;
    private String email;
    private String status;
    private String authorNickname;

public AuthorRegistered(Author aggregate) {
    super(aggregate);
    this.authorId = aggregate.getAuthorId();
    this.authorName = aggregate.getAuthorName();
    this.phoneNumber = aggregate.getPhoneNumber();
    this.email = aggregate.getEmail();
    this.status = aggregate.getStatus();
    this.authorNickname = aggregate.getAuthorNickname();
    }

    public AuthorRegistered() {
        super();
    }
}
//>>> DDD / Domain Event
