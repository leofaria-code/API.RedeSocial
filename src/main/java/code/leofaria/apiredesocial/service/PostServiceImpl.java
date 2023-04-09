package code.leofaria.apiredesocial.service;

import code.leofaria.apiredesocial.exceptions.PostNotFoundException;
import code.leofaria.apiredesocial.entity.Post;
import code.leofaria.apiredesocial.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    
    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }
    
    @Override
    public List<Post> list() {
        return postRepository.findAll();
    }
    
    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }
    @Override
    public Post update(Long id, Post post) {
        if (postRepository.existsById(id)){
            post.setPostID(id);
            return postRepository.save(post);
        }
        throw new PostNotFoundException();
    }
    
    public void delete(Long id) {
        if(!postRepository.existsById(id)) {
            throw new PostNotFoundException();
        } postRepository.deleteById(id);
    }
}
