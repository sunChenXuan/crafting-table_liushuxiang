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
package vip.xiaonuo.biz.modular.customerinspectionfile.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.customerinspectionfile.entity.TCustomerInspectionFile;
import vip.xiaonuo.biz.modular.customerinspectionfile.mapper.TCustomerInspectionFileMapper;
import vip.xiaonuo.biz.modular.customerinspectionfile.param.TCustomerInspectionFileAddParam;
import vip.xiaonuo.biz.modular.customerinspectionfile.param.TCustomerInspectionFileEditParam;
import vip.xiaonuo.biz.modular.customerinspectionfile.param.TCustomerInspectionFileIdParam;
import vip.xiaonuo.biz.modular.customerinspectionfile.param.TCustomerInspectionFilePageParam;
import vip.xiaonuo.biz.modular.customerinspectionfile.service.TCustomerInspectionFileService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 客户巡检文件Service接口实现类
 *
 * @author scx
 * @date  2023/11/01 10:13
 **/
@Service
public class TCustomerInspectionFileServiceImpl extends ServiceImpl<TCustomerInspectionFileMapper, TCustomerInspectionFile> implements TCustomerInspectionFileService {

    @Override
    public Page<TCustomerInspectionFile> page(TCustomerInspectionFilePageParam tCustomerInspectionFilePageParam) {
        QueryWrapper<TCustomerInspectionFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tCustomerInspectionFilePageParam.getIdxCustomerInspectionId())) {
            queryWrapper.lambda().eq(TCustomerInspectionFile::getIdxCustomerInspectionId, tCustomerInspectionFilePageParam.getIdxCustomerInspectionId());
        }
        if(ObjectUtil.isAllNotEmpty(tCustomerInspectionFilePageParam.getSortField(), tCustomerInspectionFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tCustomerInspectionFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, tCustomerInspectionFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tCustomerInspectionFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TCustomerInspectionFile::getPkId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TCustomerInspectionFileAddParam tCustomerInspectionFileAddParam) {
        TCustomerInspectionFile tCustomerInspectionFile = BeanUtil.toBean(tCustomerInspectionFileAddParam, TCustomerInspectionFile.class);
        this.save(tCustomerInspectionFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TCustomerInspectionFileEditParam tCustomerInspectionFileEditParam) {
        TCustomerInspectionFile tCustomerInspectionFile = this.queryEntity(tCustomerInspectionFileEditParam.getPkId());
        BeanUtil.copyProperties(tCustomerInspectionFileEditParam, tCustomerInspectionFile);
        this.updateById(tCustomerInspectionFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TCustomerInspectionFileIdParam> tCustomerInspectionFileIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tCustomerInspectionFileIdParamList, TCustomerInspectionFileIdParam::getPkId));
    }

    @Override
    public TCustomerInspectionFile detail(TCustomerInspectionFileIdParam tCustomerInspectionFileIdParam) {
        return this.queryEntity(tCustomerInspectionFileIdParam.getPkId());
    }

    @Override
    public TCustomerInspectionFile queryEntity(String id) {
        TCustomerInspectionFile tCustomerInspectionFile = this.getById(id);
        if(ObjectUtil.isEmpty(tCustomerInspectionFile)) {
            throw new CommonException("客户巡检文件不存在，id值为：{}", id);
        }
        return tCustomerInspectionFile;
    }
}
