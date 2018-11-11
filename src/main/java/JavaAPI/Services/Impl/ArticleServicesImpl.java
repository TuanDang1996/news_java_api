package JavaAPI.Services.Impl;

import JavaAPI.DAO.Interface.ArticleDAO;
import JavaAPI.DAO.Interface.CategoryDAO;
import JavaAPI.DAO.Interface.UserDAO;
import JavaAPI.DTO.ArticleDTO;
import JavaAPI.DTO.ArticleViewDTO;
import JavaAPI.Model.Article;
import JavaAPI.Model.User;
import JavaAPI.Services.Interface.ArticleServices;
import JavaAPI.Services.Interface.ExecuteServiceInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class ArticleServicesImpl implements ArticleServices {

    final static Logger logger = Logger.getLogger(ArticleServices.class);

    @Autowired
    ArticleDAO articleDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    ExecuteServiceInterface executeService;

    @Autowired
    CategoryDAO categoryDAO;

    @Override
    @Transactional
    public ArticleViewDTO findById(Long id) throws Exception {
        logger.info("Find ArticleViewDTO by id: " + id);
        Article article = articleDAO.findArticleById(id);
        System.out.println(String.valueOf(article.getPictures().size()));
        ArticleViewDTO result = new ArticleViewDTO(article, "none", categoryDAO.findById(article.getCategoryId()));
        return result;
    }

    @Override
    @Transactional
    public Article findById(Long id, Long userId) throws Exception {
        logger.info("Find article by id: " + id);
        Article article = articleDAO.findArticleById(id);
        return article;
    }

    @Override
    @Transactional
    public List<ArticleDTO> findNewestArticle(Long page, Long limit) {
        logger.info("Find newest article, page: " + page + ", limit: " + limit);
        return articleDAO.findNewestArticles(page, limit);
    }

    @Override
    @Transactional
    public List<ArticleDTO> findTopNewsByCategory(Long categoryId, Long limit) {
        logger.info("Find top news by category: " + categoryId + ", limit: " + limit);
        return articleDAO.findTopNewsByCategory(categoryId, limit);
    }

    @Override
    @Transactional
    public Set<ArticleDTO> findTopViewDuringWeek(Long limit) {
        logger.info("Find top view news during a week: " + limit);
        return articleDAO.findTopViewDuringWeek(limit);
    }

    @Override
    @Transactional
    public void updateView(Long articleId) {
        articleDAO.updateView(articleId);
    }

    @Override
    @Transactional
    public List<ArticleDTO> listHistoriesByUser(Long userId) {
        logger.info("List histories by user : " + userId);
        User user = userDAO.findUserById(userId);
        List<Long> histories = new LinkedList<>();
        List<ArticleDTO> result = new LinkedList<>();

        if(user.getHistory() != null){
            histories = executeService.convertStringToLongArray(user.getHistory());
        }
        logger.info("History articles size: " + histories.size());

        for(int i=0;i<histories.size(); i++){
            Article article = articleDAO.findArticleById(histories.get(i));
            result.add(new ArticleDTO(article));

        }
        
        return result;
    }

    @Override
    public Long countByStatus(String status, Long articleId) throws Exception {
        logger.info("Count article by status, articleId: " + articleId + ", status: " + status);
        return articleDAO.countSameStatus(status, articleId);
    }

    @Override
    @Transactional
    public List<ArticleDTO> findArticle(String keyword, int page, int limit) throws Exception{
        logger.info("Find article by keyqrod, keyword: " + keyword + ", page: " + page + ", limit: " + limit);
        return articleDAO.findArticleByString(keyword, page, limit);
    }

    @Override
    @Transactional
    public List<ArticleDTO> getArticlesBookmarkedByUserId(long userId, long page, long limit) {
        logger.info("Get user by userId: " + userId);
        User user = userDAO.findUserById(userId);
        List<Long> boolmark = new ArrayList<>();
        //read data
        if(user.getBookmarkedArtilces() != null && !user.getBookmarkedArtilces().equals("")){
            logger.info("Get bookmark article...");
            boolmark = executeService.convertStringToLongArray(user.getBookmarkedArtilces() );
        }
        List<ArticleDTO> result = new ArrayList<>();
        long startIndex = (page - 1) * limit;
        long endIndex = (page * limit) < boolmark.size() ? page * limit : boolmark.size() - 1;
        for(long i = startIndex; i < endIndex; i++){
            ArticleDTO articleDTO = new ArticleDTO(articleDAO.findArticleById(boolmark.get(Integer.parseInt(String.valueOf(i)))));
            articleDTO.setCheckBookmark(true);
            result.add(articleDTO);
        }
        return result;
    }
}
