package JavaAPI.Controller;

import JavaAPI.DTO.CategoryDTO;
import JavaAPI.Model.Category;
import JavaAPI.Model.EmptyResponse;
import JavaAPI.Services.Interface.CategoryServices;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryContrller {

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
