package code.leofaria.apiredesocial.service;

import code.leofaria.apiredesocial.entity.Profile;
import code.leofaria.apiredesocial.exceptions.EntityNotFoundException;
import code.leofaria.apiredesocial.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{
    private final ProfileRepository profileRepository;
    
    @Override
    public List<Profile> listAllProfiles() {
        return profileRepository.findAll();
    }
    @Override
    public Profile findById(Long id) throws EntityNotFoundException {
        return profileRepository.findById(id).orElseThrow();
    }
    @Override
    public Profile findByUsername(String username) throws EntityNotFoundException{
        return profileRepository.findByUsername(username).orElseThrow();
    }
    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }
    @Override
    public Profile update(Long id, Profile profileUpdating) throws EntityNotFoundException {
        if(profileRepository.existsById(id)) {
            profileUpdating.setProfileID(id);
            return profileRepository.save(profileUpdating);
        }
        throw new EntityNotFoundException("Perfil ID #%d não encontrado!".formatted(id));
    }
    
    @Override
    @Transactional
    public void deleteById(Long id) throws EntityNotFoundException {
        if(!profileRepository.existsById(id)) {
            throw new EntityNotFoundException("Perfil ID #%d não encontrado!".formatted(id));
        } profileRepository.deleteById(id);
    }
}
