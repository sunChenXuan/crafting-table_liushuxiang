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
package vip.xiaonuo.biz.modular.computerinspectiontype.service.impl;

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
import vip.xiaonuo.biz.modular.computerinspectiontype.entity.TComputerInspectionType;
import vip.xiaonuo.biz.modular.computerinspectiontype.mapper.TComputerInspectionTypeMapper;
import vip.xiaonuo.biz.modular.computerinspectiontype.param.TComputerInspectionTypeAddParam;
import vip.xiaonuo.biz.modular.computerinspectiontype.param.TComputerInspectionTypeEditParam;
import vip.xiaonuo.biz.modular.computerinspectiontype.param.TComputerInspectionTypeIdParam;
import vip.xiaonuo.biz.modular.computerinspectiontype.param.TComputerInspectionTypePageParam;
import vip.xiaonuo.biz.modular.computerinspectiontype.service.TComputerInspectionTypeService;

import java.util.List;

/**
 * 机房巡检类型Service接口实现类
 *
 * @author scx
 * @date  2023/11/01 10:11
 **/
@Service
public class TComputerInspectionTypeServiceImpl extends ServiceImpl<TComputerInspectionTypeMapper, TComputerInspectionType> implements TComputerInspectionTypeService {

    @Override
    public Page<TComputerInspectionType> page(TComputerInspectionTypePageParam tComputerInspectionTypePageParam) {
        QueryWrapper<TComputerInspectionType> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tComputerInspectionTypePageParam.getInspectionTypeName())) {
            queryWrapper.lambda().eq(TComputerInspectionType::getInspectionTypeName, tComputerInspectionTypePageParam.getInspectionTypeName());
        }
        if(ObjectUtil.isNotEmpty(tComputerInspectionTypePageParam.getInspectionDetail())) {
            queryWrapper.lambda().like(TComputerInspectionType::getInspectionDetail, tComputerInspectionTypePageParam.getInspectionDetail());
        }
        if(ObjectUtil.isAllNotEmpty(tComputerInspectionTypePageParam.getSortField(), tComputerInspectionTypePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tComputerInspectionTypePageParam.getSortOrder());
            queryWrapper.orderBy(true, tComputerInspectionTypePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tComputerInspectionTypePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TComputerInspectionType::getPkId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TComputerInspectionTypeAddParam tComputerInspectionTypeAddParam) {
        TComputerInspectionType tComputerInspectionType = BeanUtil.toBean(tComputerInspectionTypeAddParam, TComputerInspectionType.class);
        this.save(tComputerInspectionType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TComputerInspectionTypeEditParam tComputerInspectionTypeEditParam) {
        TComputerInspectionType tComputerInspectionType = this.queryEntity(tComputerInspectionTypeEditParam.getPkId());
        BeanUtil.copyProperties(tComputerInspectionTypeEditParam, tComputerInspectionType);
        this.updateById(tComputerInspectionType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TComputerInspectionTypeIdParam> tComputerInspectionTypeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tComputerInspectionTypeIdParamList, TComputerInspectionTypeIdParam::getPkId));
    }

    @Override
    public TComputerInspectionType detail(TComputerInspectionTypeIdParam tComputerInspectionTypeIdParam) {
        return this.queryEntity(tComputerInspectionTypeIdParam.getPkId());
    }

    @Override
    public TComputerInspectionType queryEntity(String id) {
        TComputerInspectionType tComputerInspectionType = this.getById(id);
        if(ObjectUtil.isEmpty(tComputerInspectionType)) {
            throw new CommonException("机房巡检类型不存在，id值为：{}", id);
        }
        return tComputerInspectionType;
    }
}
