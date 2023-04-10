package code.leofaria.apiredesocial.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfileSaveDTO {
    public final String MSG_notBlank= "Esse campo não pode ficar em branco!";
    public final String MSG_notNull = "Esse campo não pode ser nulo!";
    public final String MSG_notEmpty = "Esse campo não pode ser vazio!";
    public final String MSG_votValid = MSG_notEmpty
            + "\n" + MSG_notNull
            + "\n" + MSG_notBlank;
    
    @Valid
    @NotBlank(message = MSG_notBlank)
    @NotNull(message = MSG_notNull)
    @NotEmpty(message = MSG_notEmpty)
    private String username;
    
    @Valid
    @NotBlank(message = MSG_notBlank)
    @NotNull(message = MSG_notNull)
    @NotEmpty(message = MSG_notEmpty)
    private String password;
}
