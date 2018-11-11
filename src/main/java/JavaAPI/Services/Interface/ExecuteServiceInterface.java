package JavaAPI.Services.Interface;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface ExecuteServiceInterface {
    List<Long> convertStringToLongArray(String array);
    String convertListToString(List<Long> array);
}
