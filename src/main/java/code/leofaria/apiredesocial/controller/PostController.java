package code.leofaria.apiredesocial.controller;

import code.leofaria.apiredesocial.dto.PostSaveDTO;
import code.leofaria.apiredesocial.entity.Post;
import code.leofaria.apiredesocial.entity.Profile;
import code.leofaria.apiredesocial.mapper.PostMapper;
import code.leofaria.apiredesocial.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService service;
    private final PostMapper mapper;
    
    @GetMapping
    public ResponseEntity<List<Post>> getAll(){
        return new ResponseEntity<>(service.listAllPosts(), HttpStatus.OK);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Post> getById(@PathVariable Long id){
        Optional<Post> postToShow = Optional.ofNullable(service.findById(id));
        String msgAction = "Post ID #%06d: ".formatted(id);
        HttpStatus httpStatus;
        if(postToShow.isPresent()){
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(service.findById(id), httpStatus);
        }else{
            httpStatus = HttpStatus.NOT_FOUND;
            String msgNotFound = "NÃO ENCONTRADO!\n";
            String msg = msgAction + msgNotFound;
            responseMsgAndStatus(msg, httpStatus);
            return new ResponseEntity<>(httpStatus);
        }
    }
    
    @GetMapping("/user/{profileId}")
    public ResponseEntity<List<Post>> getPostsFromProfileID(@PathVariable Long profileId){
        List<Post> postToShow = service.findByProfileID(profileId);
        String msgAction = "Posts do Perfil ID #%06d: ".formatted(profileId);
        HttpStatus httpStatus;
        if(!postToShow.isEmpty()){
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(service.findByProfileID(profileId), httpStatus);
        }else{
            httpStatus = HttpStatus.NOT_FOUND;
            String msgNotFound = "NÃO ENCONTRADO!\n";
            String msg = msgAction + msgNotFound;
            responseMsgAndStatus(msg, httpStatus);
            return new ResponseEntity<>(httpStatus);
        }
    }
    
    @PostMapping
    public ResponseEntity<Post> post(@Valid @RequestBody PostSaveDTO dto){
        Post post = mapper.postSaveDTOtoPost(dto);
        post.setProfileID(Profile.builder()
                .profileID(dto.getProfileId())
                .build());
        return new ResponseEntity<>(service.save(post), HttpStatus.CREATED);
    }
    
    @PutMapping
    public ResponseEntity<Post> put(@RequestBody Post post){
        Optional<Post> postToEdit = Optional.ofNullable(service.findById(post.getPostID()));
        HttpStatus httpStatus;
        String msgAction = "Post ID #%06d: ".formatted(post.getPostID());
        if(postToEdit.isPresent()){
            post.setTimestampPostUpdated(LocalDateTime.now());
            httpStatus = HttpStatus.ACCEPTED;
            return new ResponseEntity<>(service.save(post), httpStatus);
        }else{
            httpStatus = HttpStatus.NOT_FOUND;
            String msgNotFound = "NÃO ENCONTRADO!\n";
            String msg = msgAction + msgNotFound;
            responseMsgAndStatus(msg, httpStatus);
            return new ResponseEntity<>(httpStatus);
        }
    }
    
    @PutMapping("{id}")
    public ResponseEntity<Post> put(@PathVariable Long id, @RequestBody Post post){
        Optional<Post> postToEdit = Optional.ofNullable(service.findById(id));
        HttpStatus httpStatus;
        String msgAction = "Post ID #%06d: ".formatted(id);
        if(postToEdit.isPresent()){
            post.setTimestampPostUpdated(LocalDateTime.now());
            httpStatus = HttpStatus.ACCEPTED;
            return new ResponseEntity<>(service.update(id, post), httpStatus);
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
        Optional<Post> postToDelete = Optional.ofNullable(service.findById(id));
        String msgAction = "Post ID #%06d: ".formatted(id);
        LocalDateTime timestamp = LocalDateTime.now();
        HttpStatus httpStatus;
        if(postToDelete.isPresent()){
            service.deleteById(id);
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

