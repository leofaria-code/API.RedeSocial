package code.leofaria.apiredesocial.controller;

import code.leofaria.apiredesocial.entity.Post;
import code.leofaria.apiredesocial.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostRepository postRepository;
    
    public PostController(@Autowired PostRepository postRepository){
        this.postRepository = postRepository;
    }
    
    @GetMapping
    public ResponseEntity<List<Post>> getAll(){
        return new ResponseEntity<>(postRepository.findAll(), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Post> post(@RequestBody Post post){
        post.setTimestampPostCreated(LocalDateTime.now());
        return new ResponseEntity<>(postRepository.save(post), HttpStatus.CREATED);
    }
    
//    @PutMapping("/post/{id}")
    @PutMapping
    public ResponseEntity<Post> put(@RequestBody Post post){
        Optional<Post> postToEdit = postRepository.findById(post.getId());
        HttpStatus httpStatus;
        if(postToEdit.isPresent()){
            post.setTimestampPostCreated(postToEdit.get().getTimestampPostCreated());
            post.setTimestampPostUpdated(LocalDateTime.now());
            httpStatus = HttpStatus.ACCEPTED;
            return new ResponseEntity<>(postRepository.save(post), httpStatus);
        }else{
            httpStatus = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(httpStatus);
        }

//        return new ResponseEntity<>(postRepository.save(post), httpStatus);
    }
    
    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<Post> postToDelete = postRepository.findById(id);
        String msgAction = "Post %s: ".formatted(id.toString());
        HttpStatus httpStatus;
        if(postToDelete.isPresent()){
            postRepository.deleteById(id);
            String msgFound = "ENCONTRADO e DELETADO permanentemente!/n";
            msgAction = msgAction + msgFound;
            httpStatus = HttpStatus.GONE;
        }else{
            String msgNotFound = "NÃO ENCONTRADO!/n";
            msgAction = msgAction + msgNotFound;
            httpStatus = HttpStatus.NOT_FOUND;
        }
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
        return new ResponseEntity<>(msgAction + timestamp, httpStatus);
    }
}
