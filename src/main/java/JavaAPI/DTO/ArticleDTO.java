package JavaAPI.DTO;

import JavaAPI.DAO.Interface.CategoryDAO;
import JavaAPI.Model.Article;
import JavaAPI.Model.Category;
import JavaAPI.Services.Interface.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;

public class ArticleDTO {
    private Long id;
    private String title;
    private String sapo;
    private String picture;
    private Date date;
    private Long view;
    private String author;
    private String status;
    private Long countLike;
    private Long countDislike;
    private boolean checkBookmark;

    public boolean isCheckBookmark() {
        return checkBookmark;
    }

    public void setCheckBookmark(boolean checkBookmark) {
        this.checkBookmark = checkBookmark;
    }

    public Long getCountLike() {
        return countLike;
    }

    public void setCountLike(Long countLike) {
        this.countLike = countLike;
    }

    public Long getCountDislike() {
        return countDislike;
    }

    public void setCountDislike(Long countDislike) {
        this.countDislike = countDislike;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSapo() {
        return sapo;
    }

    public void setSapo(String sapo) {
        this.sapo = sapo;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArticleDTO(Long id, String title, String sapo, String picture, Date date, Long view, String author, String status) {
        this.id = id;
        this.title = title;
        this.sapo = sapo;
        this.picture = picture;
        this.date = date;
        this.view = view;
        this.author = author;
        this.status = status;
    }

    public ArticleDTO(Article article, String status){
        this.id = article.getId();
        this.title = article.getTitle();
        this.sapo = article.getSapo();
        this.picture = article.getPicture();
        this.date = article.getDate();
        this.view = article.getView();
        this.author = article.getAuthor();
        this.status = status;
    }

    public ArticleDTO(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.sapo = article.getSapo();
        this.picture = article.getPicture();
        this.date = article.getDate();
        this.view = article.getView();
        this.author = article.getAuthor();
        this.status = "None";
        this.countLike = Long.parseLong("0");
        this.countDislike = Long.parseLong("0");
    }

    @Override
    public boolean equals(Object art){
        if(art.getClass() == ArticleDTO.class && ((ArticleDTO)art).getId() == this.id) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.title, this.sapo, this.author);
    }

    public ArticleDTO(){}
}
