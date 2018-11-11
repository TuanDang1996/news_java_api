package JavaAPI.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="category", schema="jfzv4qj56bw65xyy")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String Code;

    @Column(name = "icon")
    private String icon;

    @Column(name = "iconWidth")
    private long iconWidth;

    @Column(name = "iconHeight")
    private long iconHeight;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private List<Article> articles;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getIconWidth() {
        return iconWidth;
    }

    public void setIconWidth(long iconWidth) {
        this.iconWidth = iconWidth;
    }

    public long getIconHeight() {
        return iconHeight;
    }

    public void setIconHeight(long iconHeight) {
        this.iconHeight = iconHeight;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(){}
}
