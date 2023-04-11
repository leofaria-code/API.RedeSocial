package code.leofaria.apiredesocial.controller;

import code.leofaria.apiredesocial.entity.Profile;
import code.leofaria.apiredesocial.mapper.ProfileMapper;
import code.leofaria.apiredesocial.service.ProfileService;
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
@RequestMapping("/users")
public class ProfileController {
    private final ProfileService service;
    private final ProfileMapper mapper;
    
    @GetMapping
    public ResponseEntity<List<Profile>> getAll(){
        return new ResponseEntity<>(service.listAllProfiles(), HttpStatus.OK);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Profile> getById(@PathVariable Long id){
        HttpStatus httpStatus = presenceStatus(Optional.ofNullable(service.findById(id)));
        if (httpStatus==HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(httpStatus);
        } return new ResponseEntity<>(service.findById(id), httpStatus);
    }
    
    @GetMapping("{username}")
    public ResponseEntity<Profile> getById(@PathVariable String username){
        HttpStatus httpStatus = presenceStatus(Optional.ofNullable(service.findByUsername(username)));
        if (httpStatus==HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(httpStatus);
        } return new ResponseEntity<>(service.findByUsername(username), httpStatus);
    }
    
    @PostMapping
    public ResponseEntity<Profile> post(@RequestBody Profile profile){
        return new ResponseEntity<>(service.save(profile), HttpStatus.CREATED);
    }
    
    @PutMapping
    public ResponseEntity<Profile> put(@RequestBody Profile profile){
        Optional<Profile> profileToEdit = Optional.ofNullable(service.findById(profile.getProfileID()));
        String msgAction = "Profile ID #%06d: ".formatted(profile.getProfileID());
        HttpStatus httpStatus = presenceStatus(profileToEdit);
        if(httpStatus == HttpStatus.NOT_FOUND){
            String msgNotFound = "NÃO ENCONTRADO!\n";
            String msg = msgAction + msgNotFound;
            responseMsgAndStatus(msg, httpStatus);
            return new ResponseEntity<>(httpStatus);
        } else {
            Profile profileUpdating = new Profile();
            profileUpdating.setProfileID(profile.getProfileID());
            profileUpdating.setUsername(profile.getUsername());
            profileUpdating.setPassword(profile.getPassword());
            return new ResponseEntity<>(service.save(profileUpdating), httpStatus);
        }
    }
    
    @PutMapping("{id}")
    public ResponseEntity<Profile> put(@PathVariable Long id, @RequestBody Profile profile){
        Optional<Profile> profileToEdit = Optional.ofNullable(service.findById(id));
        String msgAction = "Profile ID #%06d: ".formatted(id);
        HttpStatus httpStatus = presenceStatus(profileToEdit);
        if(httpStatus == HttpStatus.NOT_FOUND){
            String msgNotFound = "NÃO ENCONTRADO!\n";
            String msg = msgAction + msgNotFound;
            responseMsgAndStatus(msg, httpStatus);
            return new ResponseEntity<>(httpStatus);
        } else {
            Profile profileUpdating = new Profile();
            profileUpdating.setProfileID(id);
            profileUpdating.setUsername(profile.getUsername());
            profileUpdating.setPassword(profile.getPassword());
            return new ResponseEntity<>(service.save(profileUpdating), httpStatus);
        }
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        String msgAction = "Profile ID #%06d: ".formatted(id);
        HttpStatus httpStatus = presenceStatus(Optional.ofNullable(service.findById(id)));
        if(httpStatus == HttpStatus.NOT_FOUND){
            String msgNotFound = "NÃO ENCONTRADO!\n";
            msgAction = msgAction + msgNotFound;
        }else{
            service.deleteById(id);
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
