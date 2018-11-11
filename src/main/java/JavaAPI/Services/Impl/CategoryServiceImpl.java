package JavaAPI.Services.Impl;

import JavaAPI.DAO.Interface.ArticleDAO;
import JavaAPI.DAO.Interface.CategoryDAO;
import JavaAPI.DTO.ArticleDTO;
import JavaAPI.DTO.CategoryDTO;
import JavaAPI.Model.Article;
import JavaAPI.Model.Category;
import JavaAPI.Services.Interface.CategoryServices;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryServices {

    final static Logger logger = Logger.getLogger(CategoryServices.class);

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    ArticleDAO articleDAO;

    @Override
    @Transactional
    public List<CategoryDTO> findAll() {
        logger.info("Find all category");
        return categoryDAO.findAll();
    }

    @Override
    @Transactional
    public List<ArticleDTO> findListArticleByCategory(Long categoryId, Long page, Long limit) throws Exception {
        logger.info("Find list article by categoryId: " + categoryId + ", page: " + page + ", limit: " + limit);
        return articleDAO.findListArticleByCategory(categoryId, page, limit);
    }

    @Override
    @Transactional
    public Category findById(Long categoryId) {
        logger.info("Find category by id: " + categoryId);
        return categoryDAO.findById(categoryId);
    }
}
