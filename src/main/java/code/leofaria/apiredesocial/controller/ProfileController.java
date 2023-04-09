package code.leofaria.apiredesocial.controller;

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
        Optional<Profile> profileToShow = profileRepository.findById(id);
        String msgAction = "Profile ID #%06d: ".formatted(id);
        HttpStatus httpStatus;
        if(profileToShow.isPresent()){
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(profileRepository.getReferenceById(id), httpStatus);
        }else{
            httpStatus = HttpStatus.NOT_FOUND;
            String msgNotFound = "NÃO ENCONTRADO!\n";
            String msg = msgAction + msgNotFound;
            responseMsgAndStatus(msg, httpStatus);
            return new ResponseEntity<>(httpStatus);
        }
    }
    
    @PostMapping
    public ResponseEntity<Profile> post(@RequestBody Profile profile){
        return new ResponseEntity<>(profileRepository.save(profile), HttpStatus.CREATED);
    }
    
    @PutMapping
    public ResponseEntity<Profile> put(@RequestBody Profile profile){
        Optional<Profile> profileToEdit = profileRepository.findById(profile.getProfileID());
        HttpStatus httpStatus;
        String msgAction = "Profile ID #%06d: ".formatted(profile.getProfileID());
        if(profileToEdit.isPresent()){
            httpStatus = HttpStatus.ACCEPTED;
            return new ResponseEntity<>(profileRepository.save(profile), httpStatus);
        }else{
            httpStatus = HttpStatus.NOT_FOUND;
            String msgNotFound = "NÃO ENCONTRADO!\n";
            String msg = msgAction + msgNotFound;
            responseMsgAndStatus(msg, httpStatus);
            return new ResponseEntity<>(httpStatus);
        }
    }
    
    private ResponseEntity<String> responseMsgAndStatus(String msg, HttpStatus httpStatus){
        new ResponseEntity<>(msg, httpStatus);
        return null;
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<Profile> profileToDelete = profileRepository.findById(id);
        String msgAction = "Post ID #%06d: ".formatted(id);
        LocalDateTime timestamp = LocalDateTime.now();
        HttpStatus httpStatus;
        if(profileToDelete.isPresent()){
            profileRepository.deleteById(id);
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
}
