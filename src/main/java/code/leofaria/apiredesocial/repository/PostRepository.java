package code.leofaria.apiredesocial.repository;

import code.leofaria.apiredesocial.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}