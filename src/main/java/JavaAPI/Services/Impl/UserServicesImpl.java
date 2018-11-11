package JavaAPI.Services.Impl;

import JavaAPI.DAO.Interface.*;
import JavaAPI.DTO.ArticleDTO;
import JavaAPI.DTO.UserDTO;
import JavaAPI.Model.Category;
import JavaAPI.Model.FavoriteCategory;
import JavaAPI.Model.Token;
import JavaAPI.Model.User;
import JavaAPI.Services.Interface.ExecuteServiceInterface;
import JavaAPI.Services.Interface.UserServices;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service("userServices")
@Transactional
public class UserServicesImpl implements UserServices {
    private final int maxSize = 10;

    final static Logger logger = Logger.getLogger(UserServices.class);

    @Autowired
    UserDAO userDAO;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    TokenDAO tokenDAO;

    @Autowired
    ExecuteServiceInterface executeService;

    @Autowired
    FavoriteCategoryDAO favoriteCategoryDAO;

    @Autowired
    private ArticleDAO articleDAO;

    @Override
    @Transactional
    public User addNewUser(UserDTO userDTO) {
        logger.info("Add new user: " + userDTO.getUsername() + ", token: " + userDTO.getToken());
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPicture(userDTO.getPicture());
        user.setUsername(userDTO.getUsername());
        user = userDAO.addNewUser(user);

        Token token = new Token();
        token.setToken(userDTO.getToken());
        token.setType(userDTO.getType());
        token.setUserId(user.getId());
        tokenDAO.addNewToken(token);

        return user;

    }



    @Override
    @Transactional
    public User getUserById(Long userId) {
        logger.info("Get user by id: " + userId);
        return userDAO.findUserById(userId);
    }

    @Override
    public User getUserByToken(String token) {
        logger.info("Get user by token: " + token);
        Token tokenElm = tokenDAO.findByToken(token);
        return userDAO.findUserById(tokenElm.getUserId());
    }

    @Override
    @Transactional
    public void updateUserInformation(User user) {
        logger.info("Update user information.....");
        User currentUser = userDAO.findUserById(user.getId());
        currentUser.setEmail(user.getEmail());
        currentUser.setPicture(user.getPicture());
        currentUser.setUsername(user.getUsername());
        userDAO.updateUserInformation(currentUser);
    }

    @Override
    @Transactional
    public void updateFavoriteCategory(List<Long> categorieIds, Long userId) {
        logger.info("Update favorite category, size: " + categorieIds.size() + ", userId:" + userId);
        favoriteCategoryDAO.deleteAllFavoriteCategory(userId);

        for(Long i : categorieIds){
            Category category = categoryDAO.findById(i);
            FavoriteCategory favoriteCategory = new FavoriteCategory(category);
            favoriteCategoryDAO.addNewFavoriteCategory(favoriteCategory, userId);
        }
    }

    @Override
    @Transactional
    public void likeAndDislikeArticle(Long userID, Long articleId, String type) {
        logger.info(type + " article has id: " + articleId + ", userId: " + userID);
        User user = userDAO.findUserById(userID);
        List<Long> dislikedArticles = new LinkedList<>(), likedArticles = new LinkedList<>();
        //read data
        if(user.getDislikedArticles() != null){
            dislikedArticles = executeService.convertStringToLongArray(user.getDislikedArticles());
        }
        if(user.getLikedArticles() != null){
            likedArticles = executeService.convertStringToLongArray(user.getLikedArticles());
        }
        switch(type) {
            case "like": {
                //check if disliked this article in past
                if (!likedArticles.contains(articleId)) {
                    if (dislikedArticles.contains(articleId)) {
                        dislikedArticles.remove(articleId);
                    }
                    //like this article
                    likedArticles.add(articleId);
                }
            } break;
            case "dislike":{
                if (!dislikedArticles.contains(articleId)) {
                    if (likedArticles.contains(articleId)) {
                        likedArticles.remove(articleId);
                    }
                    //like this article
                    dislikedArticles.add(articleId);
                }
            } break;
        }
        logger.info("Set like and dislike article....");
        user.setLikedArticles(executeService.convertListToString(likedArticles));
        user.setDislikedArticles(executeService.convertListToString(dislikedArticles));
        userDAO.updateUserInformation(user);
    }

    @Override
    @Transactional
    public void updatehistory(Long articleId, Long userId) {
        logger.info("Find user by userId: " + userId);
        User user = userDAO.findUserById(userId);
        List<Long> histories = new LinkedList<>();
        //convert data
        if(user.getHistory() != null){
            histories = executeService.convertStringToLongArray(user.getHistory());
        }

        if(!histories.contains(articleId)){
            logger.info("Performing update history....");
            if(histories.size() > maxSize){
                histories.remove(0);
            }
            histories.add(articleId);
        }
        user.setHistory(executeService.convertListToString(histories));
        userDAO.updateUserInformation(user);
    }

    @Override
    @Transactional
    public void updateBookmarkArtilces(long userId, long articleId) throws Exception {
        logger.info("Update bookmark article, userId: " + userId + ", articleId: " + articleId);
        User user = userDAO.findUserById(userId);
        List<Long> boolmark = new ArrayList<>();
        //read data
        if(user.getBookmarkedArtilces() != null && !user.getBookmarkedArtilces().equals("")){
            logger.info("Get bookmark article...");
            boolmark = executeService.convertStringToLongArray(user.getBookmarkedArtilces() );
        }

        //check if disliked this article in past
        logger.info("Performing bookmark article...");
        if (boolmark.contains(articleId)) {
            boolmark.remove(articleId);
        } else {
            boolmark.add(articleId);
        }
        user.setBookmarkedArtilces(executeService.convertListToString(boolmark));
        userDAO.updateUserInformation(user);
    }

    @Override
    @Transactional
    public String getBookmarkString(long userId) throws Exception {
        return userDAO.checkBookmark(userId);
    }
}
