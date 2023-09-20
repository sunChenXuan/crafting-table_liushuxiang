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
package vip.xiaonuo.biz.modular.customerinspection.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.customerinspection.entity.TCustomerInspection;
import vip.xiaonuo.biz.modular.customerinspection.param.TCustomerInspectionAddParam;
import vip.xiaonuo.biz.modular.customerinspection.param.TCustomerInspectionEditParam;
import vip.xiaonuo.biz.modular.customerinspection.param.TCustomerInspectionIdParam;
import vip.xiaonuo.biz.modular.customerinspection.param.TCustomerInspectionPageParam;

import java.util.List;

/**
 * 客户巡检Service接口
 *
 * @author scx
 * @date  2023/09/20 17:03
 **/
public interface TCustomerInspectionService extends IService<TCustomerInspection> {

    /**
     * 获取客户巡检分页
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    Page<TCustomerInspection> page(TCustomerInspectionPageParam tCustomerInspectionPageParam);

    /**
     * 添加客户巡检
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    void add(TCustomerInspectionAddParam tCustomerInspectionAddParam);

    /**
     * 编辑客户巡检
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    void edit(TCustomerInspectionEditParam tCustomerInspectionEditParam);

    /**
     * 删除客户巡检
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    void delete(List<TCustomerInspectionIdParam> tCustomerInspectionIdParamList);

    /**
     * 获取客户巡检详情
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    TCustomerInspection detail(TCustomerInspectionIdParam tCustomerInspectionIdParam);

    /**
     * 获取客户巡检详情
     *
     * @author scx
     * @date  2023/09/20 17:03
     **/
    TCustomerInspection queryEntity(String id);
}
