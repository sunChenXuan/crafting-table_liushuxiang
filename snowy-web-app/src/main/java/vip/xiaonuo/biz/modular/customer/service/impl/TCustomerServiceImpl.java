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
package vip.xiaonuo.biz.modular.customer.service.impl;

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
import vip.xiaonuo.biz.modular.customer.entity.TCustomer;
import vip.xiaonuo.biz.modular.customer.mapper.TCustomerMapper;
import vip.xiaonuo.biz.modular.customer.param.TCustomerAddParam;
import vip.xiaonuo.biz.modular.customer.param.TCustomerEditParam;
import vip.xiaonuo.biz.modular.customer.param.TCustomerIdParam;
import vip.xiaonuo.biz.modular.customer.param.TCustomerPageParam;
import vip.xiaonuo.biz.modular.customer.service.TCustomerService;

import java.util.List;

/**
 * 客户Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:03
 **/
@Service
public class TCustomerServiceImpl extends ServiceImpl<TCustomerMapper, TCustomer> implements TCustomerService {

    @Override
    public Page<TCustomer> page(TCustomerPageParam tCustomerPageParam) {
        QueryWrapper<TCustomer> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tCustomerPageParam.getCustomerName())) {
            queryWrapper.lambda().like(TCustomer::getCustomerName, tCustomerPageParam.getCustomerName());
        }
        if(ObjectUtil.isNotEmpty(tCustomerPageParam.getCustomerType())) {
            queryWrapper.lambda().eq(TCustomer::getCustomerType, tCustomerPageParam.getCustomerType());
        }
        if(ObjectUtil.isNotEmpty(tCustomerPageParam.getCustomerPhone())) {
            queryWrapper.lambda().like(TCustomer::getCustomerPhone, tCustomerPageParam.getCustomerPhone());
        }
        if(ObjectUtil.isNotEmpty(tCustomerPageParam.getCustomerAddress())) {
            queryWrapper.lambda().like(TCustomer::getCustomerAddress, tCustomerPageParam.getCustomerAddress());
        }
        if(ObjectUtil.isNotEmpty(tCustomerPageParam.getCustomerProfile())) {
            queryWrapper.lambda().like(TCustomer::getCustomerProfile, tCustomerPageParam.getCustomerProfile());
        }
        if(ObjectUtil.isAllNotEmpty(tCustomerPageParam.getSortField(), tCustomerPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tCustomerPageParam.getSortOrder());
            queryWrapper.orderBy(true, tCustomerPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tCustomerPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TCustomer::getPkId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TCustomerAddParam tCustomerAddParam) {
        TCustomer tCustomer = BeanUtil.toBean(tCustomerAddParam, TCustomer.class);
        this.save(tCustomer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TCustomerEditParam tCustomerEditParam) {
        TCustomer tCustomer = this.queryEntity(tCustomerEditParam.getPkId());
        BeanUtil.copyProperties(tCustomerEditParam, tCustomer);
        this.updateById(tCustomer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TCustomerIdParam> tCustomerIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tCustomerIdParamList, TCustomerIdParam::getPkId));
    }

    @Override
    public TCustomer detail(TCustomerIdParam tCustomerIdParam) {
        return this.queryEntity(tCustomerIdParam.getPkId());
    }

    @Override
    public TCustomer queryEntity(String id) {
        TCustomer tCustomer = this.getById(id);
        if(ObjectUtil.isEmpty(tCustomer)) {
            throw new CommonException("客户不存在，id值为：{}", id);
        }
        return tCustomer;
    }
}
