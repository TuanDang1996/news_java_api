package JavaAPI.DAO.Impl;

import JavaAPI.DAO.AbstractDao;
import JavaAPI.DTO.CategoryDTO;
import JavaAPI.Model.Article;
import JavaAPI.Services.Interface.ExecuteServiceInterface;
import JavaAPI.DAO.Interface.CategoryDAO;
import JavaAPI.Model.Category;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;
import java.util.List;

@Repository
public class CategoryImpl extends AbstractDao<Long, Category> implements CategoryDAO {

    final static Logger logger = Logger.getLogger(CategoryDAO.class);

    @Override
    @SuppressWarnings("unchecked")
    public List<CategoryDTO> findAll(){
        logger.info("Making query string...");
        Criteria criteria = createEntityCriteria();
        List<Category> list = (List<Category>)criteria.list();

        logger.info("Get result and convert to list CategoryDTO...");
        List<CategoryDTO> result = new LinkedList<>();
        for(Category cate : list){
            result.add(new CategoryDTO(cate));
        }
        return result;
    }

    @Override
    public Category findById(Long id) {
        logger.info("Find category by id: " + id);
        return getByKey(id);
    }


}
