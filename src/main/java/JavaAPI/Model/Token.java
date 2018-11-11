package JavaAPI.Model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;

@Entity
@Table(name = "token", schema = "jfzv4qj56bw65xyy")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Token {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "type")
    private int type;

    @Column(name = "userId")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
