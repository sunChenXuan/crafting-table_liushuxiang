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
package vip.xiaonuo.biz.modular.equipmentmaintenancefile.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.entity.TEquipmentMaintenanceFile;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.param.TEquipmentMaintenanceFileAddParam;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.param.TEquipmentMaintenanceFileEditParam;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.param.TEquipmentMaintenanceFileIdParam;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.param.TEquipmentMaintenanceFilePageParam;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.service.TEquipmentMaintenanceFileService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.dev.modular.file.enums.DevFileEngineTypeEnum;
import vip.xiaonuo.dev.modular.file.service.DevFileService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 设备维保文件控制器
 *
 * @author scx
 * @date  2023/09/20 17:02
 */
@Api(tags = "设备维保文件控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TEquipmentMaintenanceFileController {

    @Resource
    private TEquipmentMaintenanceFileService tEquipmentMaintenanceFileService;

    @Resource
    private DevFileService devFileService;

    /**
     * 获取设备维保文件分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取设备维保文件分页")
    @SaCheckPermission("/biz/equipmentmaintenancefile/page")
    @GetMapping("/biz/equipmentmaintenancefile/page")
    public CommonResult<Page<TEquipmentMaintenanceFile>> page(TEquipmentMaintenanceFilePageParam tEquipmentMaintenanceFilePageParam) {
        return CommonResult.data(tEquipmentMaintenanceFileService.page(tEquipmentMaintenanceFilePageParam));
    }

    /**
     * 添加设备维保文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加设备维保文件")
    @CommonLog("添加设备维保文件")
    @SaCheckPermission("/biz/equipmentmaintenancefile/add")
    @PostMapping("/biz/equipmentmaintenancefile/add")
    public CommonResult<String> add(@RequestPart("file") MultipartFile file, @RequestPart("data") String string) {
        TEquipmentMaintenanceFileAddParam tEquipmentMaintenanceFileAddParam = JSONObject.parseObject(string, TEquipmentMaintenanceFileAddParam.class);
        tEquipmentMaintenanceFileAddParam.setUkFileId(devFileService.uploadReturnId(DevFileEngineTypeEnum.MINIO.getValue(), file));
        tEquipmentMaintenanceFileService.add(tEquipmentMaintenanceFileAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑设备维保文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑设备维保文件")
    @CommonLog("编辑设备维保文件")
    @SaCheckPermission("/biz/equipmentmaintenancefile/edit")
    @PostMapping("/biz/equipmentmaintenancefile/edit")
    public CommonResult<String> edit(@RequestPart("file") MultipartFile file, @RequestPart("data") String string) {
        final TEquipmentMaintenanceFileEditParam tEquipmentMaintenanceFileEditParam = JSONObject.parseObject(string, TEquipmentMaintenanceFileEditParam.class);
        tEquipmentMaintenanceFileEditParam.setUkFileId(devFileService.uploadReturnId(DevFileEngineTypeEnum.MINIO.getValue(), file));
        tEquipmentMaintenanceFileService.edit(tEquipmentMaintenanceFileEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除设备维保文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除设备维保文件")
    @CommonLog("删除设备维保文件")
    @SaCheckPermission("/biz/equipmentmaintenancefile/delete")
    @PostMapping("/biz/equipmentmaintenancefile/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TEquipmentMaintenanceFileIdParam> tEquipmentMaintenanceFileIdParamList) {
        tEquipmentMaintenanceFileService.delete(tEquipmentMaintenanceFileIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取设备维保文件详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取设备维保文件详情")
    @SaCheckPermission("/biz/equipmentmaintenancefile/detail")
    @GetMapping("/biz/equipmentmaintenancefile/detail")
    public CommonResult<TEquipmentMaintenanceFile> detail(@Valid TEquipmentMaintenanceFileIdParam tEquipmentMaintenanceFileIdParam) {
        return CommonResult.data(tEquipmentMaintenanceFileService.detail(tEquipmentMaintenanceFileIdParam));
    }
}
