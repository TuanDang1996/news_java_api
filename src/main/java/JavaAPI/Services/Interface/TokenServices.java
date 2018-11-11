package JavaAPI.Services.Interface;

import JavaAPI.Model.User;

public interface TokenServices {
    User getUserByToken(String token);
    boolean checkExistToken(String token, long typeId);
}
