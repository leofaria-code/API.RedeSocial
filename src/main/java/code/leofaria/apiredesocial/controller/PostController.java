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
    
    @GetMapping("{id}")
    public ResponseEntity<Post> getById(@PathVariable Long id){
        Optional<Post> postToShow = postRepository.findById(id);
        String msgAction = "Post ID #%06d: ".formatted(id);
        HttpStatus httpStatus;
        if(postToShow.isPresent()){
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(postRepository.getReferenceById(id), httpStatus);
        }else{
            httpStatus = HttpStatus.NOT_FOUND;
            String msgNotFound = "NÃO ENCONTRADO!\n";
            String msg = msgAction + msgNotFound;
            responseMsgAndStatus(msg, httpStatus);
            return new ResponseEntity<>(httpStatus);
        }
    }
    
    @PostMapping
    public ResponseEntity<Post> post(@RequestBody Post post){
        return new ResponseEntity<>(postRepository.save(post), HttpStatus.CREATED);
    }
    
    @PutMapping
    public ResponseEntity<Post> put(@RequestBody Post post){
        Optional<Post> postToEdit = postRepository.findById(post.getPostID());
        HttpStatus httpStatus;
        String msgAction = "Post ID #%06d: ".formatted(post.getPostID());
        if(postToEdit.isPresent()){
            post.setTimestampPostCreated(postToEdit.get().getTimestampPostCreated());
            post.setTimestampPostUpdated(LocalDateTime.now());
            httpStatus = HttpStatus.ACCEPTED;
            return new ResponseEntity<>(postRepository.save(post), httpStatus);
        }else{
            httpStatus = HttpStatus.NOT_FOUND;
            String msgNotFound = "NÃO ENCONTRADO!\n";
            String msg = msgAction + msgNotFound;
            responseMsgAndStatus(msg, httpStatus);
            return new ResponseEntity<>(httpStatus);
        }
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<Post> postToDelete = postRepository.findById(id);
        String msgAction = "Post ID #%06d: ".formatted(id);
        LocalDateTime timestamp = LocalDateTime.now();
        HttpStatus httpStatus;
        if(postToDelete.isPresent()){
            postRepository.deleteById(id);
            String msgFound = "ENCONTRADO e DELETADO permanentemente!\n";
            msgAction = msgAction + msgFound;
            httpStatus = HttpStatus.GONE;
        }else{
            String msgNotFound = "NÃO ENCONTRADO!\n";
            msgAction = msgAction + msgNotFound;
            httpStatus = HttpStatus.NOT_FOUND;
        }
        String formattedTimestamp = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return responseMsgAndStatus(msgAction+formattedTimestamp, httpStatus);
    }
    
    private ResponseEntity<String> responseMsgAndStatus(String msg, HttpStatus httpStatus){
        new ResponseEntity<>(msg, httpStatus);
        return null;
    }
}
