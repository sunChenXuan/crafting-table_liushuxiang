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
package vip.xiaonuo.biz.modular.projectfile.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.projectfile.entity.TProjectFile;
import vip.xiaonuo.biz.modular.projectfile.param.TProjectFileAddParam;
import vip.xiaonuo.biz.modular.projectfile.param.TProjectFileEditParam;
import vip.xiaonuo.biz.modular.projectfile.param.TProjectFileIdParam;
import vip.xiaonuo.biz.modular.projectfile.param.TProjectFilePageParam;

import java.util.List;

/**
 * 项目文件Service接口
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
public interface TProjectFileService extends IService<TProjectFile> {

    /**
     * 获取项目文件分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    Page<TProjectFile> page(TProjectFilePageParam tProjectFilePageParam);

    /**
     * 添加项目文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void add(TProjectFileAddParam tProjectFileAddParam);

    /**
     * 编辑项目文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void edit(TProjectFileEditParam tProjectFileEditParam);

    /**
     * 删除项目文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void delete(List<TProjectFileIdParam> tProjectFileIdParamList);

    /**
     * 获取项目文件详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    TProjectFile detail(TProjectFileIdParam tProjectFileIdParam);

    /**
     * 获取项目文件详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     **/
    TProjectFile queryEntity(String id);
}
