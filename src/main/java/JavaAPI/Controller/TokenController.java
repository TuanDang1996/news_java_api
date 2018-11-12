package JavaAPI.Controller;
import JavaAPI.DTO.UserDTO;
import JavaAPI.Model.EmptyResponse;
import JavaAPI.Model.User;
import JavaAPI.Services.Interface.TokenServices;
import JavaAPI.Services.Interface.UserServices;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("token")
public class TokenController {

    final static Logger logger = Logger.getLogger(TokenController.class);

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private UserServices userServices;

    /**
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public final ResponseEntity<Object> checkLogin(@RequestBody UserDTO params){
        logger.info("Check login by token");
        User result;
        try{
            boolean checkToken = tokenServices.checkExistToken(params.getToken(), params.getType());
            if(checkToken){
                result = tokenServices.getUserByToken(params.getToken());
                /**
                 * check type token
                 */
                switch(params.getType()){
                    case 1:{
                        User user = new User();
                        user.setId(result.getId());
                        user.setUsername(params.getUsername());
                        user.setPicture(params.getPicture());
                        user.setEmail(params.getEmail());
                        userServices.updateUserInformation(user);
                    } break;
                }
            } else {
                result = userServices.addNewUser(params);
            }
            logger.info("User info: " + userServices.toString());
            return new ResponseEntity<Object>(result, HttpStatus.CREATED);
        } catch (Exception ex){
            return new ResponseEntity<Object>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
