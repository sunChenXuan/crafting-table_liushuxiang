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
package vip.xiaonuo.biz.modular.fixedassetfile.service.impl;

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
import vip.xiaonuo.biz.modular.fixedassetfile.entity.TFixedAssetFile;
import vip.xiaonuo.biz.modular.fixedassetfile.mapper.TFixedAssetFileMapper;
import vip.xiaonuo.biz.modular.fixedassetfile.param.TFixedAssetFileAddParam;
import vip.xiaonuo.biz.modular.fixedassetfile.param.TFixedAssetFileEditParam;
import vip.xiaonuo.biz.modular.fixedassetfile.param.TFixedAssetFileIdParam;
import vip.xiaonuo.biz.modular.fixedassetfile.param.TFixedAssetFilePageParam;
import vip.xiaonuo.biz.modular.fixedassetfile.service.TFixedAssetFileService;

import java.util.List;

/**
 * 固定资产文件Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Service
public class TFixedAssetFileServiceImpl extends ServiceImpl<TFixedAssetFileMapper, TFixedAssetFile> implements TFixedAssetFileService {

    @Override
    public Page<TFixedAssetFile> page(TFixedAssetFilePageParam tFixedAssetFilePageParam) {
        QueryWrapper<TFixedAssetFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tFixedAssetFilePageParam.getIdxFixedAssetId())) {
            queryWrapper.lambda().eq(TFixedAssetFile::getIdxFixedAssetId, tFixedAssetFilePageParam.getIdxFixedAssetId());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetFilePageParam.getUkFileId())) {
            queryWrapper.lambda().eq(TFixedAssetFile::getUkFileId, tFixedAssetFilePageParam.getUkFileId());
        }
        if(ObjectUtil.isAllNotEmpty(tFixedAssetFilePageParam.getSortField(), tFixedAssetFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tFixedAssetFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, tFixedAssetFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tFixedAssetFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TFixedAssetFile::getPkId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TFixedAssetFileAddParam tFixedAssetFileAddParam) {
        TFixedAssetFile tFixedAssetFile = BeanUtil.toBean(tFixedAssetFileAddParam, TFixedAssetFile.class);
        this.save(tFixedAssetFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TFixedAssetFileEditParam tFixedAssetFileEditParam) {
        TFixedAssetFile tFixedAssetFile = this.queryEntity(tFixedAssetFileEditParam.getPkId());
        BeanUtil.copyProperties(tFixedAssetFileEditParam, tFixedAssetFile);
        this.updateById(tFixedAssetFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TFixedAssetFileIdParam> tFixedAssetFileIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tFixedAssetFileIdParamList, TFixedAssetFileIdParam::getPkId));
    }

    @Override
    public TFixedAssetFile detail(TFixedAssetFileIdParam tFixedAssetFileIdParam) {
        return this.queryEntity(tFixedAssetFileIdParam.getPkId());
    }

    @Override
    public TFixedAssetFile queryEntity(String id) {
        TFixedAssetFile tFixedAssetFile = this.getById(id);
        if(ObjectUtil.isEmpty(tFixedAssetFile)) {
            throw new CommonException("固定资产文件不存在，id值为：{}", id);
        }
        return tFixedAssetFile;
    }
}
