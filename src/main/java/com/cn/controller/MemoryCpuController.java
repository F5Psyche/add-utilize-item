package com.cn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cn
 * @version 1.0
 * @date 9:17 2022/5/25
 */
@RestController
@RequestMapping("")
public class MemoryCpuController {

    private static final Logger log = LoggerFactory.getLogger(MemoryCpuController.class);

    private static Map<String, Object> memoryMap = new HashMap<>();

    private static SecureRandom secureRandom = new SecureRandom();

    @RequestMapping({"addMemory"})
    public String addMemory() {
        byte[] bytes = new byte[1073741824];
        memoryMap.put(secureRandom.nextInt(1000) + "", bytes);
        return "addMemory OK";
    }

    @RequestMapping({"addCpu"})
    public String addCpu() {
        Thread thread = new Thread(() -> {
            while (true) {
                byte b = 2;
                log.info("b={}", b);
            }
        });
        thread.start();
        return "addCpu OK";
    }
}
