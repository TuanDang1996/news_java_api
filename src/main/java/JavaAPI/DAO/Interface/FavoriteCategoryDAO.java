package JavaAPI.DAO.Interface;


import JavaAPI.Model.FavoriteCategory;

public interface FavoriteCategoryDAO {
    void deleteAllFavoriteCategory(Long userId);
    void addNewFavoriteCategory(FavoriteCategory favoriteCategory, Long userId);
}
