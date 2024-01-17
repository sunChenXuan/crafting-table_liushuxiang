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
package vip.xiaonuo.biz.modular.customerinspection.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.customerinspection.entity.TCustomerInspection;
import vip.xiaonuo.biz.modular.customerinspection.mapper.TCustomerInspectionMapper;
import vip.xiaonuo.biz.modular.customerinspection.param.TCustomerInspectionAddParam;
import vip.xiaonuo.biz.modular.customerinspection.param.TCustomerInspectionEditParam;
import vip.xiaonuo.biz.modular.customerinspection.param.TCustomerInspectionIdParam;
import vip.xiaonuo.biz.modular.customerinspection.param.TCustomerInspectionPageParam;
import vip.xiaonuo.biz.modular.customerinspection.service.TCustomerInspectionService;
import vip.xiaonuo.biz.modular.project.service.TProjectService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户巡检Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:03
 **/
@Service
public class TCustomerInspectionServiceImpl extends ServiceImpl<TCustomerInspectionMapper, TCustomerInspection> implements TCustomerInspectionService {
    @Resource
    private TProjectService projectService;

    @Override
    public Page<TCustomerInspection> page(TCustomerInspectionPageParam tCustomerInspectionPageParam) {
        QueryWrapper<TCustomerInspection> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tCustomerInspectionPageParam.getInspectionName())) {
            queryWrapper.lambda().like(TCustomerInspection::getInspectionName, tCustomerInspectionPageParam.getInspectionName());
        }
        if(ObjectUtil.isNotEmpty(tCustomerInspectionPageParam.getInspectionType())) {
            queryWrapper.lambda().eq(TCustomerInspection::getInspectionType, tCustomerInspectionPageParam.getInspectionType());
        }
        if(ObjectUtil.isNotEmpty(tCustomerInspectionPageParam.getRemark())) {
            queryWrapper.lambda().like(TCustomerInspection::getRemark, tCustomerInspectionPageParam.getRemark());
        }
        if(ObjectUtil.isAllNotEmpty(tCustomerInspectionPageParam.getSortField(), tCustomerInspectionPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tCustomerInspectionPageParam.getSortOrder());
            queryWrapper.orderBy(true, tCustomerInspectionPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tCustomerInspectionPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TCustomerInspection::getPkId);
        }
        final Page<TCustomerInspection> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        for (TCustomerInspection ci : page.getRecords()) {
            if (ci.getInspectionName() != null && !ci.getInspectionName().isEmpty()){
                ci.setProjectName(projectService.getById(ci.getInspectionName()).getProjectName());
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TCustomerInspectionAddParam tCustomerInspectionAddParam) {
        TCustomerInspection tCustomerInspection = BeanUtil.toBean(tCustomerInspectionAddParam, TCustomerInspection.class);
        final SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        tCustomerInspection.setPhone(loginUser.getPhone());
        tCustomerInspection.setUserName(loginUser.getName());
        this.save(tCustomerInspection);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TCustomerInspectionEditParam tCustomerInspectionEditParam) {
        TCustomerInspection tCustomerInspection = this.queryEntity(tCustomerInspectionEditParam.getPkId());
        BeanUtil.copyProperties(tCustomerInspectionEditParam, tCustomerInspection);
        final SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        tCustomerInspection.setPhone(loginUser.getPhone());
        tCustomerInspection.setUserName(loginUser.getName());
        this.updateById(tCustomerInspection);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TCustomerInspectionIdParam> tCustomerInspectionIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tCustomerInspectionIdParamList, TCustomerInspectionIdParam::getPkId));
    }

    @Override
    public TCustomerInspection detail(TCustomerInspectionIdParam tCustomerInspectionIdParam) {
        return this.queryEntity(tCustomerInspectionIdParam.getPkId());
    }

    @Override
    public TCustomerInspection queryEntity(String id) {
        TCustomerInspection tCustomerInspection = this.getById(id);
        if(ObjectUtil.isEmpty(tCustomerInspection)) {
            throw new CommonException("客户巡检不存在，id值为：{}", id);
        }
        return tCustomerInspection;
    }
}
