package JavaAPI.Services.Interface;

import JavaAPI.DTO.ArticleDTO;
import JavaAPI.DTO.ArticleViewDTO;
import JavaAPI.Model.Article;

import java.util.List;
import java.util.Set;

public interface ArticleServices {
    ArticleViewDTO findById(Long id) throws Exception;
    Article findById(Long id, Long userId) throws Exception;
    List<ArticleDTO> findNewestArticle(Long page, Long limit);
    List<ArticleDTO> findTopNewsByCategory(Long categoryId, Long limit);
    Set<ArticleDTO> findTopViewDuringWeek(Long limit);
    void updateView(Long articleId);
    List<ArticleDTO> listHistoriesByUser(Long userId);
    Long countByStatus(String status, Long articleId) throws Exception;
    List<ArticleDTO> findArticle(String keyword, int page, int limit) throws Exception;
    List<ArticleDTO> getArticlesBookmarkedByUserId(long userId, long page, long limit);
}
