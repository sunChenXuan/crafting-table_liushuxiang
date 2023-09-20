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
package vip.xiaonuo.biz.modular.fixedasset.service.impl;

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
import vip.xiaonuo.biz.modular.fixedasset.entity.TFixedAsset;
import vip.xiaonuo.biz.modular.fixedasset.mapper.TFixedAssetMapper;
import vip.xiaonuo.biz.modular.fixedasset.param.TFixedAssetAddParam;
import vip.xiaonuo.biz.modular.fixedasset.param.TFixedAssetEditParam;
import vip.xiaonuo.biz.modular.fixedasset.param.TFixedAssetIdParam;
import vip.xiaonuo.biz.modular.fixedasset.param.TFixedAssetPageParam;
import vip.xiaonuo.biz.modular.fixedasset.service.TFixedAssetService;

import java.util.List;

/**
 * 固定资产Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Service
public class TFixedAssetServiceImpl extends ServiceImpl<TFixedAssetMapper, TFixedAsset> implements TFixedAssetService {

    @Override
    public Page<TFixedAsset> page(TFixedAssetPageParam tFixedAssetPageParam) {
        QueryWrapper<TFixedAsset> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getSerialNumber())) {
            queryWrapper.lambda().eq(TFixedAsset::getSerialNumber, tFixedAssetPageParam.getSerialNumber());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetType())) {
            queryWrapper.lambda().eq(TFixedAsset::getFixedAssetType, tFixedAssetPageParam.getFixedAssetType());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetCpu())) {
            queryWrapper.lambda().eq(TFixedAsset::getFixedAssetCpu, tFixedAssetPageParam.getFixedAssetCpu());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetMemory())) {
            queryWrapper.lambda().eq(TFixedAsset::getFixedAssetMemory, tFixedAssetPageParam.getFixedAssetMemory());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetSsd())) {
            queryWrapper.lambda().eq(TFixedAsset::getFixedAssetSsd, tFixedAssetPageParam.getFixedAssetSsd());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetDisk())) {
            queryWrapper.lambda().eq(TFixedAsset::getFixedAssetDisk, tFixedAssetPageParam.getFixedAssetDisk());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetGpu())) {
            queryWrapper.lambda().eq(TFixedAsset::getFixedAssetGpu, tFixedAssetPageParam.getFixedAssetGpu());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetAccessory())) {
            queryWrapper.lambda().like(TFixedAsset::getFixedAssetAccessory, tFixedAssetPageParam.getFixedAssetAccessory());
        }
        if(ObjectUtil.isAllNotEmpty(tFixedAssetPageParam.getSortField(), tFixedAssetPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tFixedAssetPageParam.getSortOrder());
            queryWrapper.orderBy(true, tFixedAssetPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tFixedAssetPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TFixedAsset::getPkId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TFixedAssetAddParam tFixedAssetAddParam) {
        TFixedAsset tFixedAsset = BeanUtil.toBean(tFixedAssetAddParam, TFixedAsset.class);
        this.save(tFixedAsset);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TFixedAssetEditParam tFixedAssetEditParam) {
        TFixedAsset tFixedAsset = this.queryEntity(tFixedAssetEditParam.getPkId());
        BeanUtil.copyProperties(tFixedAssetEditParam, tFixedAsset);
        this.updateById(tFixedAsset);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TFixedAssetIdParam> tFixedAssetIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tFixedAssetIdParamList, TFixedAssetIdParam::getPkId));
    }

    @Override
    public TFixedAsset detail(TFixedAssetIdParam tFixedAssetIdParam) {
        return this.queryEntity(tFixedAssetIdParam.getPkId());
    }

    @Override
    public TFixedAsset queryEntity(String id) {
        TFixedAsset tFixedAsset = this.getById(id);
        if(ObjectUtil.isEmpty(tFixedAsset)) {
            throw new CommonException("固定资产不存在，id值为：{}", id);
        }
        return tFixedAsset;
    }
}
