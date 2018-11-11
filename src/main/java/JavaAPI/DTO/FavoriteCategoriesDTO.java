package JavaAPI.DTO;

import java.util.List;

public class FavoriteCategoriesDTO {
    private List<Long> categorieIds;

    public List<Long> getCategorieIds() {
        return categorieIds;
    }

    public void setCategorieIds(List<Long> categorieIds) {
        this.categorieIds = categorieIds;
    }
}
