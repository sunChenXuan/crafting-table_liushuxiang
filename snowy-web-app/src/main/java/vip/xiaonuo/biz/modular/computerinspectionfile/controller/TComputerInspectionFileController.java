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
package vip.xiaonuo.biz.modular.computerinspectionfile.controller;

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
import vip.xiaonuo.biz.modular.computerinspectionfile.entity.TComputerInspectionFile;
import vip.xiaonuo.biz.modular.computerinspectionfile.param.TComputerInspectionFileAddParam;
import vip.xiaonuo.biz.modular.computerinspectionfile.param.TComputerInspectionFileEditParam;
import vip.xiaonuo.biz.modular.computerinspectionfile.param.TComputerInspectionFileIdParam;
import vip.xiaonuo.biz.modular.computerinspectionfile.param.TComputerInspectionFilePageParam;
import vip.xiaonuo.biz.modular.computerinspectionfile.service.TComputerInspectionFileService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 机房巡检文件控制器
 *
 * @author scx
 * @date  2024/01/19 09:56
 */
@Api(tags = "机房巡检文件控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TComputerInspectionFileController {

    @Resource
    private TComputerInspectionFileService tComputerInspectionFileService;

    /**
     * 获取机房巡检文件分页
     *
     * @author scx
     * @date  2024/01/19 09:56
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取机房巡检文件分页")
    @SaCheckPermission("/biz/computerinspectionfile/page")
    @GetMapping("/biz/computerinspectionfile/page")
    public CommonResult<Page<TComputerInspectionFile>> page(TComputerInspectionFilePageParam tComputerInspectionFilePageParam) {
        return CommonResult.data(tComputerInspectionFileService.page(tComputerInspectionFilePageParam));
    }

    /**
     * 添加机房巡检文件
     *
     * @author scx
     * @date  2024/01/19 09:56
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加机房巡检文件")
    @CommonLog("添加机房巡检文件")
    @SaCheckPermission("/biz/computerinspectionfile/add")
    @PostMapping("/biz/computerinspectionfile/add")
    public CommonResult<String> add(@RequestBody @Valid TComputerInspectionFileAddParam tComputerInspectionFileAddParam) {
        tComputerInspectionFileService.add(tComputerInspectionFileAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑机房巡检文件
     *
     * @author scx
     * @date  2024/01/19 09:56
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑机房巡检文件")
    @CommonLog("编辑机房巡检文件")
    @SaCheckPermission("/biz/computerinspectionfile/edit")
    @PostMapping("/biz/computerinspectionfile/edit")
    public CommonResult<String> edit(@RequestBody @Valid TComputerInspectionFileEditParam tComputerInspectionFileEditParam) {
        tComputerInspectionFileService.edit(tComputerInspectionFileEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除机房巡检文件
     *
     * @author scx
     * @date  2024/01/19 09:56
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除机房巡检文件")
    @CommonLog("删除机房巡检文件")
    @SaCheckPermission("/biz/computerinspectionfile/delete")
    @PostMapping("/biz/computerinspectionfile/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TComputerInspectionFileIdParam> tComputerInspectionFileIdParamList) {
        tComputerInspectionFileService.delete(tComputerInspectionFileIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取机房巡检文件详情
     *
     * @author scx
     * @date  2024/01/19 09:56
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取机房巡检文件详情")
    @SaCheckPermission("/biz/computerinspectionfile/detail")
    @GetMapping("/biz/computerinspectionfile/detail")
    public CommonResult<TComputerInspectionFile> detail(@Valid TComputerInspectionFileIdParam tComputerInspectionFileIdParam) {
        return CommonResult.data(tComputerInspectionFileService.detail(tComputerInspectionFileIdParam));
    }
}
