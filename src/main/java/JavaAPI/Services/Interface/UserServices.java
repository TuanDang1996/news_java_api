package JavaAPI.Services.Interface;

import JavaAPI.DTO.ArticleDTO;
import JavaAPI.DTO.UserDTO;
import JavaAPI.Model.FavoriteCategory;
import JavaAPI.Model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserServices {
    User addNewUser(UserDTO userDTO);
    User getUserById(Long userId);
    User getUserByToken(String token);
    void updateUserInformation(User user);
    void updateFavoriteCategory(List<Long> categorieIds, Long userId);
    void likeAndDislikeArticle(Long userID, Long articleId, String type);
    void updatehistory(Long articleId, Long userId);
    void updateBookmarkArtilces(long userId, long articleId) throws Exception;
    String getBookmarkString(long userId) throws Exception;
}
