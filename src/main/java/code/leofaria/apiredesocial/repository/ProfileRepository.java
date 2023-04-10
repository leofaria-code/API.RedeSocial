package code.leofaria.apiredesocial.repository;

import code.leofaria.apiredesocial.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
//public interface ProfileRepository extends CrudRepository<Profile, Long> {
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
    
    Optional<Profile> findByUsername(String username);
}
