package JavaAPI.DAO;

import JavaAPI.DTO.ArticleDTO;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractDao <PK extends Serializable, T> {
    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractDao(){
        this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession(){
        try {
             return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }

    @SuppressWarnings("unchecked")
    public T getByKey(PK key) {
        T result =  (T) getSession().get(persistentClass, key);
        return result;
    }

    public T persist(T entity) {
        Session session = getSession();
        session.persist(entity);
        return entity;
    }

    public void update(T entity) {
        Session session = getSession();
        session.saveOrUpdate(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    protected Criteria createEntityCriteria(){
        return getSession().createCriteria(persistentClass);
    }
}
