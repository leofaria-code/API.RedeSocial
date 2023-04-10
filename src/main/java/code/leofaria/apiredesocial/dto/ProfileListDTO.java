package code.leofaria.apiredesocial.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProfileListDTO {
    
    private Long profileID;
    private String username;
    private String password;
}
