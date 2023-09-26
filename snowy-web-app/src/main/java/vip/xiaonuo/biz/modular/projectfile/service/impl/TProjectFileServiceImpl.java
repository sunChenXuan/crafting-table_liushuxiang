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
package vip.xiaonuo.biz.modular.projectfile.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.projectfile.entity.TProjectFile;
import vip.xiaonuo.biz.modular.projectfile.mapper.TProjectFileMapper;
import vip.xiaonuo.biz.modular.projectfile.param.TProjectFileAddParam;
import vip.xiaonuo.biz.modular.projectfile.param.TProjectFileEditParam;
import vip.xiaonuo.biz.modular.projectfile.param.TProjectFileIdParam;
import vip.xiaonuo.biz.modular.projectfile.param.TProjectFilePageParam;
import vip.xiaonuo.biz.modular.projectfile.service.TProjectFileService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.modular.file.service.DevFileService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目文件Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Service
public class TProjectFileServiceImpl extends ServiceImpl<TProjectFileMapper, TProjectFile> implements TProjectFileService {

    @Resource
    private DevFileService devFileService;

    @Override
    public Page<TProjectFile> page(TProjectFilePageParam tProjectFilePageParam) {
        QueryWrapper<TProjectFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tProjectFilePageParam.getIdxProjectId())) {
            queryWrapper.lambda().eq(TProjectFile::getIdxProjectId, tProjectFilePageParam.getIdxProjectId());
        }
        if(ObjectUtil.isNotEmpty(tProjectFilePageParam.getUkFileId())) {
            queryWrapper.lambda().eq(TProjectFile::getUkFileId, tProjectFilePageParam.getUkFileId());
        }
        if(ObjectUtil.isNotEmpty(tProjectFilePageParam.getProjectFileType())) {
            queryWrapper.lambda().eq(TProjectFile::getProjectFileType, tProjectFilePageParam.getProjectFileType());
        }
        if(ObjectUtil.isAllNotEmpty(tProjectFilePageParam.getSortField(), tProjectFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tProjectFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, tProjectFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tProjectFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TProjectFile::getPkId);
        }
        final Page<TProjectFile> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        for (TProjectFile pf : page.getRecords()) {
            pf.setDevFile(devFileService.queryEntity(pf.getUkFileId()));
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TProjectFileAddParam tProjectFileAddParam) {
        TProjectFile tProjectFile = BeanUtil.toBean(tProjectFileAddParam, TProjectFile.class);
        this.save(tProjectFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TProjectFileEditParam tProjectFileEditParam) {
        TProjectFile tProjectFile = this.queryEntity(tProjectFileEditParam.getPkId());
        BeanUtil.copyProperties(tProjectFileEditParam, tProjectFile);
        this.updateById(tProjectFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TProjectFileIdParam> tProjectFileIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tProjectFileIdParamList, TProjectFileIdParam::getPkId));
    }

    @Override
    public TProjectFile detail(TProjectFileIdParam tProjectFileIdParam) {
        return this.queryEntity(tProjectFileIdParam.getPkId());
    }

    @Override
    public TProjectFile queryEntity(String id) {
        TProjectFile tProjectFile = this.getById(id);
        if(ObjectUtil.isEmpty(tProjectFile)) {
            throw new CommonException("项目文件不存在，id值为：{}", id);
        }
        return tProjectFile;
    }
}
