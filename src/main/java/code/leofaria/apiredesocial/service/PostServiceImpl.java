package code.leofaria.apiredesocial.service;

import code.leofaria.apiredesocial.entity.Post;
import code.leofaria.apiredesocial.exceptions.EntityNotFoundException;
import code.leofaria.apiredesocial.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    @Override
    public List<Post> listAllPosts() {
        return postRepository.findAll();
    }
    @Override
    public Post findById(Long id) throws EntityNotFoundException {
        return postRepository.findById(id).orElseThrow();
    }
    @Override
    public List<Post> findByIdProfile(Long id) throws EntityNotFoundException {
        return postRepository.findByProfileID_ProfileID(id).orElseThrow();
    }
    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }
    @Override
    public Post update(Long id, Post postUpdating) throws EntityNotFoundException {
        if (postRepository.existsById(id)){
            postUpdating.setPostID(id);
            postUpdating.setTimestampPostUpdated(LocalDateTime.now());
            return postRepository.save(postUpdating);
        }
        throw new EntityNotFoundException("Post ID #%d não encontrado!".formatted(id));
    }
    @Override
    public void delete(Long id) throws EntityNotFoundException {
        if(!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post ID #%d não encontrado!".formatted(id));
        } postRepository.deleteById(id);
    }
}
