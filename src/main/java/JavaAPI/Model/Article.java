package JavaAPI.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "article", schema = "jfzv4qj56bw65xyy")
public class Article {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "sapo")
    private String sapo;

    @Column(name = "content")
    private String content;

    @Column(name = "author")
    private String author;

    @Column(name = "picture")
    private String picture;

    @Column(name = "categoryId")
    @JsonIgnore
    private Long categoryId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @Column(name = "view")
    private Long view;

    @Column(name = "link")
    private String link;

    @Column(name = "megazine")
    private String meagazine;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleId", referencedColumnName = "id")
    private List<Pictures> pictures;

    public List<Pictures> getPictures() {
        return pictures;
    }

    public void setPictures(List<Pictures> pictures) {
        this.pictures = pictures;
    }

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMeagazine() {
        return meagazine;
    }

    public void setMeagazine(String meagazine) {
        this.meagazine = meagazine;
    }

    public Article() {
;    }
}
