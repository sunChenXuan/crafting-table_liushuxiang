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
package vip.xiaonuo.biz.modular.fixedassetflow.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.fixedassetflow.entity.TFixedAssetFlow;
import vip.xiaonuo.biz.modular.fixedassetflow.param.TFixedAssetFlowAddParam;
import vip.xiaonuo.biz.modular.fixedassetflow.param.TFixedAssetFlowEditParam;
import vip.xiaonuo.biz.modular.fixedassetflow.param.TFixedAssetFlowIdParam;
import vip.xiaonuo.biz.modular.fixedassetflow.param.TFixedAssetFlowPageParam;

import java.util.List;

/**
 * 固定资产借还流水Service接口
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
public interface TFixedAssetFlowService extends IService<TFixedAssetFlow> {

    /**
     * 获取固定资产借还流水分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    Page<TFixedAssetFlow> page(TFixedAssetFlowPageParam tFixedAssetFlowPageParam);

    /**
     * 添加固定资产借还流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void add(TFixedAssetFlowAddParam tFixedAssetFlowAddParam);

    /**
     * 编辑固定资产借还流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void edit(TFixedAssetFlowEditParam tFixedAssetFlowEditParam);

    /**
     * 删除固定资产借还流水
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void delete(List<TFixedAssetFlowIdParam> tFixedAssetFlowIdParamList);

    /**
     * 获取固定资产借还流水详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    TFixedAssetFlow detail(TFixedAssetFlowIdParam tFixedAssetFlowIdParam);

    /**
     * 获取固定资产借还流水详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     **/
    TFixedAssetFlow queryEntity(String id);
}
