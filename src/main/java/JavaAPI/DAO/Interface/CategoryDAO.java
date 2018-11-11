package JavaAPI.DAO.Interface;

import JavaAPI.DAO.AbstractDao;
import JavaAPI.DTO.CategoryDTO;
import JavaAPI.Model.Article;
import JavaAPI.Model.Category;

import java.util.List;

public interface CategoryDAO{
    List<CategoryDTO> findAll();
    Category findById(Long id);
}
