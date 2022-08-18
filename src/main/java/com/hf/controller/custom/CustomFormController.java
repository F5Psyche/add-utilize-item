package com.hf.controller.custom;

import com.google.common.collect.Lists;
import com.hf.entity.po.custom.CustomFormInfo;
import com.hf.modules.service.custom.CustomService;
import com.hf.tools.config.enums.GlobalCustomCodeEnum;
import com.hf.tools.entity.ResultVo;
import com.hf.tools.util.CommonCustomUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author zhanghf/f5psyche@163.com
 * @version 1.0
 * @date 14:31 2022/5/29
 */
@RestController
@RequestMapping("/form")
@Api(tags = "表单控制器")
public class CustomFormController {

    private static final Logger log = LoggerFactory.getLogger(CustomFormController.class);

    @Resource
    CustomService customService;

    @PostMapping(value = "/operate")
    public ResultVo<List<Object>> formOperate(@RequestBody CustomFormInfo customFormInfo) {
        UUID uuid = UUID.randomUUID();
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            Long id = customService.customFormOperate(uuid, customFormInfo);
            resultVo.setResult(Lists.newArrayList(id));
            resultVo.setResultDes(GlobalCustomCodeEnum.SUCCESS.getMsg());
            resultVo.setCode(GlobalCustomCodeEnum.SUCCESS.getCode());
            resultVo.setSuccess(true);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }

    @GetMapping(value = "/menu/search")
    public ResultVo<List<Object>> formMenuSearch(@ApiParam(value = "应用ID") @RequestParam(value = "appId") Long appId) {
        UUID uuid = UUID.randomUUID();
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            List<Object> list = customService.customFormTreeSearch(appId);
            resultVo.setResult(list);
            resultVo.setResultDes(GlobalCustomCodeEnum.SUCCESS.getMsg());
            resultVo.setCode(GlobalCustomCodeEnum.SUCCESS.getCode());
            resultVo.setSuccess(true);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }

    @ApiOperation("表单/页面/表格详情查询")
    @GetMapping(value = "/detail/search")
    public ResultVo<Object> formDetailSearch(@ApiParam(value = "主键ID") @RequestParam(value = "id") Long id,
                                             @ApiParam(value = "类型（1.表单；2.页面；3.表格）") @RequestParam(value = "searchType") Integer searchType) {
        UUID uuid = UUID.randomUUID();
        ResultVo<Object> resultVo = new ResultVo<>(uuid);
        try {
            if (searchType == 1) {
                resultVo.setResult(customService.customFormSearch(id));
            } else if (searchType == 2) {
                resultVo.setResult(customService.customPageSearch(id));
            } else if (searchType == 3) {
                resultVo.setResult(customService.customTableSearch(id));
            }
            resultVo.setResultDes(GlobalCustomCodeEnum.SUCCESS.getMsg());
            resultVo.setCode(GlobalCustomCodeEnum.SUCCESS.getCode());
            resultVo.setSuccess(true);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }
}
