package JavaAPI.DAO.Impl;

import JavaAPI.DAO.AbstractDao;
import JavaAPI.DAO.Interface.FavoriteCategoryDAO;
import JavaAPI.Model.FavoriteCategory;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.sql.Statement;

@Repository
public class FavoriteCategoryImpl extends AbstractDao<Long, FavoriteCategory> implements FavoriteCategoryDAO {

    final static Logger logger = Logger.getLogger(FavoriteCategoryDAO.class);

    @Override
    public final void deleteAllFavoriteCategory(Long userId) {
        logger.info("Making query to delete all favorite category...");
        Session session = getSession();
        StringBuilder quey = new StringBuilder();
        quey.append("delete from favorite_category where accountId = :accountId");
        Query query = session.createSQLQuery(quey.toString()).setParameter("accountId", userId);
        logger.info("Perform delete query: " + quey.toString());
        query.executeUpdate();
    }

    @Override
    public final void addNewFavoriteCategory(FavoriteCategory favoriteCategory, Long userId) {
        logger.info("Making query to add new favorite category...");
        Session session = getSession();
        StringBuilder quey = new StringBuilder();
        quey.append("insert into favorite_category(accountId, categoryId) ");
        quey.append("values(:accountId, :categoryId)");
        Query query = session.createSQLQuery(quey.toString());
        query.setParameter("accountId", userId);
        query.setParameter("categoryId", favoriteCategory.getId());
        logger.info("Performing query: " + quey.toString());
        query.executeUpdate();
    }
}
