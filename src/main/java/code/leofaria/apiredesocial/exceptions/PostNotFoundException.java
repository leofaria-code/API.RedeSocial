package code.leofaria.apiredesocial.exceptions;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException() {
        super("Post não encontrado");
    }
}
