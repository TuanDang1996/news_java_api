package JavaAPI.Controller;

import JavaAPI.DTO.FavoriteCategoriesDTO;
import JavaAPI.DTO.UserDTO;
import JavaAPI.Model.EmptyResponse;
import JavaAPI.Model.User;
import JavaAPI.Services.Interface.ArticleServices;
import JavaAPI.Services.Interface.UserServices;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    final static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    UserServices userServices;

    @Autowired
    ArticleServices articleServices;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getUserInformationById(@PathVariable Long userId){
        try{
            logger.info("Get user information by id: " + userId);
            User userDTO = userServices.getUserById(userId);
            logger.info("User information: " + userDTO.toString());
            return new ResponseEntity<Object>(userDTO, HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<Object>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<EmptyResponse> updateUser(@RequestBody User user){
        try{
            logger.info("Update user information");
            userServices.updateUserInformation(user);
            logger.info("Update info user " + user.getEmail() + " success");
            return new ResponseEntity<EmptyResponse>(new EmptyResponse("Update user success!"), HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<EmptyResponse>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{userId}",method = RequestMethod.PUT, consumes =  "application/json")
    @ResponseBody
    public ResponseEntity<EmptyResponse> updateFavoriteCategory(@PathVariable Long userId, @RequestBody FavoriteCategoriesDTO favoriteCategoriesDTO){
        try{
            logger.info("Update favorite category by userId: " + userId + " ,favorite category: " + favoriteCategoriesDTO.getCategorieIds().toString());
            List<Long> categoryIds = favoriteCategoriesDTO.getCategorieIds();
            userServices.updateFavoriteCategory(categoryIds, userId);
            logger.info("Update favorite category success");
            return new ResponseEntity<EmptyResponse>(new EmptyResponse("Update favorite category success!"), HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<EmptyResponse>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/listCategory/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listLikedCategory(@PathVariable Long userId){
        try{
            logger.info("List liked category by  userId: " + userId);
            User user = userServices.getUserById(userId);
            Hibernate.initialize(user.getCategories());
            logger.info("Get liked categorys success: " + user.getCategories().size());
            return new ResponseEntity<Object>(user.getCategories(), HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<Object>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{type}/{userId}/{articleId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<EmptyResponse> likeParticalArticle(@PathVariable("userId") Long userId,
                                                             @PathVariable("articleId") Long articleId,
                                                             @PathVariable("type") String type){
        try{
            logger.info("Hit like particular article: " + articleId + ", userId: " + userId + ", type: " + type);
            userServices.likeAndDislikeArticle(userId, articleId, type);
            return new ResponseEntity<EmptyResponse>(new EmptyResponse("success"), HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<EmptyResponse>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/history/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getHistoryByUserId(@PathVariable Long userId){
        try{
            logger.info("Get history by userId: " + userId);
            return new ResponseEntity<Object>(articleServices.listHistoriesByUser(userId), HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<Object>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
