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
package vip.xiaonuo.biz.modular.computerinspectionfile.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.computerinspectionfile.entity.TComputerInspectionFile;
import vip.xiaonuo.biz.modular.computerinspectionfile.mapper.TComputerInspectionFileMapper;
import vip.xiaonuo.biz.modular.computerinspectionfile.param.TComputerInspectionFileAddParam;
import vip.xiaonuo.biz.modular.computerinspectionfile.param.TComputerInspectionFileEditParam;
import vip.xiaonuo.biz.modular.computerinspectionfile.param.TComputerInspectionFileIdParam;
import vip.xiaonuo.biz.modular.computerinspectionfile.param.TComputerInspectionFilePageParam;
import vip.xiaonuo.biz.modular.computerinspectionfile.service.TComputerInspectionFileService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 机房巡检文件Service接口实现类
 *
 * @author scx
 * @date  2024/01/19 09:56
 **/
@Service
public class TComputerInspectionFileServiceImpl extends ServiceImpl<TComputerInspectionFileMapper, TComputerInspectionFile> implements TComputerInspectionFileService {

    @Override
    public Page<TComputerInspectionFile> page(TComputerInspectionFilePageParam tComputerInspectionFilePageParam) {
        QueryWrapper<TComputerInspectionFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tComputerInspectionFilePageParam.getIdxCustomerInspectionId())) {
            queryWrapper.lambda().eq(TComputerInspectionFile::getIdxCustomerInspectionId, tComputerInspectionFilePageParam.getIdxCustomerInspectionId());
        }
        if(ObjectUtil.isAllNotEmpty(tComputerInspectionFilePageParam.getSortField(), tComputerInspectionFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tComputerInspectionFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, tComputerInspectionFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tComputerInspectionFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TComputerInspectionFile::getPkId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TComputerInspectionFileAddParam tComputerInspectionFileAddParam) {
        TComputerInspectionFile tComputerInspectionFile = BeanUtil.toBean(tComputerInspectionFileAddParam, TComputerInspectionFile.class);
        this.save(tComputerInspectionFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TComputerInspectionFileEditParam tComputerInspectionFileEditParam) {
        TComputerInspectionFile tComputerInspectionFile = this.queryEntity(tComputerInspectionFileEditParam.getPkId());
        BeanUtil.copyProperties(tComputerInspectionFileEditParam, tComputerInspectionFile);
        this.updateById(tComputerInspectionFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TComputerInspectionFileIdParam> tComputerInspectionFileIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tComputerInspectionFileIdParamList, TComputerInspectionFileIdParam::getPkId));
    }

    @Override
    public TComputerInspectionFile detail(TComputerInspectionFileIdParam tComputerInspectionFileIdParam) {
        return this.queryEntity(tComputerInspectionFileIdParam.getPkId());
    }

    @Override
    public TComputerInspectionFile queryEntity(String id) {
        TComputerInspectionFile tComputerInspectionFile = this.getById(id);
        if(ObjectUtil.isEmpty(tComputerInspectionFile)) {
            throw new CommonException("机房巡检文件不存在，id值为：{}", id);
        }
        return tComputerInspectionFile;
    }
}
