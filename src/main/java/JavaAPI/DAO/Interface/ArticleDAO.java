package JavaAPI.DAO.Interface;

import JavaAPI.DTO.ArticleDTO;
import JavaAPI.Model.Article;

import java.util.List;
import java.util.Set;

public interface ArticleDAO {
    Article findArticleById(Long id);
    List<ArticleDTO> findListArticleByCategory(Long categoryId, Long page, Long limit) throws Exception;
    List<ArticleDTO> findNewestArticles(Long page, Long limit);
    List<ArticleDTO> findTopNewsByCategory(Long categoryId, Long limit);
    Set<ArticleDTO> findTopViewDuringWeek(Long limit);
    int updateView(Long articleId);
    long countSameStatus(String status, long articleId) throws Exception;
    List<ArticleDTO> findArticleByString(String keyword, int page, int limit) throws Exception;
}
