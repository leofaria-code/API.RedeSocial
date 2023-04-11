package code.leofaria.apiredesocial.repository;

import code.leofaria.apiredesocial.entity.Post;
import code.leofaria.apiredesocial.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByProfileID_ProfileID(Long id);
}