package JavaAPI.Services.Interface;

import JavaAPI.DTO.ArticleDTO;
import JavaAPI.DTO.CategoryDTO;
import JavaAPI.Model.Article;
import JavaAPI.Model.Category;

import java.util.List;

public interface CategoryServices {
    List<CategoryDTO> findAll();
    List<ArticleDTO> findListArticleByCategory(Long categoryId, Long page, Long limmit) throws Exception;
    Category findById(Long categoryId);
}
