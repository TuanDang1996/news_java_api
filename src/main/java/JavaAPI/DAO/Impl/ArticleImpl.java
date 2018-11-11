package JavaAPI.DAO.Impl;

import JavaAPI.DAO.AbstractDao;
import JavaAPI.DAO.Interface.ArticleDAO;
import JavaAPI.DAO.Interface.CategoryDAO;
import JavaAPI.DTO.ArticleDTO;
import JavaAPI.Framework.NamedParameterStatement;
import JavaAPI.Model.Article;
import JavaAPI.Model.Category;
import JavaAPI.Services.Impl.ExecuteServiceImpl;
import JavaAPI.Services.Interface.ExecuteServiceInterface;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.HTMLDocument;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.*;

@Repository
public class ArticleImpl extends AbstractDao<Long, Article> implements ArticleDAO {

    final static Logger logger = Logger.getLogger(ArticleDAO.class);

    @Autowired
    ExecuteServiceImpl executeService;

    @Override
    public Article findArticleById(Long id){
        return getByKey(id);
    }

    @Override
    public List<ArticleDTO> findListArticleByCategory(Long categoryId, Long page, Long limit) throws Exception {
        logger.info("Creating criteria and generate query string...");
        Criteria criteria = createEntityCriteria();
        Long headIndex = limit * ( page -1 );
        criteria.add(Restrictions.eq("categoryId", categoryId));
        criteria.addOrder(Order.desc("id"));
        criteria.setFirstResult(headIndex.intValue());
        criteria.setMaxResults(limit.intValue());
        List<Article> list = (List<Article>) criteria.list();
        //pagination
        List<ArticleDTO> result = new LinkedList<>();
        int i = 0;
        logger.info("Seting like and dislike for among article...");
        for(i = 0; i < list.size(); i++){
            Article article = list.get(i);

            ArticleDTO articleDTO = new ArticleDTO(article);
            articleDTO.setCountDislike(countSameStatus("dislike", article.getId()));
            articleDTO.setCountLike(countSameStatus("like", article.getId()));

            result.add(articleDTO);
        }
        return result;
    }

    @Override
    public List<ArticleDTO> findNewestArticles(Long page, Long limit) {
        logger.info("Creating criteria and generate query string...");
        Criteria criteria = createEntityCriteria();
        criteria.addOrder(Order.desc("id"));
        List<Article> list = (List<Article>) criteria.list();
        //pagination
        List<ArticleDTO> result = new LinkedList<>();
        Long headIndex = limit * ( page -1 );
        Long tailIndex = headIndex + limit - 1;
        Long currentIndex = headIndex, length = Long.parseLong(String.valueOf(list.size()));
        logger.info("Making result form query string...");
        while(currentIndex <= tailIndex && currentIndex < length){
            result.add(new ArticleDTO(list.get(Integer.parseInt(currentIndex.toString()))));
            currentIndex++;
        }
        return result;
    }

    @Override
    public List<ArticleDTO> findTopNewsByCategory(Long categoryId, Long limit) {
        logger.info("Creating criteria and generate query string...");
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("categoryId", categoryId));
        criteria.addOrder(Order.desc("id"));
        criteria.setMaxResults(Integer.parseInt(limit.toString()));
        List<Article> list = (List<Article>) criteria.list();
        List<ArticleDTO> result = new LinkedList<>();
        logger.info("Converting result to list ArticleDTO...");
        //pagination
        for(Article article : list){
            result.add(new ArticleDTO(article));
        }
        return result;
    }

    @Override
    public Set<ArticleDTO> findTopViewDuringWeek(Long limit) {
        logger.info("Creating criteria 1  and generate query string...");
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.between("date", lastWeek(), new Date()));
        criteria.addOrder(Order.desc("view"));
        criteria.setMaxResults(Integer.parseInt(limit.toString()));
        List<Article> list = (List<Article>) criteria.list();
        Set<ArticleDTO> result = new HashSet<ArticleDTO>();

        logger.info("Creating criteria 2 and generate query string...");
        Criteria criteria2 = createEntityCriteria();
        criteria2.addOrder(Order.desc("view"));
        criteria2.setMaxResults(Integer.parseInt(limit.toString()));
        List<Article> list2 = (List<Article>) criteria2.list();
        list.addAll(list2);
        logger.info("Merging 2 results...");
        //pagination
        for(Article article : list){
            if (result.size() < limit) {
                result.add(new ArticleDTO(article));
            } else {
                break;
            }
        }
        return result;
    }

    @Override
    public int updateView(Long articleId) {
        logger.info("Updating article view...");
        Article article = getByKey(articleId);
        article.setView(article.getView() + 1);
        update(article);
        return 1;
    }

    @Override
    public long countSameStatus(String status, long articleId) throws Exception {
        logger.info("Make query string...");
        StringBuilder queryString = new StringBuilder();
        queryString.append("select count(u.id) ");
        queryString.append("from User u ");
        if(status.equalsIgnoreCase("like")) {
            queryString.append("where u.likedArticles like '%" + String.valueOf(articleId) + "%'");
        } else {
            queryString.append("where u.dislikedArticles like '%" + String.valueOf(articleId) + "%'");
        }

        Query query = getSession().createQuery(queryString.toString());

        logger.info("Perform query string: " + queryString.toString());
        Long result = (Long) query.uniqueResult();
        return result;
    }

    @Override
    public List<ArticleDTO> findArticleByString(String keyword, int page, int limit) throws Exception {
        logger.info("Make query string...");
        Session session = getSession();
        StringBuilder searchQuery = new StringBuilder();
        int fistResult = page * limit, i = 0;


        searchQuery.append("SELECT id, title, sapo, author, view FROM article ");
        searchQuery.append("WHERE MATCH (title,sapo,content) AGAINST (:keyword IN NATURAL LANGUAGE MODE)");
        Query query = session.createSQLQuery(searchQuery.toString());
        query.setParameter("keyword", keyword);
        query.setFirstResult(fistResult);
        query.setMaxResults(limit);

        logger.info("Perform query string: " + searchQuery.toString());
        List articles = query.list();
        List<ArticleDTO> result =  new ArrayList<ArticleDTO>();
        logger.info("Converting result to list ArticleDTO: " + result.size());
        for(i = 0; i < articles.size(); i++){
            Object[] fields = (Object[])articles.get(i);
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(Long.parseLong(((BigInteger)fields[0]).toString()));
            articleDTO.setTitle((String)fields[1]);
            articleDTO.setSapo((String)fields[2]);
            articleDTO.setAuthor((String)fields[3]);
            articleDTO.setView(Long.parseLong(((BigInteger)fields[4]).toString()));
            articleDTO.setCountLike(countSameStatus("like", articleDTO.getId()));
            articleDTO.setCountDislike(countSameStatus("dislike", articleDTO.getId()));
            result.add(articleDTO);
        }
        return result;
    }

    private Date lastWeek() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }
}
