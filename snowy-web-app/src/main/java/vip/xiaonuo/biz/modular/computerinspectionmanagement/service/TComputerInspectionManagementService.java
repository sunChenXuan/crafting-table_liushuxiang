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
package vip.xiaonuo.biz.modular.computerinspectionmanagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.entity.TComputerInspectionManagement;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.param.TComputerInspectionManagementAddParam;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.param.TComputerInspectionManagementEditParam;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.param.TComputerInspectionManagementIdParam;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.param.TComputerInspectionManagementPageParam;

import java.util.List;

/**
 * 机房巡检管理Service接口
 *
 * @author scx
 * @date  2024/01/17 08:46
 **/
public interface TComputerInspectionManagementService extends IService<TComputerInspectionManagement> {

    /**
     * 获取机房巡检管理分页
     *
     * @author scx
     * @date  2024/01/17 08:46
     */
    Page<TComputerInspectionManagement> page(TComputerInspectionManagementPageParam tComputerInspectionManagementPageParam);

    /**
     * 获取机房巡检管理列表
     *
     * @author scx
     * @date  2024/01/17 08:46
     */
    List<TComputerInspectionManagement> listByLoginUser();

    /**
     * 添加机房巡检管理
     *
     * @author scx
     * @date  2024/01/17 08:46
     */
    void add(TComputerInspectionManagementAddParam tComputerInspectionManagementAddParam);

    /**
     * 编辑机房巡检管理
     *
     * @author scx
     * @date  2024/01/17 08:46
     */
    void edit(TComputerInspectionManagementEditParam tComputerInspectionManagementEditParam);

    /**
     * 删除机房巡检管理
     *
     * @author scx
     * @date  2024/01/17 08:46
     */
    void delete(List<TComputerInspectionManagementIdParam> tComputerInspectionManagementIdParamList);

    /**
     * 获取机房巡检管理详情
     *
     * @author scx
     * @date  2024/01/17 08:46
     */
    TComputerInspectionManagement detail(TComputerInspectionManagementIdParam tComputerInspectionManagementIdParam);

    /**
     * 获取机房巡检管理详情
     *
     * @author scx
     * @date  2024/01/17 08:46
     **/
    TComputerInspectionManagement queryEntity(String id);
}
