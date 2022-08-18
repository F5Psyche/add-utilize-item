package com.hf.controller.custom;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hf.entity.po.custom.CustomApplicationInfo;
import com.hf.entity.vo.custom.CustomSearchVo;
import com.hf.modules.service.custom.CustomService;
import com.hf.tools.config.enums.GlobalCustomCodeEnum;
import com.hf.tools.entity.ResultVo;
import com.hf.tools.util.CommonCustomUtils;
import com.hf.tools.util.data.RedisCustomUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author zhanghf/f5psyche@163.com
 * @version 1.0
 * @date 14:31 2022/5/29
 */
@RestController
@RequestMapping("/app")
@Api(tags = "应用控制器")
public class CustomApplicationController {

    private static final Logger log = LoggerFactory.getLogger(CustomApplicationController.class);

    @Resource
    CustomService customService;


    @PostMapping(value = "/operate")
    public ResultVo<List<Object>> applicationOperate(@RequestBody CustomApplicationInfo customApplicationInfo) {
        UUID uuid = UUID.randomUUID();
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            Long id = customService.customApplicationOperate(uuid, customApplicationInfo);
            resultVo.setResult(Lists.newArrayList(id));
            resultVo.setResultDes(GlobalCustomCodeEnum.SUCCESS.getMsg());
            resultVo.setCode(GlobalCustomCodeEnum.SUCCESS.getCode());
            resultVo.setSuccess(true);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }

    @PostMapping(value = "/search")
    public ResultVo<List<CustomApplicationInfo>> applicationSearch(HttpServletRequest request,
                                                                   @RequestBody CustomSearchVo vo) {
        UUID uuid = UUID.randomUUID();
        ResultVo<List<CustomApplicationInfo>> resultVo = new ResultVo<>(uuid);
        try {
            log.info("uuid={}, vo={}", uuid, JSON.toJSON(vo));
            resultVo = customService.customApplicationSearch(uuid, vo);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }

    @Resource
    RedisCustomUtils redisCustomUtils;

    @GetMapping(value = "/test")
    public void appTest() {
        log.info("value={}", redisCustomUtils.tableIdAutoInc("", "CustomApplicationInfo"));
    }
}
