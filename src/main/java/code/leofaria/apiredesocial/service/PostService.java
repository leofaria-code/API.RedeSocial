package code.leofaria.apiredesocial.service;

import code.leofaria.apiredesocial.entity.Post;

import java.util.List;

public interface PostService {
    Post findById(Long id);
    List<Post> list();
    Post save(Post post);
    Post update(Long id, Post post);
    void delete(Long id);
//    void delete(Post post);
}
