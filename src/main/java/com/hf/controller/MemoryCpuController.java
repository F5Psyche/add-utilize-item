package com.hf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hf
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
        log.info("memoryMap={}", memoryMap);
        return "addMemory OK";
    }

    @RequestMapping({"addCpu"})
    public String addCpu() {
        ExecutorService executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),
                new ThreadPoolExecutor.AbortPolicy());
        executorService.execute(() -> {
            while (true) {
                byte b = 2;
                log.info("b={}", b);
            }
        });
        return "addCpu OK";
    }
}
