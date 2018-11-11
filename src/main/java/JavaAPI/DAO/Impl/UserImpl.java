package JavaAPI.DAO.Impl;

import JavaAPI.DAO.AbstractDao;
import JavaAPI.DAO.Interface.UserDAO;
import JavaAPI.Model.User;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDAO")
public class UserImpl extends AbstractDao<Long, User> implements UserDAO{

    final static Logger logger = Logger.getLogger(UserDAO.class);

    @Override
    public User addNewUser(User user) {
        logger.info("Persist new user...");
        User result = persist(user);
        return result;
    }

    @Override
    public User findUserByUsername(String username) {
        logger.info("Making criteria to find user by username...");
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("username", username));
        logger.info("Execute criteria to find user");
        User user = (User) criteria.uniqueResult();
        return user;
    }

    @Override
    public User findUserById(Long userId) {
        logger.info("Get token by key: " + userId);
        return getByKey(userId);
    }

    @Override
    public int updateUserInformation(User user) {
        logger.info("Update user information from update function");
        update(user);
        return 0;
    }

    @Override
    public String checkBookmark(long userId) throws Exception{
        logger.info("Making query to check bookmark...");
        StringBuilder queryString = new StringBuilder();
        queryString.append("select u.bookmarkedArtilces ");
        queryString.append("from User u ");
        queryString.append("where u.id = :userId");

        Query query = getSession().createQuery(queryString.toString());
        query.setParameter("userId", userId);

        logger.info("Execute query to check bookmark: " + queryString.toString());
        String result = (String) query.uniqueResult();
        return result;
    }
}
