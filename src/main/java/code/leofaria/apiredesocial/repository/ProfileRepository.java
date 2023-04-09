package code.leofaria.apiredesocial.repository;

import code.leofaria.apiredesocial.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
