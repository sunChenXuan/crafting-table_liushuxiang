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
package vip.xiaonuo.biz.modular.computerinspectionmanagement.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
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
import vip.xiaonuo.biz.modular.computerinspectionmanagement.entity.TComputerInspectionManagement;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.param.TComputerInspectionManagementAddParam;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.param.TComputerInspectionManagementEditParam;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.param.TComputerInspectionManagementIdParam;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.param.TComputerInspectionManagementPageParam;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.service.TComputerInspectionManagementService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 机房巡检管理控制器
 *
 * @author scx
 * @date  2024/01/17 08:46
 */
@Api(tags = "机房巡检管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TComputerInspectionManagementController {

    @Resource
    private TComputerInspectionManagementService tComputerInspectionManagementService;

    /**
     * 获取机房巡检管理分页
     *
     * @author scx
     * @date  2024/01/17 08:46
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取机房巡检管理分页")
    @SaCheckPermission("/biz/computerinspectionmanagement/page")
    @GetMapping("/biz/computerinspectionmanagement/page")
    public CommonResult<Page<TComputerInspectionManagement>> page(TComputerInspectionManagementPageParam tComputerInspectionManagementPageParam) {
        return CommonResult.data(tComputerInspectionManagementService.page(tComputerInspectionManagementPageParam));
    }

    /**
     * 获取机房巡检管理分页
     *
     * @author scx
     * @date  2024/01/17 08:46
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取机房巡检管理列表")
    @SaCheckLogin
    @GetMapping("/biz/computerinspectionmanagement/listByLoginUser")
    public CommonResult<List<TComputerInspectionManagement>> listByLoginUser() {
        return CommonResult.data(tComputerInspectionManagementService.listByLoginUser());
    }

    /**
     * 添加机房巡检管理
     *
     * @author scx
     * @date  2024/01/17 08:46
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加机房巡检管理")
    @CommonLog("添加机房巡检管理")
    @SaCheckPermission("/biz/computerinspectionmanagement/add")
    @PostMapping("/biz/computerinspectionmanagement/add")
    public CommonResult<String> add(@RequestBody @Valid TComputerInspectionManagementAddParam tComputerInspectionManagementAddParam) {
        tComputerInspectionManagementService.add(tComputerInspectionManagementAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑机房巡检管理
     *
     * @author scx
     * @date  2024/01/17 08:46
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑机房巡检管理")
    @CommonLog("编辑机房巡检管理")
    @SaCheckPermission("/biz/computerinspectionmanagement/edit")
    @PostMapping("/biz/computerinspectionmanagement/edit")
    public CommonResult<String> edit(@RequestBody @Valid TComputerInspectionManagementEditParam tComputerInspectionManagementEditParam) {
        tComputerInspectionManagementService.edit(tComputerInspectionManagementEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除机房巡检管理
     *
     * @author scx
     * @date  2024/01/17 08:46
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除机房巡检管理")
    @CommonLog("删除机房巡检管理")
    @SaCheckPermission("/biz/computerinspectionmanagement/delete")
    @PostMapping("/biz/computerinspectionmanagement/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TComputerInspectionManagementIdParam> tComputerInspectionManagementIdParamList) {
        tComputerInspectionManagementService.delete(tComputerInspectionManagementIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取机房巡检管理详情
     *
     * @author scx
     * @date  2024/01/17 08:46
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取机房巡检管理详情")
    @SaCheckPermission("/biz/computerinspectionmanagement/detail")
    @GetMapping("/biz/computerinspectionmanagement/detail")
    public CommonResult<TComputerInspectionManagement> detail(@Valid TComputerInspectionManagementIdParam tComputerInspectionManagementIdParam) {
        return CommonResult.data(tComputerInspectionManagementService.detail(tComputerInspectionManagementIdParam));
    }
}
