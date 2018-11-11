package JavaAPI.DTO;

import JavaAPI.Model.Category;

public class CategoryDTO {
    private Long id;
    private String code;
    private String name;
    private String icon;
    private long iconWidth;
    private long iconHeight;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryDTO(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.code = category.getCode();
        this.name = category.getName();
        this.icon = category.getIcon();
        this.iconWidth = category.getIconWidth();
        this.iconHeight = category.getIconHeight();
    }
}
