package JavaAPI.DTO;

import JavaAPI.Model.Article;
import JavaAPI.Model.Category;
import JavaAPI.Model.Pictures;

import java.util.Date;
import java.util.List;

public class ArticleViewDTO {
    private Long id;
    private String title;
    private String sapo;
    private String content;
    private String author;
    private Date date;
    private String status;
    private CategoryDTO category;
    private String link;
    private String megazine;
    private List<Pictures> pictures;
    private boolean isBookmarked;

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public List<Pictures> getPictures() {
        return pictures;
    }

    public void setPictures(List<Pictures> pictures) {
        this.pictures = pictures;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMegazine() {
        return megazine;
    }

    public void setMegazine(String megazine) {
        this.megazine = megazine;
    }

    public ArticleViewDTO(Long id, String title, String sapo, String content, String author, Date date, String status,
                          String link, String meagazine, List<Pictures> pictures) {
        this.id = id;
        this.title = title;
        this.sapo = sapo;
        this.content = content;
        this.author = author;
        this.date = date;
        this.status = status;
        this.link = link;
        this.megazine = meagazine;
        this.pictures = pictures;
    }

    public ArticleViewDTO(Article article, String status, Category category){
        this.id = article.getId();
        this.title = article.getTitle();
        this.sapo = article.getSapo();
        this.content = article.getContent();
        this.author = article.getAuthor();
        this.date = article.getDate();
        this.status = status;
        this.category = new CategoryDTO(category);
        this.link = article.getLink();
        this.megazine = article.getMeagazine();
        this.pictures = article.getPictures();
    }
}
