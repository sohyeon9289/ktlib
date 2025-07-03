package ktlib.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

//<<< PoEAA / Repository

@RepositoryRestResource(collectionResourceRel = "users", path = "users") // 필요 없으면 삭제 가능
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // 이메일 기반 검색 가능
}
