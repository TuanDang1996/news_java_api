package JavaAPI.Model;

public class EmptyResponse {
    public String message;

    public EmptyResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
