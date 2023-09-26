/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.fixedassethardwareflow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.entity.TFixedAssetHardwareFlow;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.param.TFixedAssetHardwareFlowAddParam;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.param.TFixedAssetHardwareFlowEditParam;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.param.TFixedAssetHardwareFlowIdParam;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.param.TFixedAssetHardwareFlowPageParam;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.service.TFixedAssetHardwareFlowService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 固定资产硬件流水控制器
 *
 * @author scx
 * @date  2023/09/20 17:02
 */
@Api(tags = "固定资产硬件流水控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TFixedAssetHardwareFlowController {

    @Resource
    private TFixedAssetHardwareFlowService tFixedAssetHardwareFlowService;

    /**
     * 获取固定资产硬件流水分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取固定资产硬件流水分页")
    @SaCheckPermission("/biz/fixedassethardwareflow/page")
    @GetMapping("/biz/fixedassethardwareflow/page")
    public CommonResult<Page<TFixedAssetHardwareFlow>> page(TFixedAssetHardwareFlowPageParam tFixedAssetHardwareFlowPageParam) {
        return CommonResult.data(tFixedAssetHardwareFlowService.page(tFixedAssetHardwareFlowPageParam));
    }

    /**
     * 添加固定资产硬件流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加固定资产硬件流水")
    @CommonLog("添加固定资产硬件流水")
    @SaCheckPermission("/biz/fixedassethardwareflow/add")
    @PostMapping("/biz/fixedassethardwareflow/add")
    public CommonResult<String> add(@RequestBody @Valid TFixedAssetHardwareFlowAddParam tFixedAssetHardwareFlowAddParam) {
        tFixedAssetHardwareFlowService.add(tFixedAssetHardwareFlowAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑固定资产硬件流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑固定资产硬件流水")
    @CommonLog("编辑固定资产硬件流水")
    @SaCheckPermission("/biz/fixedassethardwareflow/edit")
    @PostMapping("/biz/fixedassethardwareflow/edit")
    public CommonResult<String> edit(@RequestBody @Valid TFixedAssetHardwareFlowEditParam tFixedAssetHardwareFlowEditParam) {
        tFixedAssetHardwareFlowService.edit(tFixedAssetHardwareFlowEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除固定资产硬件流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除固定资产硬件流水")
    @CommonLog("删除固定资产硬件流水")
    @SaCheckPermission("/biz/fixedassethardwareflow/delete")
    @PostMapping("/biz/fixedassethardwareflow/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TFixedAssetHardwareFlowIdParam> tFixedAssetHardwareFlowIdParamList) {
        tFixedAssetHardwareFlowService.delete(tFixedAssetHardwareFlowIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取固定资产硬件流水详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取固定资产硬件流水详情")
    @SaCheckPermission("/biz/fixedassethardwareflow/detail")
    @GetMapping("/biz/fixedassethardwareflow/detail")
    public CommonResult<TFixedAssetHardwareFlow> detail(@Valid TFixedAssetHardwareFlowIdParam tFixedAssetHardwareFlowIdParam) {
        return CommonResult.data(tFixedAssetHardwareFlowService.detail(tFixedAssetHardwareFlowIdParam));
    }
}
