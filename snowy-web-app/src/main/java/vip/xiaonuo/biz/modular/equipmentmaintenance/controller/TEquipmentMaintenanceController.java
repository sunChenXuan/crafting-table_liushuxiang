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
package vip.xiaonuo.biz.modular.equipmentmaintenance.controller;

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
import vip.xiaonuo.biz.modular.equipmentmaintenance.entity.TEquipmentMaintenance;
import vip.xiaonuo.biz.modular.equipmentmaintenance.param.TEquipmentMaintenanceAddParam;
import vip.xiaonuo.biz.modular.equipmentmaintenance.param.TEquipmentMaintenanceEditParam;
import vip.xiaonuo.biz.modular.equipmentmaintenance.param.TEquipmentMaintenanceIdParam;
import vip.xiaonuo.biz.modular.equipmentmaintenance.param.TEquipmentMaintenancePageParam;
import vip.xiaonuo.biz.modular.equipmentmaintenance.service.TEquipmentMaintenanceService;
import vip.xiaonuo.biz.modular.project.entity.TProject;
import vip.xiaonuo.biz.modular.project.service.TProjectService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 设备维保控制器
 *
 * @author scx
 * @date  2023/09/20 17:02
 */
@Api(tags = "设备维保控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TEquipmentMaintenanceController {

    @Resource
    private TEquipmentMaintenanceService tEquipmentMaintenanceService;

    @Resource
    private TProjectService tProjectService;


    /**
     * 获取设备维保分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取设备维保分页")
    @SaCheckPermission("/biz/equipmentmaintenance/page")
    @GetMapping("/biz/equipmentmaintenance/page")
    public CommonResult<Page<TEquipmentMaintenance>> page(TEquipmentMaintenancePageParam tEquipmentMaintenancePageParam) {
        return CommonResult.data(tEquipmentMaintenanceService.page(tEquipmentMaintenancePageParam));
    }

    /**
     * 添加设备维保
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加设备维保")
    @CommonLog("添加设备维保")
    @SaCheckPermission("/biz/equipmentmaintenance/add")
    @PostMapping("/biz/equipmentmaintenance/add")
    public CommonResult<String> add(@RequestBody @Valid TEquipmentMaintenanceAddParam tEquipmentMaintenanceAddParam) {
        tEquipmentMaintenanceService.add(tEquipmentMaintenanceAddParam);
        return CommonResult.ok();
    }

    /**
     * 添加设备维保
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加设备维保")
    @CommonLog("添加设备维保-文件")
    @SaCheckPermission("/biz/equipmentmaintenance/addOrFile")
    @PostMapping("/biz/equipmentmaintenance/addOrFile")
    public CommonResult<String> addOrFile(@RequestPart("file") MultipartFile file, @RequestPart("data") String string) {
        // 从json获取数据
        TEquipmentMaintenanceAddParam tEquipmentMaintenanceAddParam = JSONObject.parseObject(string, TEquipmentMaintenanceAddParam.class);
        tEquipmentMaintenanceService.add(tEquipmentMaintenanceAddParam, file);
        return CommonResult.ok();
    }

    /**
     * 编辑设备维保
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑设备维保")
    @CommonLog("编辑设备维保")
    @SaCheckPermission("/biz/equipmentmaintenance/edit")
    @PostMapping("/biz/equipmentmaintenance/edit")
    public CommonResult<String> edit(@RequestBody @Valid TEquipmentMaintenanceEditParam tEquipmentMaintenanceEditParam) {
        tEquipmentMaintenanceService.edit(tEquipmentMaintenanceEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除设备维保
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除设备维保")
    @CommonLog("删除设备维保")
    @SaCheckPermission("/biz/equipmentmaintenance/delete")
    @PostMapping("/biz/equipmentmaintenance/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TEquipmentMaintenanceIdParam> tEquipmentMaintenanceIdParamList) {
        tEquipmentMaintenanceService.delete(tEquipmentMaintenanceIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取设备维保详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取设备维保详情")
    @SaCheckPermission("/biz/equipmentmaintenance/detail")
    @GetMapping("/biz/equipmentmaintenance/detail")
    public CommonResult<TEquipmentMaintenance> detail(@Valid TEquipmentMaintenanceIdParam tEquipmentMaintenanceIdParam) {
        return CommonResult.data(tEquipmentMaintenanceService.detail(tEquipmentMaintenanceIdParam));
    }
}
