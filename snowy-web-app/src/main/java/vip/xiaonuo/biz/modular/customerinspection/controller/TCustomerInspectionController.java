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
package vip.xiaonuo.biz.modular.customerinspection.controller;

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
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.customerinspection.entity.TCustomerInspection;
import vip.xiaonuo.biz.modular.customerinspection.param.TCustomerInspectionAddParam;
import vip.xiaonuo.biz.modular.customerinspection.param.TCustomerInspectionEditParam;
import vip.xiaonuo.biz.modular.customerinspection.param.TCustomerInspectionIdParam;
import vip.xiaonuo.biz.modular.customerinspection.param.TCustomerInspectionPageParam;
import vip.xiaonuo.biz.modular.customerinspection.service.TCustomerInspectionService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 客户巡检控制器
 *
 * @author scx
 * @date  2023/09/20 17:03
 */
@Api(tags = "客户巡检控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TCustomerInspectionController {

    @Resource
    private TCustomerInspectionService tCustomerInspectionService;

    /**
     * 获取客户巡检分页
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取客户巡检分页")
    @SaCheckPermission("/biz/customerinspection/page")
    @GetMapping("/biz/customerinspection/page")
    public CommonResult<Page<TCustomerInspection>> page(TCustomerInspectionPageParam tCustomerInspectionPageParam) {
        return CommonResult.data(tCustomerInspectionService.page(tCustomerInspectionPageParam));
    }

    /**
     * 添加客户巡检
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加客户巡检")
    @CommonLog("添加客户巡检")
    @SaCheckPermission("/biz/customerinspection/add")
    @PostMapping("/biz/customerinspection/add")
    public CommonResult<String> add(@RequestBody @Valid TCustomerInspectionAddParam tCustomerInspectionAddParam) {
        tCustomerInspectionService.add(tCustomerInspectionAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑客户巡检
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑客户巡检")
    @CommonLog("编辑客户巡检")
    @SaCheckPermission("/biz/customerinspection/edit")
    @PostMapping("/biz/customerinspection/edit")
    public CommonResult<String> edit(@RequestBody @Valid TCustomerInspectionEditParam tCustomerInspectionEditParam) {
        tCustomerInspectionService.edit(tCustomerInspectionEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除客户巡检
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除客户巡检")
    @CommonLog("删除客户巡检")
    @SaCheckPermission("/biz/customerinspection/delete")
    @PostMapping("/biz/customerinspection/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TCustomerInspectionIdParam> tCustomerInspectionIdParamList) {
        tCustomerInspectionService.delete(tCustomerInspectionIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取客户巡检详情
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取客户巡检详情")
    @SaCheckPermission("/biz/customerinspection/detail")
    @GetMapping("/biz/customerinspection/detail")
    public CommonResult<TCustomerInspection> detail(@Valid TCustomerInspectionIdParam tCustomerInspectionIdParam) {
        return CommonResult.data(tCustomerInspectionService.detail(tCustomerInspectionIdParam));
    }
}
