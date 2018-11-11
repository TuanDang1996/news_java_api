package JavaAPI.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Table(name = "pictures", schema = "jfzv4qj56bw65xyy")
@Entity
public class Pictures {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name =  "articleId")
    @JsonIgnore
    private Long articleId;

    @Column(name = "link")
    private String link;

    @Column(name = "description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Pictures(){}
}
