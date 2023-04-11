package code.leofaria.apiredesocial.service;

import code.leofaria.apiredesocial.entity.Post;
import java.util.List;

public interface PostService {
    List<Post> listAllPosts();
    List<Post> findByIdProfile(Long id);
    Post findById(Long id);
    Post save(Post post);
    Post update(Long id, Post post);
//    void delete(Long id);
    List<Post> findByProfileID(Long profileId);
    
    void deleteById(Long id);
    
}
