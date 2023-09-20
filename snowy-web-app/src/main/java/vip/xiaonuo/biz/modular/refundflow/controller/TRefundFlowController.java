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
package vip.xiaonuo.biz.modular.refundflow.controller;

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
import vip.xiaonuo.biz.modular.refundflow.entity.TRefundFlow;
import vip.xiaonuo.biz.modular.refundflow.param.TRefundFlowAddParam;
import vip.xiaonuo.biz.modular.refundflow.param.TRefundFlowEditParam;
import vip.xiaonuo.biz.modular.refundflow.param.TRefundFlowIdParam;
import vip.xiaonuo.biz.modular.refundflow.param.TRefundFlowPageParam;
import vip.xiaonuo.biz.modular.refundflow.service.TRefundFlowService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 续费流水控制器
 *
 * @author scx
 * @date  2023/09/20 17:02
 */
@Api(tags = "续费流水控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TRefundFlowController {

    @Resource
    private TRefundFlowService tRefundFlowService;

    /**
     * 获取续费流水分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取续费流水分页")
    @SaCheckPermission("/biz/refundflow/page")
    @GetMapping("/biz/refundflow/page")
    public CommonResult<Page<TRefundFlow>> page(TRefundFlowPageParam tRefundFlowPageParam) {
        return CommonResult.data(tRefundFlowService.page(tRefundFlowPageParam));
    }

    /**
     * 添加续费流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加续费流水")
    @CommonLog("添加续费流水")
    @SaCheckPermission("/biz/refundflow/add")
    @PostMapping("/biz/refundflow/add")
    public CommonResult<String> add(@RequestBody @Valid TRefundFlowAddParam tRefundFlowAddParam) {
        tRefundFlowService.add(tRefundFlowAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑续费流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑续费流水")
    @CommonLog("编辑续费流水")
    @SaCheckPermission("/biz/refundflow/edit")
    @PostMapping("/biz/refundflow/edit")
    public CommonResult<String> edit(@RequestBody @Valid TRefundFlowEditParam tRefundFlowEditParam) {
        tRefundFlowService.edit(tRefundFlowEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除续费流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除续费流水")
    @CommonLog("删除续费流水")
    @SaCheckPermission("/biz/refundflow/delete")
    @PostMapping("/biz/refundflow/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TRefundFlowIdParam> tRefundFlowIdParamList) {
        tRefundFlowService.delete(tRefundFlowIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取续费流水详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取续费流水详情")
    @SaCheckPermission("/biz/refundflow/detail")
    @GetMapping("/biz/refundflow/detail")
    public CommonResult<TRefundFlow> detail(@Valid TRefundFlowIdParam tRefundFlowIdParam) {
        return CommonResult.data(tRefundFlowService.detail(tRefundFlowIdParam));
    }
}
