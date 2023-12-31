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
package vip.xiaonuo.biz.modular.fixedassetfile.controller;

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
import vip.xiaonuo.biz.modular.fixedassetfile.entity.TFixedAssetFile;
import vip.xiaonuo.biz.modular.fixedassetfile.param.TFixedAssetFileAddParam;
import vip.xiaonuo.biz.modular.fixedassetfile.param.TFixedAssetFileEditParam;
import vip.xiaonuo.biz.modular.fixedassetfile.param.TFixedAssetFileIdParam;
import vip.xiaonuo.biz.modular.fixedassetfile.param.TFixedAssetFilePageParam;
import vip.xiaonuo.biz.modular.fixedassetfile.service.TFixedAssetFileService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.dev.modular.file.enums.DevFileEngineTypeEnum;
import vip.xiaonuo.dev.modular.file.service.DevFileService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 固定资产文件控制器
 *
 * @author scx
 * @date  2023/09/20 17:02
 */
@Api(tags = "固定资产文件控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TFixedAssetFileController {

    @Resource
    private TFixedAssetFileService tFixedAssetFileService;

    @Resource
    private DevFileService devFileService;

    /**
     * 获取固定资产文件分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取固定资产文件分页")
    @SaCheckPermission("/biz/fixedassetfile/page")
    @GetMapping("/biz/fixedassetfile/page")
    public CommonResult<Page<TFixedAssetFile>> page(TFixedAssetFilePageParam tFixedAssetFilePageParam) {
        return CommonResult.data(tFixedAssetFileService.page(tFixedAssetFilePageParam));
    }

    /**
     * 添加固定资产文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加固定资产文件")
    @CommonLog("添加固定资产文件")
    @SaCheckPermission("/biz/fixedassetfile/add")
    @PostMapping("/biz/fixedassetfile/add")
    public CommonResult<String> add(@RequestPart("file") MultipartFile file, @RequestPart("data") String string) {
        final TFixedAssetFileAddParam tFixedAssetFileAddParam = JSONObject.parseObject(string, TFixedAssetFileAddParam.class);
        tFixedAssetFileAddParam.setUkFileId(devFileService.uploadReturnId(DevFileEngineTypeEnum.MINIO.getValue(), file));
        tFixedAssetFileService.add(tFixedAssetFileAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑固定资产文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑固定资产文件")
    @CommonLog("编辑固定资产文件")
    @SaCheckPermission("/biz/fixedassetfile/edit")
    @PostMapping("/biz/fixedassetfile/edit")
    public CommonResult<String> edit(@RequestPart("file") MultipartFile file, @RequestPart("data") String string) {
        final TFixedAssetFileEditParam tFixedAssetFileEditParam = JSONObject.parseObject(string, TFixedAssetFileEditParam.class);
        tFixedAssetFileEditParam.setUkFileId(devFileService.uploadReturnId(DevFileEngineTypeEnum.MINIO.getValue(), file));
        tFixedAssetFileService.edit(tFixedAssetFileEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除固定资产文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除固定资产文件")
    @CommonLog("删除固定资产文件")
    @SaCheckPermission("/biz/fixedassetfile/delete")
    @PostMapping("/biz/fixedassetfile/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TFixedAssetFileIdParam> tFixedAssetFileIdParamList) {
        tFixedAssetFileService.delete(tFixedAssetFileIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取固定资产文件详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取固定资产文件详情")
    @SaCheckPermission("/biz/fixedassetfile/detail")
    @GetMapping("/biz/fixedassetfile/detail")
    public CommonResult<TFixedAssetFile> detail(@Valid TFixedAssetFileIdParam tFixedAssetFileIdParam) {
        return CommonResult.data(tFixedAssetFileService.detail(tFixedAssetFileIdParam));
    }
}
