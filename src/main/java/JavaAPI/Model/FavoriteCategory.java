package JavaAPI.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="category", schema="newsdb")
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FavoriteCategory {
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

    public FavoriteCategory(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.Code = category.getCode();
        this.icon = category.getIcon();
        this.iconHeight = category.getIconHeight();
        this.iconWidth = category.getIconWidth();
    }

    public FavoriteCategory(){}
}
