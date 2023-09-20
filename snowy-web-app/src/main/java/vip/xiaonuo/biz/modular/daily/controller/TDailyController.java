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
package vip.xiaonuo.biz.modular.daily.controller;

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
import vip.xiaonuo.biz.modular.daily.entity.TDaily;
import vip.xiaonuo.biz.modular.daily.param.TDailyAddParam;
import vip.xiaonuo.biz.modular.daily.param.TDailyEditParam;
import vip.xiaonuo.biz.modular.daily.param.TDailyIdParam;
import vip.xiaonuo.biz.modular.daily.param.TDailyPageParam;
import vip.xiaonuo.biz.modular.daily.service.TDailyService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 日报控制器
 *
 * @author scx
 * @date  2023/09/20 17:03
 */
@Api(tags = "日报控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TDailyController {

    @Resource
    private TDailyService tDailyService;

    /**
     * 获取日报分页
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取日报分页")
    @SaCheckPermission("/biz/daily/page")
    @GetMapping("/biz/daily/page")
    public CommonResult<Page<TDaily>> page(TDailyPageParam tDailyPageParam) {
        return CommonResult.data(tDailyService.page(tDailyPageParam));
    }

    /**
     * 添加日报
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加日报")
    @CommonLog("添加日报")
    @SaCheckPermission("/biz/daily/add")
    @PostMapping("/biz/daily/add")
    public CommonResult<String> add(@RequestBody @Valid TDailyAddParam tDailyAddParam) {
        tDailyService.add(tDailyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑日报
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑日报")
    @CommonLog("编辑日报")
    @SaCheckPermission("/biz/daily/edit")
    @PostMapping("/biz/daily/edit")
    public CommonResult<String> edit(@RequestBody @Valid TDailyEditParam tDailyEditParam) {
        tDailyService.edit(tDailyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除日报
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除日报")
    @CommonLog("删除日报")
    @SaCheckPermission("/biz/daily/delete")
    @PostMapping("/biz/daily/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TDailyIdParam> tDailyIdParamList) {
        tDailyService.delete(tDailyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取日报详情
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取日报详情")
    @SaCheckPermission("/biz/daily/detail")
    @GetMapping("/biz/daily/detail")
    public CommonResult<TDaily> detail(@Valid TDailyIdParam tDailyIdParam) {
        return CommonResult.data(tDailyService.detail(tDailyIdParam));
    }
}
