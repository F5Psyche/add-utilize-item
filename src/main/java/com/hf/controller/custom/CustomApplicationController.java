package com.hf.controller.custom;

import com.google.common.collect.Lists;
import com.hf.entity.po.custom.CustomApplicationInfo;
import com.hf.entity.vo.custom.CustomSearchVo;
import com.hf.modules.service.custom.CustomService;
import com.hf.tools.config.enums.custom.GlobalCustomCodeEnum;
import com.hf.tools.entity.ResultVo;
import com.hf.tools.util.CommonCustomUtils;
import com.hf.tools.util.JackJsonUtils;
import io.swagger.annotations.Api;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.hf.tools.config.filter.CsrfDefenseFilter.UUID_KEY;

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
        Object uuid = ThreadContext.get(UUID_KEY);
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            customService.customApplicationOperate(customApplicationInfo);
            resultVo.setResult(Lists.newArrayList(customApplicationInfo.getAppId()));
            resultVo.setResultDes(GlobalCustomCodeEnum.SUCCESS.getMsg());
            resultVo.setCode(GlobalCustomCodeEnum.SUCCESS.getCode());
            resultVo.setSuccess(true);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }

    @PostMapping(value = "/search")
    public ResultVo<List<CustomApplicationInfo>> applicationSearch(@RequestBody CustomSearchVo<Object> vo) {
        Object uuid = ThreadContext.get(UUID_KEY);
        ResultVo<List<CustomApplicationInfo>> resultVo = new ResultVo<>(uuid);
        try {
            String voData = JackJsonUtils.writeValueAsString(vo);
            log.info("uuid={}, vo={}", uuid, voData);
            return customService.customApplicationSearch(vo);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }
}
