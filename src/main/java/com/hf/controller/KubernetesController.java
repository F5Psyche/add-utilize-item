package com.hf.controller;

import com.alibaba.fastjson.JSON;
import com.hf.tools.util.CommonCustomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

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
    public List<String> podIps() {
        List<String> ipList = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> ipAddressEnum = networkInterface.getInetAddresses();
                while (ipAddressEnum.hasMoreElements()) {
                    InetAddress ipAddress = ipAddressEnum.nextElement();
                    String ip = JSON.toJSONString(ipAddress);
                    if (ipAddress.isLoopbackAddress() || ip.contains(":")) {
                        continue;
                    }
                    if (!StringUtils.isEmpty(ip)) {
                        ipList.add(ip.replace("\"", ""));
                    }
                }
            }
            Collections.sort(ipList);
        } catch (Exception e) {
            log.error("errMsg={}", CommonCustomUtils.getStackTraceString(e));
        }
        return ipList;
    }
}
