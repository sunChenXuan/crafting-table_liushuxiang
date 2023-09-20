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
package vip.xiaonuo.biz.modular.fixedassetfile.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.fixedassetfile.entity.TFixedAssetFile;
import vip.xiaonuo.biz.modular.fixedassetfile.param.TFixedAssetFileAddParam;
import vip.xiaonuo.biz.modular.fixedassetfile.param.TFixedAssetFileEditParam;
import vip.xiaonuo.biz.modular.fixedassetfile.param.TFixedAssetFileIdParam;
import vip.xiaonuo.biz.modular.fixedassetfile.param.TFixedAssetFilePageParam;

import java.util.List;

/**
 * 固定资产文件Service接口
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
public interface TFixedAssetFileService extends IService<TFixedAssetFile> {

    /**
     * 获取固定资产文件分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    Page<TFixedAssetFile> page(TFixedAssetFilePageParam tFixedAssetFilePageParam);

    /**
     * 添加固定资产文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void add(TFixedAssetFileAddParam tFixedAssetFileAddParam);

    /**
     * 编辑固定资产文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void edit(TFixedAssetFileEditParam tFixedAssetFileEditParam);

    /**
     * 删除固定资产文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void delete(List<TFixedAssetFileIdParam> tFixedAssetFileIdParamList);

    /**
     * 获取固定资产文件详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    TFixedAssetFile detail(TFixedAssetFileIdParam tFixedAssetFileIdParam);

    /**
     * 获取固定资产文件详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     **/
    TFixedAssetFile queryEntity(String id);
}
