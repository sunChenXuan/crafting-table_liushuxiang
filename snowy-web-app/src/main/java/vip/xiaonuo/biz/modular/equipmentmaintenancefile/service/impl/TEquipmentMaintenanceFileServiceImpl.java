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
package vip.xiaonuo.biz.modular.equipmentmaintenancefile.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.entity.TEquipmentMaintenanceFile;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.mapper.TEquipmentMaintenanceFileMapper;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.param.TEquipmentMaintenanceFileAddParam;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.param.TEquipmentMaintenanceFileEditParam;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.param.TEquipmentMaintenanceFileIdParam;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.param.TEquipmentMaintenanceFilePageParam;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.service.TEquipmentMaintenanceFileService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 设备维保文件Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Service
public class TEquipmentMaintenanceFileServiceImpl extends ServiceImpl<TEquipmentMaintenanceFileMapper, TEquipmentMaintenanceFile> implements TEquipmentMaintenanceFileService {

    @Override
    public Page<TEquipmentMaintenanceFile> page(TEquipmentMaintenanceFilePageParam tEquipmentMaintenanceFilePageParam) {
        QueryWrapper<TEquipmentMaintenanceFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tEquipmentMaintenanceFilePageParam.getIdxEquipmentMaintenanceId())) {
            queryWrapper.lambda().eq(TEquipmentMaintenanceFile::getIdxEquipmentMaintenanceId, tEquipmentMaintenanceFilePageParam.getIdxEquipmentMaintenanceId());
        }
        if(ObjectUtil.isNotEmpty(tEquipmentMaintenanceFilePageParam.getUkFileId())) {
            queryWrapper.lambda().eq(TEquipmentMaintenanceFile::getUkFileId, tEquipmentMaintenanceFilePageParam.getUkFileId());
        }
        if(ObjectUtil.isAllNotEmpty(tEquipmentMaintenanceFilePageParam.getSortField(), tEquipmentMaintenanceFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tEquipmentMaintenanceFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, tEquipmentMaintenanceFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tEquipmentMaintenanceFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TEquipmentMaintenanceFile::getPkId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TEquipmentMaintenanceFileAddParam tEquipmentMaintenanceFileAddParam) {
        TEquipmentMaintenanceFile tEquipmentMaintenanceFile = BeanUtil.toBean(tEquipmentMaintenanceFileAddParam, TEquipmentMaintenanceFile.class);
        this.save(tEquipmentMaintenanceFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TEquipmentMaintenanceFileEditParam tEquipmentMaintenanceFileEditParam) {
        TEquipmentMaintenanceFile tEquipmentMaintenanceFile = this.queryEntity(tEquipmentMaintenanceFileEditParam.getPkId());
        BeanUtil.copyProperties(tEquipmentMaintenanceFileEditParam, tEquipmentMaintenanceFile);
        this.updateById(tEquipmentMaintenanceFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TEquipmentMaintenanceFileIdParam> tEquipmentMaintenanceFileIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tEquipmentMaintenanceFileIdParamList, TEquipmentMaintenanceFileIdParam::getPkId));
    }

    @Override
    public TEquipmentMaintenanceFile detail(TEquipmentMaintenanceFileIdParam tEquipmentMaintenanceFileIdParam) {
        return this.queryEntity(tEquipmentMaintenanceFileIdParam.getPkId());
    }

    @Override
    public TEquipmentMaintenanceFile queryEntity(String id) {
        TEquipmentMaintenanceFile tEquipmentMaintenanceFile = this.getById(id);
        if(ObjectUtil.isEmpty(tEquipmentMaintenanceFile)) {
            throw new CommonException("设备维保文件不存在，id值为：{}", id);
        }
        return tEquipmentMaintenanceFile;
    }
}
