package code.leofaria.apiredesocial.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Date timestamp;
    private String status;
    private String msg;
    private String detail;
}