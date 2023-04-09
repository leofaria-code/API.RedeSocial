package code.leofaria.apiredesocial.mapper;

import code.leofaria.apiredesocial.dto.PostSaveDTO;
import code.leofaria.apiredesocial.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    
//    @Mapping(source="timestampPostCreated", target="timestampPostCreated", dateFormat = "LocalDateTime")
    Post postSaveDTOtoPost(PostSaveDTO dto);
}
