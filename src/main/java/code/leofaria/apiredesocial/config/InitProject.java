package code.leofaria.apiredesocial.config;

import code.leofaria.apiredesocial.entity.Profile;
import code.leofaria.apiredesocial.repository.ProfileRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Component
@Transactional
public class InitProject implements ApplicationRunner {
    
    private final ProfileRepository profileRepository;
    
    public InitProject(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Profile> admin = profileRepository.findByUsername("admin");
        if (admin.isEmpty()) {
            Profile profile = new Profile();
            profile.setUsername("admin");
            profile.setPassword("1234");
            profile.setDob(LocalDate.now());
            profileRepository.save(profile);
        }
    }
}
