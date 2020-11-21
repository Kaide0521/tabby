package tabby.core.data;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tabby.config.GlobalConfiguration;
import tabby.util.FileUtils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wh1t3P1g
 * @since 2020/11/20
 */
@Slf4j
@Data
@Component
public class RulesContainer {

    private Map<String, List<Map<String, Object>>> sinks = new HashMap<>();

    public RulesContainer() throws FileNotFoundException {
        loadSinks();
    }

    public boolean isSink(String classname, String method){
        if(sinks.containsKey(classname)){
            List<Map<String, Object>> functions = sinks.get(classname);
            for(Map<String, Object> function:functions){
                if(method.equals(function.get("name"))){
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings({"unchecked"})
    private void loadSinks() throws FileNotFoundException {
        sinks = (Map<String, List<Map<String, Object>>>) FileUtils.getJsonContent(GlobalConfiguration.SINKS_PATH, Map.class);
        if(sinks == null){
            throw new FileNotFoundException("Sink File Not Found");
        }
        log.info("load "+ sinks.size() +" sinks success!");
    }
}
