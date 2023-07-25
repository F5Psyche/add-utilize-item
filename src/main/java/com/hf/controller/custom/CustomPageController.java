package com.hf.controller.custom;

import com.google.common.collect.Lists;
import com.hf.entity.po.custom.CustomPageInfo;
import com.hf.modules.service.custom.CustomService;
import com.hf.tools.config.enums.custom.GlobalCustomCodeEnum;
import com.hf.tools.entity.ResultVo;
import com.hf.tools.util.CommonCustomUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.hf.tools.config.filter.CsrfDefenseFilter.UUID_KEY;

/**
 * @author zhanghf/f5psyche@163.com
 * @version 1.0
 * @date 14:31 2022/5/29
 */
@RestController
@RequestMapping("/page")
@Api(tags = "页面控制器")
public class CustomPageController {

    private static final Logger log = LoggerFactory.getLogger(CustomPageController.class);

    @Resource
    CustomService customService;

    @PostMapping(value = "/operate")
    public ResultVo<List<Object>> pageOperate(@RequestBody CustomPageInfo customPageInfo) {
        Object uuid = ThreadContext.get(UUID_KEY);
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            customService.customPageOperate(customPageInfo);
            resultVo.setResult(Lists.newArrayList(customPageInfo.getCustomId()));
            resultVo.setResultDes(GlobalCustomCodeEnum.SUCCESS.getMsg());
            resultVo.setCode(GlobalCustomCodeEnum.SUCCESS.getCode());
            resultVo.setSuccess(true);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }


    @GetMapping(value = "/menu/search")
    public ResultVo<List<Object>> pageMenuSearch(@ApiParam(value = "应用ID") @RequestParam(value = "appId") String appId) {
        Object uuid = ThreadContext.get(UUID_KEY);
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            List<Object> list = customService.customPageTreeSearch(appId);
            resultVo.setResult(list);
            resultVo.setResultDes(GlobalCustomCodeEnum.SUCCESS.getMsg());
            resultVo.setCode(GlobalCustomCodeEnum.SUCCESS.getCode());
            resultVo.setSuccess(true);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }
}
