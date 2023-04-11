package code.leofaria.apiredesocial.service;

import code.leofaria.apiredesocial.entity.Profile;

import java.util.List;

public interface ProfileService {
    Profile findById(Long id);
    Profile findByUsername(String username);
    List<Profile> listAllProfiles();
    Profile save(Profile profile);
    Profile update(Long id, Profile profile);
    void deleteById(Long id);
}
