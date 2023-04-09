package code.leofaria.apiredesocial.controller;

import code.leofaria.apiredesocial.entity.Post;
import code.leofaria.apiredesocial.entity.Profile;
import code.leofaria.apiredesocial.repository.ProfileRepository;
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
@RequestMapping("/users")
public class ProfileController {
    private final ProfileRepository profileRepository;
    
    public ProfileController(@Autowired ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }
    
    @GetMapping
    public ResponseEntity<List<Profile>> getAll(){
        return new ResponseEntity<>(profileRepository.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Profile> getById(@PathVariable Long id){
        HttpStatus httpStatus = presenceStatus(profileRepository.findById(id));
        if (httpStatus==HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(httpStatus);
        } return new ResponseEntity<>(profileRepository.getReferenceById(id), httpStatus);
    }
    
    @GetMapping("/user/{id}/posts")
    public ResponseEntity<List<Post>> getPostsByProfileID(@PathVariable Long id){
        HttpStatus httpStatus = presenceStatus(profileRepository.findById(id));
        if (httpStatus==HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(httpStatus);
        } return new ResponseEntity<>(profileRepository.getReferenceById(id).getOwnedPosts(), httpStatus);
    }
    
    @PostMapping
    public ResponseEntity<Profile> post(@RequestBody Profile profile){
        return new ResponseEntity<>(profileRepository.save(profile), HttpStatus.CREATED);
    }
    
    @PutMapping
    public ResponseEntity<Profile> put(@RequestBody Profile profile){
        Optional<Profile> profileToEdit = profileRepository.findById(profile.getProfileID());
        String msgAction = "Profile ID #%06d: ".formatted(profile.getProfileID());
        HttpStatus httpStatus = presenceStatus(profileToEdit);
        if(httpStatus == HttpStatus.NOT_FOUND){
            String msgNotFound = "NÃO ENCONTRADO!\n";
            String msg = msgAction + msgNotFound;
            responseMsgAndStatus(msg, httpStatus);
            return new ResponseEntity<>(httpStatus);
        } return new ResponseEntity<>(profileRepository.save(profile), httpStatus);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        String msgAction = "Profile ID #%06d: ".formatted(id);
        HttpStatus httpStatus = presenceStatus(profileRepository.findById(id));
        if(httpStatus == HttpStatus.NOT_FOUND){
            String msgNotFound = "NÃO ENCONTRADO!\n";
            msgAction = msgAction + msgNotFound;
        }else{
            profileRepository.deleteById(id);
            httpStatus = HttpStatus.GONE;
            String msgFound = "ENCONTRADO e DELETADO permanentemente!\n";
            msgAction = msgAction + msgFound;
        }
        String formattedTimestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return responseMsgAndStatus(msgAction+formattedTimestamp, httpStatus);
    }
    
    private HttpStatus presenceStatus(Optional<Profile> p){
        if (p.isPresent()){
            return HttpStatus.OK;
        } return HttpStatus.NOT_FOUND;
    }
    
    private ResponseEntity<String> responseMsgAndStatus(String msg, HttpStatus httpStatus){
        new ResponseEntity<>(msg, httpStatus);
        return null;
    }
}
