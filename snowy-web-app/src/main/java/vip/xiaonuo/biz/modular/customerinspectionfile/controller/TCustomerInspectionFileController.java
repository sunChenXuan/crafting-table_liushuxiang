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
package vip.xiaonuo.biz.modular.customerinspectionfile.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.customerinspectionfile.entity.TCustomerInspectionFile;
import vip.xiaonuo.biz.modular.customerinspectionfile.param.TCustomerInspectionFileAddParam;
import vip.xiaonuo.biz.modular.customerinspectionfile.param.TCustomerInspectionFileEditParam;
import vip.xiaonuo.biz.modular.customerinspectionfile.param.TCustomerInspectionFileIdParam;
import vip.xiaonuo.biz.modular.customerinspectionfile.param.TCustomerInspectionFilePageParam;
import vip.xiaonuo.biz.modular.customerinspectionfile.service.TCustomerInspectionFileService;
import vip.xiaonuo.dev.modular.file.enums.DevFileEngineTypeEnum;
import vip.xiaonuo.dev.modular.file.service.DevFileService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 客户巡检文件控制器
 *
 * @author scx
 * @date  2023/11/01 10:13
 */
@Api(tags = "客户巡检文件控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TCustomerInspectionFileController {

    @Resource
    private TCustomerInspectionFileService tCustomerInspectionFileService;

    @Resource
    private DevFileService devFileService;

    /**
     * 获取客户巡检文件分页
     *
     * @author scx
     * @date  2023/11/01 10:13
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取客户巡检文件分页")
    @SaCheckPermission("/biz/customerinspectionfile/page")
    @GetMapping("/biz/customerinspectionfile/page")
    public CommonResult<List<TCustomerInspectionFile>> page(TCustomerInspectionFilePageParam tCustomerInspectionFilePageParam) {
        // 重写为list
        final QueryWrapper<TCustomerInspectionFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(TCustomerInspectionFile::getIdxCustomerInspectionId, tCustomerInspectionFilePageParam.getIdxCustomerInspectionId());
        final List<TCustomerInspectionFile> list = tCustomerInspectionFileService.list(queryWrapper);
        for(TCustomerInspectionFile i : list){
            i.setFile(devFileService.queryEntity(i.getUkFileId()));
        }
        return CommonResult.data(list);
    }

    /**
     * 添加客户巡检文件
     *
     * @author scx
     * @date  2023/11/01 10:13
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加客户巡检文件")
    @CommonLog("添加客户巡检文件")
    @SaCheckPermission("/biz/customerinspectionfile/add")
    @PostMapping("/biz/customerinspectionfile/add")
    public CommonResult<String> add(@RequestPart("file") MultipartFile file, @RequestPart("data") String string) {
        final TCustomerInspectionFileAddParam tCustomerInspectionFileAddParam = new TCustomerInspectionFileAddParam();
        tCustomerInspectionFileAddParam.setIdxCustomerInspectionId(string);
        tCustomerInspectionFileAddParam.setUkFileId(devFileService.uploadReturnId(DevFileEngineTypeEnum.LOCAL.getValue(), file));
        tCustomerInspectionFileService.add(tCustomerInspectionFileAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑客户巡检文件
     *
     * @author scx
     * @date  2023/11/01 10:13
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑客户巡检文件")
    @CommonLog("编辑客户巡检文件")
    @SaCheckPermission("/biz/customerinspectionfile/edit")
    @PostMapping("/biz/customerinspectionfile/edit")
    public CommonResult<String> edit(@RequestBody @Valid TCustomerInspectionFileEditParam tCustomerInspectionFileEditParam) {
        tCustomerInspectionFileService.edit(tCustomerInspectionFileEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除客户巡检文件
     *
     * @author scx
     * @date  2023/11/01 10:13
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除客户巡检文件")
    @CommonLog("删除客户巡检文件")
    @SaCheckPermission("/biz/customerinspectionfile/delete")
    @PostMapping("/biz/customerinspectionfile/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TCustomerInspectionFileIdParam> tCustomerInspectionFileIdParamList) {
        tCustomerInspectionFileService.delete(tCustomerInspectionFileIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取客户巡检文件详情
     *
     * @author scx
     * @date  2023/11/01 10:13
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取客户巡检文件详情")
    @SaCheckPermission("/biz/customerinspectionfile/detail")
    @GetMapping("/biz/customerinspectionfile/detail")
    public CommonResult<TCustomerInspectionFile> detail(@Valid TCustomerInspectionFileIdParam tCustomerInspectionFileIdParam) {
        return CommonResult.data(tCustomerInspectionFileService.detail(tCustomerInspectionFileIdParam));
    }
}
