package code.leofaria.apiredesocial.service;

import code.leofaria.apiredesocial.entity.Post;
import code.leofaria.apiredesocial.entity.Profile;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post findById(Long id);
    List<Post> findByIdProfile(Long id);
    List<Post> listAllPosts();
    Post save(Post post);
    Post update(Long id, Post post);
    void delete(Long id);
}
