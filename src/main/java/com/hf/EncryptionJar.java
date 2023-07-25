package com.hf;

import io.xjar.XCryptos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密包的生成
 *
 * @author zhanghf/f5psyche@163.com
 * @version 1.0
 * @date 10:49 2023/3/23
 */
public class EncryptionJar {

    private static final Logger log = LoggerFactory.getLogger(EncryptionJar.class);

    public static void main(String[] args) {
        try {
            XCryptos.encryption()
                    .from("C:\\Users\\f5psy\\Desktop\\区域项目-杭州市局退军\\encrypt\\ss-scene-service-provider.jar")
                    .use("uRLFriHE3$pxmNzt")
                    .to("C:\\Users\\f5psy\\Desktop\\区域项目-杭州市局退军\\decrypt\\ss-scene-service-provider\\ss-scene-service-provider.jar");
            log.info("加密完成");

//            XCryptos.decryption()
//                    .from("C:\\Users\\f5psy\\Desktop\\encrypt\\retire-0.0.1-SNAPSHOT.xjar")
//                    .use("sbwangxin")
//                    .to("C:\\Users\\f5psy\\Desktop\\encrypt\\retire-0.0.1-SNAPSHOT.jar");
//            log.info("解密完成");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
