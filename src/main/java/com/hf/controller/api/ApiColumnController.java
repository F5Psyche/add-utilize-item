package com.hf.controller.api;

import com.google.common.collect.Lists;
import com.hf.entity.vo.CustomAopSourceVo;
import com.hf.modules.service.value.ValueSearchService;
import com.hf.tools.config.enums.custom.GlobalCustomCodeEnum;
import com.hf.tools.entity.ResultVo;
import com.hf.tools.util.CommonCustomUtils;
import com.hf.tools.util.data.StructureCustomUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhanghf/f5psyche@163.com
 * @version 1.0
 * @date 11:40 2022/6/24
 */
@RestController
@RequestMapping("")
@Api(tags = "")
public class ApiColumnController {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    private static Logger log = LoggerFactory.getLogger(ApiColumnController.class);

    @ApiOperation("redis")
    @GetMapping(value = "/redis/test")
    public ResultVo<List<Object>> redisCustomTest() {
        UUID uuid = UUID.randomUUID();
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            Map<String, Object> map = new HashMap<>(16);
            map.put("nodeName", "ClassName");
            map.put("nodeValue", "一班");
            map.put("nodeNo", 1);
            redisTemplate.opsForValue().set("redis", map);
            Object data = redisTemplate.opsForValue().get("redis");
            log.info("uuid={}, data={}, className={}", uuid, data, data.getClass().getSimpleName());
            GlobalCustomCodeEnum.isSuccessResult(resultVo, Lists.newArrayList(map));
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }

    @Resource
    ValueSearchService valueSearchService;

    @ApiOperation("dataNode")
    @GetMapping(value = "/dataNode")
    public ResultVo<List<Object>> dataNode() {
        UUID uuid = UUID.randomUUID();
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            CustomAopSourceVo vo = new CustomAopSourceVo();
            Map<String, Object> objectMap = new HashMap<>(16);
            vo.setObjectMap(objectMap);
            List<Map<String, Object>> list = valueSearchService.customSearch(vo, "select * from aa26_cb", null, null).getResult();
            long step1 = System.currentTimeMillis();
            List<Object> obs = StructureCustomUtils.dataToDataNode(list, "AAB301", "AAA148", "children");
            long step2 = System.currentTimeMillis();
            log.info("cus={}", step2 - step1);
            GlobalCustomCodeEnum.isSuccessResult(resultVo, obs);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }
}
