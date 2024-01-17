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
package vip.xiaonuo.biz.modular.computerinspectionmanagement.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.computerinspection.entity.TComputerInspection;
import vip.xiaonuo.biz.modular.computerinspection.param.TComputerInspectionPageParam;
import vip.xiaonuo.biz.modular.computerinspectiontype.entity.TComputerInspectionType;
import vip.xiaonuo.biz.modular.computerinspectiontype.service.TComputerInspectionTypeService;
import vip.xiaonuo.biz.modular.project.service.TProjectService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.entity.TComputerInspectionManagement;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.mapper.TComputerInspectionManagementMapper;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.param.TComputerInspectionManagementAddParam;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.param.TComputerInspectionManagementEditParam;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.param.TComputerInspectionManagementIdParam;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.param.TComputerInspectionManagementPageParam;
import vip.xiaonuo.biz.modular.computerinspectionmanagement.service.TComputerInspectionManagementService;
import vip.xiaonuo.sys.modular.user.param.SysUserIdListParam;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 机房巡检管理Service接口实现类
 *
 * @author scx
 * @date  2024/01/17 08:46
 **/
@Service
public class TComputerInspectionManagementServiceImpl extends ServiceImpl<TComputerInspectionManagementMapper, TComputerInspectionManagement> implements TComputerInspectionManagementService {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private TProjectService projectService;
    @Resource
    private TComputerInspectionTypeService computerInspectionTypeService;

    @Override
    public Page<TComputerInspectionManagement> page(TComputerInspectionManagementPageParam tComputerInspectionManagementPageParam) {
        QueryWrapper<TComputerInspectionManagement> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tComputerInspectionManagementPageParam.getInspectionName())) {
            queryWrapper.lambda().eq(TComputerInspectionManagement::getInspectionName, tComputerInspectionManagementPageParam.getInspectionName());
        }
        if(ObjectUtil.isNotEmpty(tComputerInspectionManagementPageParam.getInspectionType())) {
            queryWrapper.lambda().eq(TComputerInspectionManagement::getInspectionType, tComputerInspectionManagementPageParam.getInspectionType());
        }
        if(ObjectUtil.isNotEmpty(tComputerInspectionManagementPageParam.getInspectionUsers())) {
            queryWrapper.lambda().like(TComputerInspectionManagement::getInspectionUsers, tComputerInspectionManagementPageParam.getInspectionUsers());
        }
        if(ObjectUtil.isAllNotEmpty(tComputerInspectionManagementPageParam.getSortField(), tComputerInspectionManagementPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tComputerInspectionManagementPageParam.getSortOrder());
            queryWrapper.orderBy(true, tComputerInspectionManagementPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tComputerInspectionManagementPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TComputerInspectionManagement::getPkId);
        }
        final Page<TComputerInspectionManagement> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        for (TComputerInspectionManagement cim : page.getRecords()) {
            if (cim.getInspectionName() != null && !cim.getInspectionName().isEmpty()){
                cim.setProjectName(projectService.getById(cim.getInspectionName()).getProjectName());
            }
            if (cim.getInspectionType() != null && !cim.getInspectionType().isEmpty()){
                final TComputerInspectionType byId = computerInspectionTypeService.getById(cim.getInspectionType());
                cim.setInspectionTypeName(byId == null ? null :byId.getInspectionTypeName());
            }
            SysUserIdListParam sysUserIdListParam = new SysUserIdListParam();
            if (cim.getInspectionUsers() != null && !cim.getInspectionUsers().isEmpty()){
                sysUserIdListParam.setIdList(JSONArray.parseArray(cim.getInspectionUsers(), String.class));
                cim.setUserList(sysUserService.getUserListByIdList(sysUserIdListParam));
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TComputerInspectionManagementAddParam tComputerInspectionManagementAddParam) {
        TComputerInspectionManagement tComputerInspectionManagement = BeanUtil.toBean(tComputerInspectionManagementAddParam, TComputerInspectionManagement.class);
        this.save(tComputerInspectionManagement);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TComputerInspectionManagementEditParam tComputerInspectionManagementEditParam) {
        TComputerInspectionManagement tComputerInspectionManagement = this.queryEntity(tComputerInspectionManagementEditParam.getPkId());
        BeanUtil.copyProperties(tComputerInspectionManagementEditParam, tComputerInspectionManagement);
        this.updateById(tComputerInspectionManagement);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TComputerInspectionManagementIdParam> tComputerInspectionManagementIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tComputerInspectionManagementIdParamList, TComputerInspectionManagementIdParam::getPkId));
    }

    @Override
    public TComputerInspectionManagement detail(TComputerInspectionManagementIdParam tComputerInspectionManagementIdParam) {
        return this.queryEntity(tComputerInspectionManagementIdParam.getPkId());
    }

    @Override
    public TComputerInspectionManagement queryEntity(String id) {
        TComputerInspectionManagement tComputerInspectionManagement = this.getById(id);
        if(ObjectUtil.isEmpty(tComputerInspectionManagement)) {
            throw new CommonException("机房巡检管理不存在，id值为：{}", id);
        }
        return tComputerInspectionManagement;
    }
}
