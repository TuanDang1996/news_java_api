package JavaAPI.Services.Impl;

import JavaAPI.DAO.Interface.TokenDAO;
import JavaAPI.DAO.Interface.UserDAO;
import JavaAPI.Model.Token;
import JavaAPI.Model.User;
import JavaAPI.Services.Interface.TokenServices;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenServiceImpl implements TokenServices {

    final static Logger logger = Logger.getLogger(TokenServices.class);

    @Autowired
    TokenDAO tokenDAO;

    @Autowired
    UserDAO userDAO;

    @Override
    @Transactional
    public User getUserByToken(String token) {
        logger.info("Get user by token: " + token);
        Token tokenObj = tokenDAO.findByToken(token);
        User result = userDAO.findUserById(tokenObj.getUserId());
        return result;
    }

    @Override
    @Transactional
    public boolean checkExistToken(String token, long typeId) {
        logger.info("Check exsit token: " + token + ", type: " + typeId);
        Token tokenObj = tokenDAO.findByToken(token);
        if(tokenObj != null && tokenObj.getType() == typeId){
            return  true;
        }
        return false;
    }
}
