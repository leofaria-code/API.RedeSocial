package code.leofaria.apiredesocial.mapper;

import code.leofaria.apiredesocial.dto.PostSaveDTO;
import code.leofaria.apiredesocial.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post postSaveDTOtoPost(PostSaveDTO dto);
}
