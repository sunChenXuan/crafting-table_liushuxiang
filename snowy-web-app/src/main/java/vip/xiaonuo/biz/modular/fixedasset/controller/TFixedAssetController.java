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
package vip.xiaonuo.biz.modular.fixedasset.controller;

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
import vip.xiaonuo.biz.modular.fixedasset.entity.TFixedAsset;
import vip.xiaonuo.biz.modular.fixedasset.param.TFixedAssetAddParam;
import vip.xiaonuo.biz.modular.fixedasset.param.TFixedAssetEditParam;
import vip.xiaonuo.biz.modular.fixedasset.param.TFixedAssetIdParam;
import vip.xiaonuo.biz.modular.fixedasset.param.TFixedAssetPageParam;
import vip.xiaonuo.biz.modular.fixedasset.service.TFixedAssetService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 固定资产控制器
 *
 * @author scx
 * @date  2023/09/20 17:02
 */
@Api(tags = "固定资产控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TFixedAssetController {

    @Resource
    private TFixedAssetService tFixedAssetService;

    /**
     * 获取固定资产分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取固定资产分页")
    @SaCheckPermission("/biz/fixedasset/page")
    @GetMapping("/biz/fixedasset/page")
    public CommonResult<Page<TFixedAsset>> page(TFixedAssetPageParam tFixedAssetPageParam) {
        return CommonResult.data(tFixedAssetService.page(tFixedAssetPageParam));
    }

    /**
     * 添加固定资产
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加固定资产")
    @CommonLog("添加固定资产")
    @SaCheckPermission("/biz/fixedasset/add")
    @PostMapping("/biz/fixedasset/add")
    public CommonResult<String> add(@RequestBody @Valid TFixedAssetAddParam tFixedAssetAddParam) {
        tFixedAssetService.add(tFixedAssetAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑固定资产
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑固定资产")
    @CommonLog("编辑固定资产")
    @SaCheckPermission("/biz/fixedasset/edit")
    @PostMapping("/biz/fixedasset/edit")
    public CommonResult<String> edit(@RequestBody @Valid TFixedAssetEditParam tFixedAssetEditParam) {
        tFixedAssetService.edit(tFixedAssetEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除固定资产
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除固定资产")
    @CommonLog("删除固定资产")
    @SaCheckPermission("/biz/fixedasset/delete")
    @PostMapping("/biz/fixedasset/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<TFixedAssetIdParam> tFixedAssetIdParamList) {
        tFixedAssetService.delete(tFixedAssetIdParamList);
        return CommonResult.ok();
    }

    /**
     * 删除固定资产
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("归还固定资产")
    @CommonLog("归还固定资产")
    @SaCheckPermission("/biz/fixedasset/isReturn")
    @PostMapping("/biz/fixedasset/isReturn")
    public CommonResult<String> isReturn(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<TFixedAssetIdParam> tFixedAssetIdParamList) {
        tFixedAssetService.isReturn(tFixedAssetIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取固定资产详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取固定资产详情")
    @SaCheckPermission("/biz/fixedasset/detail")
    @GetMapping("/biz/fixedasset/detail")
    public CommonResult<TFixedAsset> detail(@Valid TFixedAssetIdParam tFixedAssetIdParam) {
        return CommonResult.data(tFixedAssetService.detail(tFixedAssetIdParam));
    }
}
