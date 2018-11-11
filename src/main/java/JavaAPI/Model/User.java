package JavaAPI.Model;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user", schema = "jfzv4qj56bw65xyy")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "picture")
    private String picture;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "favorite_category", catalog = "jfzv4qj56bw65xyy", joinColumns = {
            @JoinColumn(name = "accountId", referencedColumnName = "id")
    }, inverseJoinColumns = {@JoinColumn(name = "categoryId", referencedColumnName = "id")})
    private List<FavoriteCategory> categories;

    @Column(name = "likedArticles")
    private String likedArticles;

    @Column(name = "unlkedArticles")
    private String dislikedArticles;

    @Column(name = "bookmarkedArtilces")
    private String bookmarkedArtilces;

    @Column(name = "history")
    private String history;

    public String getBookmarkedArtilces() {
        return bookmarkedArtilces;
    }

    public void setBookmarkedArtilces(String bookmarkedArtilces) {
        this.bookmarkedArtilces = bookmarkedArtilces;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getLikedArticles() {
        return likedArticles;
    }

    public void setLikedArticles(String likedArticles) {
        this.likedArticles = likedArticles;
    }

    public String getDislikedArticles() {
        return dislikedArticles;
    }

    public void setDislikedArticles(String unlikedArticles) {
        this.dislikedArticles = unlikedArticles;
    }

    public List<FavoriteCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<FavoriteCategory> categories) {
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public User(){
        this.history = "[]";
        this.likedArticles = "[]";
        this.dislikedArticles = "[]";
    }

    @Override
    public String toString(){
        return "Username: " + this.username + "; Email: " + this.email;
    }
}
