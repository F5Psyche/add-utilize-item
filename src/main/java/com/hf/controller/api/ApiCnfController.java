package com.hf.controller.api;

import com.hf.entity.po.api.ApiCnfInfo;
import com.hf.entity.vo.CustomAopSourceVo;
import com.hf.entity.vo.DataSourceInfoVo;
import com.hf.modules.service.api.ApiCnfInfoService;
import com.hf.tools.config.enums.GlobalCustomCodeEnum;
import com.hf.tools.entity.ResultVo;
import com.hf.tools.util.CommonCustomUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.hf.tools.config.filter.CsrfDefenseFilter.UUID_KEY;

/**
 * @author zhanghf/f5psyche@163.com
 * @version 1.0
 * @date 16:34 2022/12/5
 */
@RestController
@RequestMapping("cnf")
@Api(tags = "api 接口配置信息表维护")
public class ApiCnfController {

    private static final Logger log = LoggerFactory.getLogger(ApiCnfController.class);

    @Resource
    ApiCnfInfoService apiCnfInfoService;

    @ApiOperation("数据库连接测试")
    @PostMapping(value = "/dataSource/connectTest")
    public ResultVo<List<Object>> dataSourceConnectTest(HttpServletRequest request,
                                                        @RequestBody DataSourceInfoVo dataSourceInfoVo) {
        Object uuid = request.getAttribute(UUID_KEY).toString();
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            apiCnfInfoService.dataSourceConnectTest(dataSourceInfoVo);
            GlobalCustomCodeEnum.isSuccessResult(resultVo, Collections.emptyList());
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }

    @ApiOperation("数据库 aop 切面更换数据源测试 druid")
    @PostMapping(value = "/dataSource/aopConnectTest")
    public ResultVo<List<Map<String, Object>>> databaseAopConnectTest(HttpServletRequest request,
                                                                      @RequestBody CustomAopSourceVo vo) {
        Object uuid = request.getAttribute(UUID_KEY).toString();
        ResultVo<List<Map<String, Object>>> resultVo = new ResultVo<>(uuid);
        try {
            vo.setUuid(uuid);
            vo.setSwitchDatabase(!StringUtils.isEmpty(vo.getDataSourceInfoVo()));
            List<Map<String, Object>> list = apiCnfInfoService.databaseAopConnectTest(vo);
            GlobalCustomCodeEnum.isSuccessResult(resultVo, list);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }

    @ApiOperation("数据库存储")
    @PostMapping(value = "/dataSource/save")
    public ResultVo<List<Object>> apiCnfDataSourceSave(HttpServletRequest request,
                                                       @RequestBody DataSourceInfoVo dataSourceInfoVo) {
        Object uuid = request.getAttribute(UUID_KEY).toString();
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            String id = apiCnfInfoService.apiCnfDataSourceSave(uuid, dataSourceInfoVo);
            List<Object> list = new ArrayList<>();
            list.add(id);
            GlobalCustomCodeEnum.isSuccessResult(resultVo, list);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }


    @ApiOperation("配置信息操作")
    @PostMapping(value = "/info/operate")
    public ResultVo<List<Object>> apiCnfInfoOperate(HttpServletRequest request,
                                                    @RequestBody ApiCnfInfo apiCnfInfo) {
        Object uuid = request.getAttribute(UUID_KEY).toString();
        ResultVo<List<Object>> resultVo = new ResultVo<>(uuid);
        try {
            apiCnfInfoService.apiCnfInfoOperate(apiCnfInfo);
            List<Object> list = new ArrayList<>();
            list.add(apiCnfInfo.getId());
            GlobalCustomCodeEnum.isSuccessResult(resultVo, list);
        } catch (Exception e) {
            CommonCustomUtils.exceptionToResult(e, resultVo);
        }
        return resultVo;
    }
}
