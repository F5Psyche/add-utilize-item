package com.hf.controller;

import com.hf.entity.po.api.ApiValColComments;
import com.hf.es.modules.service.ElasticsearchIndexService;
import com.hf.tools.util.CommonCustomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhanghf/f5psyche@163.com
 * @version 1.0
 * @date 14:31 2022/5/29
 */
@RestController
@RequestMapping("/pod")
public class KubernetesController {

    private static final Logger log = LoggerFactory.getLogger(KubernetesController.class);

    @GetMapping(value = "/ips")
    public List<Map<String, String>> podIps() {
        return CommonCustomUtils.getHostInfo("");
    }

    @Resource
    ElasticsearchIndexService elasticsearchIndexService;

    @GetMapping(value = "/esTest")
    public void esTest() {
        try {
            elasticsearchIndexService.elasticIndexCreate(UUID.randomUUID(), ApiValColComments.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
