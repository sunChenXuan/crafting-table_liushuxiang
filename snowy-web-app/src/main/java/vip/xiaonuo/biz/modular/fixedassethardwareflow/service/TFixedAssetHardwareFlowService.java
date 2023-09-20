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
package vip.xiaonuo.biz.modular.fixedassethardwareflow.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.entity.TFixedAssetHardwareFlow;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.param.TFixedAssetHardwareFlowAddParam;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.param.TFixedAssetHardwareFlowEditParam;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.param.TFixedAssetHardwareFlowIdParam;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.param.TFixedAssetHardwareFlowPageParam;

import java.util.List;

/**
 * 固定资产硬件流水Service接口
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
public interface TFixedAssetHardwareFlowService extends IService<TFixedAssetHardwareFlow> {

    /**
     * 获取固定资产硬件流水分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    Page<TFixedAssetHardwareFlow> page(TFixedAssetHardwareFlowPageParam tFixedAssetHardwareFlowPageParam);

    /**
     * 添加固定资产硬件流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void add(TFixedAssetHardwareFlowAddParam tFixedAssetHardwareFlowAddParam);

    /**
     * 编辑固定资产硬件流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void edit(TFixedAssetHardwareFlowEditParam tFixedAssetHardwareFlowEditParam);

    /**
     * 删除固定资产硬件流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void delete(List<TFixedAssetHardwareFlowIdParam> tFixedAssetHardwareFlowIdParamList);

    /**
     * 获取固定资产硬件流水详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    TFixedAssetHardwareFlow detail(TFixedAssetHardwareFlowIdParam tFixedAssetHardwareFlowIdParam);

    /**
     * 获取固定资产硬件流水详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     **/
    TFixedAssetHardwareFlow queryEntity(String id);
}
