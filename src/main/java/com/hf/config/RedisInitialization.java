package com.hf.config;

import com.hf.mapper.api.ApiValColCommentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghf/f5psyche@163.com
 * @version 1.0
 * @date 14:24 2022/6/22
 */
@Component
public class RedisInitialization {
    private static final Logger log = LoggerFactory.getLogger(RedisInitialization.class);

    @Resource
    ApiValColCommentsMapper apiValColCommentsMapper;

    @Resource
    ValueOperations<String, Object> valueOperations;

    private static final Map<String, String> INITIALIZATION;


    static {
        INITIALIZATION = new HashMap<>(16);

        INITIALIZATION.put("CustomApplicationInfo", "select max(app_id) \"maxId\" from custom_application_info");
        INITIALIZATION.put("CustomFormInfo", "select max(form_id) \"maxId\" from custom_form_info");
        INITIALIZATION.put("CustomPageInfo", "select max(page_id) \"maxId\" from custom_page_info");
        INITIALIZATION.put("CustomTableInfo", "select max(table_id) \"maxId\" from custom_table_info");
        INITIALIZATION.put("ApiCnfInfo", "select max(id) \"maxId\" from api_cnf_info");
        INITIALIZATION.put("ApiInsideInterfaceInfo", "select max(aii_id) \"maxId\" from api_inside_interface_info");
    }

    /**
     * 项目启动时先执行
     */
    @PostConstruct
    public void redisIncrInitialization() {
        INITIALIZATION.keySet().forEach(key -> {
            Map<String, Object> searchMap = new HashMap<>(2);
            searchMap.put("selectSql", INITIALIZATION.get(key));
            List<Map<String, Object>> list = apiValColCommentsMapper.customQuery(searchMap);
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, Object> map = list.get(0);
                if (map != null) {
                    Integer id = Integer.valueOf(map.get("maxId") == null ? "0" : map.get("maxId").toString());
                    valueOperations.set(key, id);
                }
            }
        });
    }
}
