package JavaAPI.DAO.Impl;

import JavaAPI.DAO.AbstractDao;
import JavaAPI.DAO.Interface.TokenDAO;
import JavaAPI.Model.Token;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TokenImpl extends AbstractDao<Long, Token> implements TokenDAO{

    final static Logger logger = Logger.getLogger(TokenDAO.class);

    @Override
    public Token addNewToken(Token token) {
        logger.info("Persist new token: " + token);
        return persist(token);
    }

    @Override
    public Token findByToken(String token) {
        logger.info("Making query to find token by token...");
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("token", token));
        logger.info("Excecute query to find token...");
        Token tokenElm = (Token) criteria.uniqueResult();
        return tokenElm;
    }
}
