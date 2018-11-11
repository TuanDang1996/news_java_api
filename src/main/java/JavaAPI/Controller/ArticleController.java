package JavaAPI.Controller;

import JavaAPI.DTO.ArticleDTO;
import JavaAPI.DTO.ArticleViewDTO;
import JavaAPI.Model.Article;
import JavaAPI.Model.EmptyResponse;
import JavaAPI.Model.User;
import JavaAPI.Services.Interface.ArticleServices;
import JavaAPI.Services.Interface.CategoryServices;
import JavaAPI.Services.Interface.ExecuteServiceInterface;
import JavaAPI.Services.Interface.UserServices;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("article")
public class ArticleController {

    final static Logger logger = Logger.getLogger(ArticleController.class);

    @Autowired
    ArticleServices articleServices;

    @Autowired
    CategoryServices categoryServices;

    @Autowired
    UserServices userServices;

    @Autowired
    ExecuteServiceInterface executeService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getArticleById(@PathVariable("id") Long id,
                                                 HttpServletRequest request){
        try{
            logger.info("Get Article By Id: " + id);
            ArticleViewDTO article = articleServices.findById(id);
            return new ResponseEntity<Object>(checkUser(article, request), HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<Object>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{userId}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getArticleByIdAndUser(@PathVariable("id") Long id,
                                                        @PathVariable("userId") Long userId,
                                                        HttpServletRequest request){
        try{
            logger.info("Get article by id: " + id + " and user: " + userId);
            userServices.updatehistory(id, userId);
            ArticleViewDTO result = articleServices.findById(id);

            //set status of article : like, dislike or none
            User user = userServices.getUserById(userId);
            List<Long> dislikedArticles = new ArrayList<>(), likedArticles = new ArrayList<>();
            //read data
            if(user.getDislikedArticles() != null){
                dislikedArticles = executeService.convertStringToLongArray(user.getDislikedArticles());
            }
            if(user.getLikedArticles() != null){
                likedArticles = executeService.convertStringToLongArray(user.getLikedArticles());
            }

            if(likedArticles.contains(id)){
                result.setStatus("liked");
            } else if(dislikedArticles.contains(id)){
                result.setStatus("disliked");
            }

            return new ResponseEntity<Object>(checkUser(result, request), HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<Object>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getListArticleByCategory(@PathVariable("categoryId") Long categoryId, HttpServletRequest request){
        try{
            logger.info("Get list articles by categoryId: " + categoryId);
            Long page = Long.parseLong(request.getParameter("page"));
            Long limit  = Long.parseLong(request.getParameter("limit"));
            logger.info("Page: " + page + ", limit: " + limit);

            return new ResponseEntity<Object>(checkUser(categoryServices.findListArticleByCategory(categoryId, page, limit), request), HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<Object>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/newest", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getNewest(HttpServletRequest request){
        try {
            logger.info("Get newest articles");
            Long page = Long.parseLong(request.getParameter("page"));
            Long limit = Long.parseLong(request.getParameter("limit"));
            logger.info("Page: " + page + ", limit: " + limit);
            return new ResponseEntity<Object>(checkUser(articleServices.findNewestArticle(page, limit), request), HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<Object>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/category/{categoryId}/top", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getTopNewsByCategory(@PathVariable("categoryId") Long categoryId, HttpServletRequest request){
        try{
            logger.info("Get top news by categoryId: " + categoryId);
            Long limit = Long.parseLong(request.getParameter("limit"));
            return new ResponseEntity<Object>(checkUser(articleServices.findTopNewsByCategory(categoryId, limit), request), HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<Object>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/topView", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getTopViewNew(@RequestParam("limit") Long limit){
        try{
            logger.info("Get top view have limit: " + limit);
            return new ResponseEntity<Object>(articleServices.findTopViewDuringWeek(limit), HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<Object>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/updateView/{articleId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<EmptyResponse> updateView(@PathVariable("articleId") Long articleId){
        try{
            logger.info("Update view of article: " + articleId);
            articleServices.updateView(articleId);
            return new ResponseEntity<EmptyResponse>(new EmptyResponse("Update view get success!"), HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<EmptyResponse>(new EmptyResponse("Update view get error! Try it in another time!"), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> search(@RequestParam("keyword") String keyword,
                                         @RequestParam("page") int page,
                                         @RequestParam("limit") int limit,
                                         HttpServletRequest request){
        try{
            logger.info("Search articles has keyword: " + keyword);
            logger.info("Page: " + page + ", limit: " + limit);
            List<ArticleDTO> list = articleServices.findArticle(keyword, page, limit);
            return new ResponseEntity<Object>(checkUser(list, request), HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<Object>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/bookmark/{userId}/{articleId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<EmptyResponse> bookmarkArticle(@PathVariable("articleId") Long articleId,
                                                  @PathVariable("userId") Long userId){
        try{
            logger.info("Bookmark article: " + articleId + " userId: " + userId);
            userServices.updateBookmarkArtilces(userId, articleId);
            return new ResponseEntity<EmptyResponse>(new EmptyResponse("success"), HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<EmptyResponse>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/bookmark/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getBookmarkArticles(@PathVariable("userId") Long userId,
                                                      @RequestParam("page") long page,
                                                      @RequestParam("limit") long limit){
        try{
            logger.info("Get bookmark articles by userId: " + userId);
            logger.info("page: " + page + ", limit: " + limit);
            List<ArticleDTO> result = articleServices.getArticlesBookmarkedByUserId(userId, page, limit);
            logger.info("Result size: " + result.size());
            return new ResponseEntity<Object>(result, HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<Object>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    private <T> T checkUser(T article, HttpServletRequest request){
        try{
            Long userId = Long.parseLong(request.getParameter("userId"));
            String bookmarkString = userServices.getBookmarkString(userId);

            if(article.getClass() == LinkedList.class || article.getClass() == ArrayList.class){
                for(ArticleDTO at : (List<ArticleDTO>)article){
                    if(bookmarkString.contains(String.valueOf(at.getId()))) {
                        at.setCheckBookmark(true);
                    } else {
                        at.setCheckBookmark(false);
                    }
                }
            } else if(article.getClass() == ArticleDTO.class){
                Long id  = ((ArticleDTO)article).getId();
                if(bookmarkString.contains(String.valueOf(id))) {
                    ((ArticleDTO)article).setCheckBookmark(true);
                } else {
                    ((ArticleDTO) article).setCheckBookmark(false);
                }
            } else {
                Long id  = ((ArticleViewDTO)article).getId();
                if(bookmarkString.contains(String.valueOf(id))) {
                    ((ArticleViewDTO)article).setBookmarked(true);
                } else {
                    ((ArticleViewDTO) article).setBookmarked(false);
                }
            }
            return article;
        } catch (Exception ex){
            return article;
        }
    }
}
