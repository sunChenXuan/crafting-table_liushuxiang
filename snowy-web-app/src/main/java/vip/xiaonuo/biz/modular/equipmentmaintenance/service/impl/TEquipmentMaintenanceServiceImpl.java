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
package vip.xiaonuo.biz.modular.equipmentmaintenance.service.impl;

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
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.biz.modular.equipmentmaintenance.entity.TEquipmentMaintenance;
import vip.xiaonuo.biz.modular.equipmentmaintenance.mapper.TEquipmentMaintenanceMapper;
import vip.xiaonuo.biz.modular.equipmentmaintenance.param.TEquipmentMaintenanceAddParam;
import vip.xiaonuo.biz.modular.equipmentmaintenance.param.TEquipmentMaintenanceEditParam;
import vip.xiaonuo.biz.modular.equipmentmaintenance.param.TEquipmentMaintenanceIdParam;
import vip.xiaonuo.biz.modular.equipmentmaintenance.param.TEquipmentMaintenancePageParam;
import vip.xiaonuo.biz.modular.equipmentmaintenance.service.TEquipmentMaintenanceService;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.entity.TEquipmentMaintenanceFile;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.param.TEquipmentMaintenanceFileIdParam;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.service.TEquipmentMaintenanceFileService;
import vip.xiaonuo.biz.modular.project.entity.TProject;
import vip.xiaonuo.biz.modular.project.service.TProjectService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.modular.file.enums.DevFileEngineTypeEnum;
import vip.xiaonuo.dev.modular.file.service.DevFileService;
import vip.xiaonuo.sys.modular.user.param.SysUserIdListParam;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 设备维保Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Service
public class TEquipmentMaintenanceServiceImpl extends ServiceImpl<TEquipmentMaintenanceMapper, TEquipmentMaintenance> implements TEquipmentMaintenanceService {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private TProjectService tProjectService;
    @Resource
    private TEquipmentMaintenanceFileService tEquipmentMaintenanceFileService;

    @Resource
    private DevFileService devFileService;

    @Override
    public Page<TEquipmentMaintenance> page(TEquipmentMaintenancePageParam tEquipmentMaintenancePageParam) {
        QueryWrapper<TEquipmentMaintenance> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tEquipmentMaintenancePageParam.getIdxProjectId())) {
            queryWrapper.lambda().eq(TEquipmentMaintenance::getIdxProjectId, tEquipmentMaintenancePageParam.getIdxProjectId());
        }
        if(ObjectUtil.isNotEmpty(tEquipmentMaintenancePageParam.getEquipmentName())) {
            queryWrapper.lambda().like(TEquipmentMaintenance::getEquipmentName, tEquipmentMaintenancePageParam.getEquipmentName());
        }
        if(ObjectUtil.isNotEmpty(tEquipmentMaintenancePageParam.getEquipmentType())) {
            queryWrapper.lambda().eq(TEquipmentMaintenance::getEquipmentType, tEquipmentMaintenancePageParam.getEquipmentType());
        }
        if(ObjectUtil.isNotEmpty(tEquipmentMaintenancePageParam.getEquipmentManufacturer())) {
            queryWrapper.lambda().eq(TEquipmentMaintenance::getEquipmentManufacturer, tEquipmentMaintenancePageParam.getEquipmentManufacturer());
        }
        if(ObjectUtil.isNotEmpty(tEquipmentMaintenancePageParam.getSerialNumber())) {
            queryWrapper.lambda().eq(TEquipmentMaintenance::getSerialNumber, tEquipmentMaintenancePageParam.getSerialNumber());
        }
        if(ObjectUtil.isNotEmpty(tEquipmentMaintenancePageParam.getStartAuthorizationStartTime()) && ObjectUtil.isNotEmpty(tEquipmentMaintenancePageParam.getEndAuthorizationStartTime())) {
            queryWrapper.lambda().between(TEquipmentMaintenance::getAuthorizationStartTime, tEquipmentMaintenancePageParam.getStartAuthorizationStartTime(), tEquipmentMaintenancePageParam.getEndAuthorizationStartTime());
        }
        if(ObjectUtil.isNotEmpty(tEquipmentMaintenancePageParam.getStartAuthorizationEndTime()) && ObjectUtil.isNotEmpty(tEquipmentMaintenancePageParam.getEndAuthorizationEndTime())) {
            queryWrapper.lambda().between(TEquipmentMaintenance::getAuthorizationEndTime, tEquipmentMaintenancePageParam.getStartAuthorizationEndTime(), tEquipmentMaintenancePageParam.getEndAuthorizationEndTime());
        }
        if(ObjectUtil.isNotEmpty(tEquipmentMaintenancePageParam.getEquipmentSysUsers())) {
            queryWrapper.lambda().eq(TEquipmentMaintenance::getEquipmentSysUsers, tEquipmentMaintenancePageParam.getEquipmentSysUsers());
        }
        if(ObjectUtil.isNotEmpty(tEquipmentMaintenancePageParam.getEquipmentUsers())) {
            queryWrapper.lambda().eq(TEquipmentMaintenance::getEquipmentUsers, tEquipmentMaintenancePageParam.getEquipmentUsers());
        }
        if(ObjectUtil.isAllNotEmpty(tEquipmentMaintenancePageParam.getSortField(), tEquipmentMaintenancePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tEquipmentMaintenancePageParam.getSortOrder());
            queryWrapper.orderBy(true, tEquipmentMaintenancePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tEquipmentMaintenancePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TEquipmentMaintenance::getPkId);
        }
        final Page<TEquipmentMaintenance> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        // 填充补充字段
        for (TEquipmentMaintenance em : page.getRecords()) {
            final TProject tProject = tProjectService.queryEntity(em.getIdxProjectId());
            em.setProjectName(tProject.getProjectName());
            em.setProjectContacts(tProject.getProjectContacts());
            em.setProjectPhone(tProject.getProjectPhone());
            em.setEquipmentSysUsers(tProject.getProjectHeadUsers());
            SysUserIdListParam sysUserIdListParam = new SysUserIdListParam();
            if (em.getEquipmentUsers() != null && !em.getEquipmentUsers().isEmpty()){
                sysUserIdListParam.setIdList(JSONArray.parseArray(em.getEquipmentUsers(), String.class));
                em.setEquipmentUserList(sysUserService.getUserListByIdList(sysUserIdListParam));
            }
            if (em.getEquipmentSysUsers() != null && !em.getEquipmentSysUsers().isEmpty()){
                sysUserIdListParam.setIdList(JSONArray.parseArray(em.getEquipmentSysUsers(), String.class));
                em.setEquipmentSysUserList(sysUserService.getUserListByIdList(sysUserIdListParam));
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TEquipmentMaintenanceAddParam tEquipmentMaintenanceAddParam) {
        TEquipmentMaintenance tEquipmentMaintenance = BeanUtil.toBean(tEquipmentMaintenanceAddParam, TEquipmentMaintenance.class);
        this.save(tEquipmentMaintenance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TEquipmentMaintenanceAddParam tEquipmentMaintenanceAddParam, MultipartFile file) {
        TEquipmentMaintenance tEquipmentMaintenance = BeanUtil.toBean(tEquipmentMaintenanceAddParam, TEquipmentMaintenance.class);
        this.save(tEquipmentMaintenance);

        final TEquipmentMaintenanceFile tEquipmentMaintenanceFile = new TEquipmentMaintenanceFile();
        tEquipmentMaintenanceFile.setIdxEquipmentMaintenanceId(tEquipmentMaintenance.getPkId());
        tEquipmentMaintenanceFile.setUkFileId(devFileService.uploadReturnId(DevFileEngineTypeEnum.MINIO.getValue(), file));
        tEquipmentMaintenanceFileService.save(tEquipmentMaintenanceFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TEquipmentMaintenanceEditParam tEquipmentMaintenanceEditParam) {
        TEquipmentMaintenance tEquipmentMaintenance = this.queryEntity(tEquipmentMaintenanceEditParam.getPkId());
        BeanUtil.copyProperties(tEquipmentMaintenanceEditParam, tEquipmentMaintenance);
        this.updateById(tEquipmentMaintenance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TEquipmentMaintenanceIdParam> tEquipmentMaintenanceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tEquipmentMaintenanceIdParamList, TEquipmentMaintenanceIdParam::getPkId));

        {
            // 删除文件
            QueryWrapper<TEquipmentMaintenanceFile> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(
                    TEquipmentMaintenanceFile::getIdxEquipmentMaintenanceId, tEquipmentMaintenanceIdParamList.stream().map(TEquipmentMaintenanceIdParam::getPkId).collect(Collectors.toList())
            );
            final List<TEquipmentMaintenanceFile> list = tEquipmentMaintenanceFileService.list(queryWrapper);
            if (list != null && !list.isEmpty()){
                tEquipmentMaintenanceFileService.delete(list.stream().map(i -> {
                    TEquipmentMaintenanceFileIdParam param = new TEquipmentMaintenanceFileIdParam();
                    param.setPkId(i.getPkId());
                    return param;
                }).collect(Collectors.toList()));
            }
        }
    }

    @Override
    public TEquipmentMaintenance detail(TEquipmentMaintenanceIdParam tEquipmentMaintenanceIdParam) {
        return this.queryEntity(tEquipmentMaintenanceIdParam.getPkId());
    }

    @Override
    public TEquipmentMaintenance queryEntity(String id) {
        TEquipmentMaintenance tEquipmentMaintenance = this.getById(id);
        if(ObjectUtil.isEmpty(tEquipmentMaintenance)) {
            throw new CommonException("设备维保不存在，id值为：{}", id);
        }
        return tEquipmentMaintenance;
    }
}
