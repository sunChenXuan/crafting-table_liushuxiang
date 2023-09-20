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
package vip.xiaonuo.biz.modular.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.project.entity.TProject;
import vip.xiaonuo.biz.modular.project.mapper.TProjectMapper;
import vip.xiaonuo.biz.modular.project.param.TProjectAddParam;
import vip.xiaonuo.biz.modular.project.param.TProjectEditParam;
import vip.xiaonuo.biz.modular.project.param.TProjectIdParam;
import vip.xiaonuo.biz.modular.project.param.TProjectPageParam;
import vip.xiaonuo.biz.modular.project.service.TProjectService;

import java.util.List;

/**
 * 项目Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Service
public class TProjectServiceImpl extends ServiceImpl<TProjectMapper, TProject> implements TProjectService {

    @Override
    public Page<TProject> page(TProjectPageParam tProjectPageParam) {
        QueryWrapper<TProject> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tProjectPageParam.getProjectName())) {
            queryWrapper.lambda().like(TProject::getProjectName, tProjectPageParam.getProjectName());
        }
        if(ObjectUtil.isNotEmpty(tProjectPageParam.getProjectContacts())) {
            queryWrapper.lambda().like(TProject::getProjectContacts, tProjectPageParam.getProjectContacts());
        }
        if(ObjectUtil.isNotEmpty(tProjectPageParam.getProjectPhone())) {
            queryWrapper.lambda().like(TProject::getProjectPhone, tProjectPageParam.getProjectPhone());
        }
        if(ObjectUtil.isNotEmpty(tProjectPageParam.getProjectHeadUsers())) {
            queryWrapper.lambda().like(TProject::getProjectHeadUsers, tProjectPageParam.getProjectHeadUsers());
        }
        if(ObjectUtil.isNotEmpty(tProjectPageParam.getProjectUsers())) {
            queryWrapper.lambda().like(TProject::getProjectUsers, tProjectPageParam.getProjectUsers());
        }
        if(ObjectUtil.isNotEmpty(tProjectPageParam.getStartProjectStartTime()) && ObjectUtil.isNotEmpty(tProjectPageParam.getEndProjectStartTime())) {
            queryWrapper.lambda().between(TProject::getProjectStartTime, tProjectPageParam.getStartProjectStartTime(), tProjectPageParam.getEndProjectStartTime());
        }
        if(ObjectUtil.isNotEmpty(tProjectPageParam.getStartProjectEndTime()) && ObjectUtil.isNotEmpty(tProjectPageParam.getEndProjectEndTime())) {
            queryWrapper.lambda().between(TProject::getProjectEndTime, tProjectPageParam.getStartProjectEndTime(), tProjectPageParam.getEndProjectEndTime());
        }
        if(ObjectUtil.isAllNotEmpty(tProjectPageParam.getSortField(), tProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, tProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tProjectPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TProject::getPkId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TProjectAddParam tProjectAddParam) {
        TProject tProject = BeanUtil.toBean(tProjectAddParam, TProject.class);
        this.save(tProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TProjectEditParam tProjectEditParam) {
        TProject tProject = this.queryEntity(tProjectEditParam.getPkId());
        BeanUtil.copyProperties(tProjectEditParam, tProject);
        this.updateById(tProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TProjectIdParam> tProjectIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tProjectIdParamList, TProjectIdParam::getPkId));
    }

    @Override
    public TProject detail(TProjectIdParam tProjectIdParam) {
        return this.queryEntity(tProjectIdParam.getPkId());
    }

    @Override
    public TProject queryEntity(String id) {
        TProject tProject = this.getById(id);
        if(ObjectUtil.isEmpty(tProject)) {
            throw new CommonException("项目不存在，id值为：{}", id);
        }
        return tProject;
    }
}
