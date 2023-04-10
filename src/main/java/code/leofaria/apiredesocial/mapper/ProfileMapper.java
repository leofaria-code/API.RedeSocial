package code.leofaria.apiredesocial.mapper;

import code.leofaria.apiredesocial.dto.ProfileSaveDTO;
import code.leofaria.apiredesocial.dto.ProfileListDTO;
import code.leofaria.apiredesocial.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile profileSaveDTOtoProfile(ProfileSaveDTO dto);
    
    ProfileListDTO profileToProfileListDTO(Profile profile);
}