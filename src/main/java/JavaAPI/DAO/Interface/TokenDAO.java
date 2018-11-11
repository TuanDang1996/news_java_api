package JavaAPI.DAO.Interface;

import JavaAPI.Model.Token;

public interface TokenDAO {
    public Token addNewToken(Token token);
    public Token findByToken(String token);
}
