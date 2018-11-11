package JavaAPI.Services.Impl;

import JavaAPI.Framework.NamedParameterStatement;
import JavaAPI.Services.Interface.ExecuteServiceInterface;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExecuteServiceImpl implements ExecuteServiceInterface {

    final static Logger logger = Logger.getLogger(ExecuteServiceInterface.class);

    @Override
    public List<Long> convertStringToLongArray(String array) {
        logger.info("Convert String ti array: " + array);
        JSONArray jsonArray = (JSONArray) new JSONObject(new JSONTokener("{data:"+array+"}")).get("data");

        List<Long> result = new LinkedList<>();

        for(int i=0; i<jsonArray.length(); i++) {
            result.add(jsonArray.getLong(i));
        }
        return result;
    }

    @Override
    public String convertListToString(List<Long> array) {
        logger.info("Convert array to string: " + array.toString());
        StringBuilder result = new StringBuilder();
        int length = array.size();

        result.append("[");

        for(int i = 0; i < length; i++){
            result.append(array.get(i).toString());
            if(i < length - 1)
                result.append(", ");
        }

        result.append("]");
        return result.toString();
    }
}
