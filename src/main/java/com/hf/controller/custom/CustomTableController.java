package com.hf.controller.custom;

import com.google.common.collect.Lists;
import com.hf.entity.po.custom.CustomTableInfo;
import com.hf.modules.service.custom.CustomService;
import com.hf.tools.config.enums.GlobalCustomCodeEnum;
import com.hf.tools.entity.ResultVo;
import com.hf.tools.util.CommonCustomUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.hf.tools.config.filter.CsrfDefenseFilter.UUID_KEY;

/**
 * @author zhanghf/f5psyche@163.com
 * @version 1.0
 * @date 14:31 2022/5/29
 */
@RestController
@RequestMapping("/table")
@Api(tags = "表格控制器")
public class CustomTableController {

    private static final Logger log = LoggerFactory.getLogger(CustomTableController.class);

    @Resource
    CustomService customService;

    @PostMapping(value = "/operate")
    public ResultVo<List<Object>> tableOperate(HttpServletRequest request,
                                               @RequestBody CustomTableInfo customTableInfo) {
        Object uuid = request.getAttribute(UUID_KEY).toString();
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            customService.customTableOperate(customTableInfo);
            resultVo.setResult(Lists.newArrayList(customTableInfo.getTableId()));
            resultVo.setResultDes(GlobalCustomCodeEnum.SUCCESS.getMsg());
            resultVo.setCode(GlobalCustomCodeEnum.SUCCESS.getCode());
            resultVo.setSuccess(true);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }

    @GetMapping(value = "/menu/search")
    public ResultVo<List<Object>> tableMenuSearch(HttpServletRequest request,
                                                  @ApiParam(value = "应用ID") @RequestParam(value = "appId") String appId) {
        Object uuid = request.getAttribute(UUID_KEY).toString();
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            List<Object> list = customService.customTableTreeSearch(uuid, appId);
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
