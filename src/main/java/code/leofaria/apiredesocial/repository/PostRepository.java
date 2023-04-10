package code.leofaria.apiredesocial.repository;

import code.leofaria.apiredesocial.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findByProfileID_ProfileID(Long id);
}