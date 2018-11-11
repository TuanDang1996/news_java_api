package JavaAPI.DAO.Interface;

import JavaAPI.Model.User;

public interface UserDAO {
    User addNewUser(User user);
    User findUserByUsername(String username);
    User findUserById(Long userId);
    int updateUserInformation(User user);
    String checkBookmark(long userId) throws Exception;
}
