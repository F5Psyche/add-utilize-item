package com.hf.controller.api;

import com.hf.entity.po.api.ApiValColComments;
import com.hf.modules.service.api.ApiCustomService;
import com.hf.tools.config.enums.GlobalCustomCodeEnum;
import com.hf.tools.entity.ResultVo;
import com.hf.tools.util.CommonCustomUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author zhanghf/f5psyche@163.com
 * @version 1.0
 * @date 14:01 2022/6/27
 */
@RestController
@RequestMapping("/inside/interface")
@Api(tags = "内部接口控制器")
public class ApiInsideInterfaceController {

    private static final Logger log = LoggerFactory.getLogger(ApiInsideInterfaceController.class);

    @Resource
    ApiCustomService apiCustomService;

    @ApiOperation("接口查询")
    @GetMapping(value = "/menu/search")
    public ResultVo<List<Object>> insideInterfaceMenuSearch(@ApiParam(value = "接口类型（1.保存；2.删除；3.修改；4.查询）") @RequestParam(value = "apiType") String apiType) {
        UUID uuid = UUID.randomUUID();
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            List<Object> list = apiCustomService.apiInsideInterfaceTreeSearch(apiType);
            resultVo.setResult(list);
            resultVo.setResultDes(GlobalCustomCodeEnum.SUCCESS.getMsg());
            resultVo.setCode(GlobalCustomCodeEnum.SUCCESS.getCode());
            resultVo.setSuccess(true);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }

    public ResultVo<List<Object>> insideInterfaceSearchSave(){


        return null;
    }


    @ApiOperation("接口涉及字段")
    @GetMapping(value = " /apiCode/column")

    public ResultVo<List<ApiValColComments>> insideInterfaceColumn() {
        UUID uuid = UUID.randomUUID();
        ResultVo<List<ApiValColComments>> resultVo = new ResultVo<>(uuid);
        try {
            List<ApiValColComments> list = apiCustomService.apiColumnInfo(3L);
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
