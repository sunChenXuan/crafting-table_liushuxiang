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
package vip.xiaonuo.biz.modular.project.controller;

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
import vip.xiaonuo.biz.modular.project.entity.TProject;
import vip.xiaonuo.biz.modular.project.param.TProjectAddParam;
import vip.xiaonuo.biz.modular.project.param.TProjectEditParam;
import vip.xiaonuo.biz.modular.project.param.TProjectIdParam;
import vip.xiaonuo.biz.modular.project.param.TProjectPageParam;
import vip.xiaonuo.biz.modular.project.service.TProjectService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 项目控制器
 *
 * @author scx
 * @date  2023/09/20 17:02
 */
@Api(tags = "项目控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TProjectController {

    @Resource
    private TProjectService tProjectService;

    /**
     * 获取项目分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目分页")
    @SaCheckPermission("/biz/project/page")
    @GetMapping("/biz/project/page")
    public CommonResult<Page<TProject>> page(TProjectPageParam tProjectPageParam) {
        return CommonResult.data(tProjectService.page(tProjectPageParam));
    }

    /**
     * 添加项目
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目")
    @CommonLog("添加项目")
    @SaCheckPermission("/biz/project/add")
    @PostMapping("/biz/project/add")
    public CommonResult<String> add(@RequestBody @Valid TProjectAddParam tProjectAddParam) {
        tProjectService.add(tProjectAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目")
    @CommonLog("编辑项目")
    @SaCheckPermission("/biz/project/edit")
    @PostMapping("/biz/project/edit")
    public CommonResult<String> edit(@RequestBody @Valid TProjectEditParam tProjectEditParam) {
        tProjectService.edit(tProjectEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目")
    @CommonLog("删除项目")
    @SaCheckPermission("/biz/project/delete")
    @PostMapping("/biz/project/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TProjectIdParam> tProjectIdParamList) {
        tProjectService.delete(tProjectIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目详情")
    @SaCheckPermission("/biz/project/detail")
    @GetMapping("/biz/project/detail")
    public CommonResult<TProject> detail(@Valid TProjectIdParam tProjectIdParam) {
        return CommonResult.data(tProjectService.detail(tProjectIdParam));
    }
}
