package com.hf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanghf/f5psyche@163.com
 * @version 1.0
 * @date 14:31 2022/5/29
 */
@RestController
@RequestMapping("/pod")
public class KubernetesController {

//    private static final Logger log = LoggerFactory.getLogger(KubernetesController.class);
//
//    @GetMapping(value = "/ips")
//    public List<Map<String, String>> podIps() {
//        return CommonCustomUtils.getHostInfo("");
//    }
//
//    @Resource
//    ElasticsearchIndexService elasticsearchIndexService;
//
//    @GetMapping(value = "/esTest")
//    public void esTest() {
//        try {
//            elasticsearchIndexService.elasticIndexCreate(UUID.randomUUID(), ApiValColComments.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
