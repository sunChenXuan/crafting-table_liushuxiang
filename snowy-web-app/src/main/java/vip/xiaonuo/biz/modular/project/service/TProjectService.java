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
package vip.xiaonuo.biz.modular.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.project.entity.TProject;
import vip.xiaonuo.biz.modular.project.param.TProjectAddParam;
import vip.xiaonuo.biz.modular.project.param.TProjectEditParam;
import vip.xiaonuo.biz.modular.project.param.TProjectIdParam;
import vip.xiaonuo.biz.modular.project.param.TProjectPageParam;

import java.util.List;

/**
 * 项目Service接口
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
public interface TProjectService extends IService<TProject> {

    /**
     * 获取项目分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    Page<TProject> page(TProjectPageParam tProjectPageParam);

    /**
     * 添加项目
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void add(TProjectAddParam tProjectAddParam);

    /**
     * 编辑项目
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void edit(TProjectEditParam tProjectEditParam);

    /**
     * 删除项目
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void delete(List<TProjectIdParam> tProjectIdParamList);

    /**
     * 获取项目详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    TProject detail(TProjectIdParam tProjectIdParam);

    /**
     * 获取项目详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     **/
    TProject queryEntity(String id);
}
