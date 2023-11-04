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
package vip.xiaonuo.biz.modular.computerinspection.service.impl;

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
import vip.xiaonuo.biz.modular.computerinspection.mapper.TComputerInspectionMapper;
import vip.xiaonuo.biz.modular.computerinspection.param.TComputerInspectionAddParam;
import vip.xiaonuo.biz.modular.computerinspection.param.TComputerInspectionEditParam;
import vip.xiaonuo.biz.modular.computerinspection.param.TComputerInspectionIdParam;
import vip.xiaonuo.biz.modular.computerinspection.param.TComputerInspectionPageParam;
import vip.xiaonuo.biz.modular.computerinspection.service.TComputerInspectionService;
import vip.xiaonuo.biz.modular.project.service.TProjectService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.user.param.SysUserIdListParam;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 机房巡检Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:03
 **/
@Service
public class TComputerInspectionServiceImpl extends ServiceImpl<TComputerInspectionMapper, TComputerInspection> implements TComputerInspectionService {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private TProjectService projectService;

    @Override
    public Page<TComputerInspection> page(TComputerInspectionPageParam tComputerInspectionPageParam) {
        QueryWrapper<TComputerInspection> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tComputerInspectionPageParam.getInspectionName())) {
            queryWrapper.lambda().eq(TComputerInspection::getInspectionName, tComputerInspectionPageParam.getInspectionName());
        }
        if(ObjectUtil.isNotEmpty(tComputerInspectionPageParam.getInspectionUsers())) {
            queryWrapper.lambda().like(TComputerInspection::getInspectionUsers, tComputerInspectionPageParam.getInspectionUsers());
        }
        if(ObjectUtil.isAllNotEmpty(tComputerInspectionPageParam.getSortField(), tComputerInspectionPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tComputerInspectionPageParam.getSortOrder());
            queryWrapper.orderBy(true, tComputerInspectionPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tComputerInspectionPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TComputerInspection::getPkId);
        }
        final Page<TComputerInspection> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        for (TComputerInspection ci : page.getRecords()) {
            if (ci.getInspectionName() != null && !ci.getInspectionName().isEmpty()){
                ci.setProjectName(projectService.getById(ci.getInspectionName()).getProjectName());
            }
            SysUserIdListParam sysUserIdListParam = new SysUserIdListParam();
            if (ci.getInspectionUsers() != null && !ci.getInspectionUsers().isEmpty()){
                sysUserIdListParam.setIdList(JSONArray.parseArray(ci.getInspectionUsers(), String.class));
                ci.setUserList(sysUserService.getUserListByIdList(sysUserIdListParam));
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TComputerInspectionAddParam tComputerInspectionAddParam) {
        TComputerInspection tComputerInspection = BeanUtil.toBean(tComputerInspectionAddParam, TComputerInspection.class);
        this.save(tComputerInspection);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TComputerInspectionEditParam tComputerInspectionEditParam) {
        TComputerInspection tComputerInspection = this.queryEntity(tComputerInspectionEditParam.getPkId());
        BeanUtil.copyProperties(tComputerInspectionEditParam, tComputerInspection);
        this.updateById(tComputerInspection);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TComputerInspectionIdParam> tComputerInspectionIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tComputerInspectionIdParamList, TComputerInspectionIdParam::getPkId));
    }

    @Override
    public TComputerInspection detail(TComputerInspectionIdParam tComputerInspectionIdParam) {
        return this.queryEntity(tComputerInspectionIdParam.getPkId());
    }

    @Override
    public TComputerInspection queryEntity(String id) {
        TComputerInspection tComputerInspection = this.getById(id);
        if(ObjectUtil.isEmpty(tComputerInspection)) {
            throw new CommonException("机房巡检不存在，id值为：{}", id);
        }
        return tComputerInspection;
    }
}
