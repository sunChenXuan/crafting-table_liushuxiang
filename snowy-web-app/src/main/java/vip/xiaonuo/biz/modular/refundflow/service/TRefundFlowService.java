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
package vip.xiaonuo.biz.modular.refundflow.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.refundflow.entity.TRefundFlow;
import vip.xiaonuo.biz.modular.refundflow.param.TRefundFlowAddParam;
import vip.xiaonuo.biz.modular.refundflow.param.TRefundFlowEditParam;
import vip.xiaonuo.biz.modular.refundflow.param.TRefundFlowIdParam;
import vip.xiaonuo.biz.modular.refundflow.param.TRefundFlowPageParam;

import java.util.List;

/**
 * 续费流水Service接口
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
public interface TRefundFlowService extends IService<TRefundFlow> {

    /**
     * 获取续费流水分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    Page<TRefundFlow> page(TRefundFlowPageParam tRefundFlowPageParam);

    /**
     * 添加续费流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void add(TRefundFlowAddParam tRefundFlowAddParam);

    /**
     * 编辑续费流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void edit(TRefundFlowEditParam tRefundFlowEditParam);

    /**
     * 删除续费流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void delete(List<TRefundFlowIdParam> tRefundFlowIdParamList);

    /**
     * 获取续费流水详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    TRefundFlow detail(TRefundFlowIdParam tRefundFlowIdParam);

    /**
     * 获取续费流水详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     **/
    TRefundFlow queryEntity(String id);
}
