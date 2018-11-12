package JavaAPI.Controller;
import JavaAPI.DTO.CategoryDTO;
import JavaAPI.DTO.UserDTO;
import JavaAPI.Model.EmptyResponse;
import JavaAPI.Model.User;
import JavaAPI.Services.Interface.CategoryServices;
import JavaAPI.Services.Interface.TokenServices;
import JavaAPI.Services.Interface.UserServices;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class test {

    final static Logger logger = Logger.getLogger(CategoryContrller.class);

    @Autowired
    CategoryServices categoryServices;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> HomeIndex() throws Exception{
        try {
            logger.info("Get All Category");
            List<CategoryDTO> result = categoryServices.findAll();
            logger.info("Quantity og category: " + result.size());
            return new ResponseEntity<Object>(result, HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<Object>(new EmptyResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
