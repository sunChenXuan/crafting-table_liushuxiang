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
package vip.xiaonuo.biz.modular.fixedassetflow.service.impl;

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
import vip.xiaonuo.biz.modular.fixedassetflow.entity.TFixedAssetFlow;
import vip.xiaonuo.biz.modular.fixedassetflow.mapper.TFixedAssetFlowMapper;
import vip.xiaonuo.biz.modular.fixedassetflow.param.TFixedAssetFlowAddParam;
import vip.xiaonuo.biz.modular.fixedassetflow.param.TFixedAssetFlowEditParam;
import vip.xiaonuo.biz.modular.fixedassetflow.param.TFixedAssetFlowIdParam;
import vip.xiaonuo.biz.modular.fixedassetflow.param.TFixedAssetFlowPageParam;
import vip.xiaonuo.biz.modular.fixedassetflow.service.TFixedAssetFlowService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.user.param.SysUserIdListParam;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 固定资产借还流水Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Service
public class TFixedAssetFlowServiceImpl extends ServiceImpl<TFixedAssetFlowMapper, TFixedAssetFlow> implements TFixedAssetFlowService {
    @Resource
    private SysUserService sysUserService;

    @Override
    public Page<TFixedAssetFlow> page(TFixedAssetFlowPageParam tFixedAssetFlowPageParam) {
        QueryWrapper<TFixedAssetFlow> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tFixedAssetFlowPageParam.getIdxFixedAssetId())) {
            queryWrapper.lambda().eq(TFixedAssetFlow::getIdxFixedAssetId, tFixedAssetFlowPageParam.getIdxFixedAssetId());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetFlowPageParam.getSerialNumber())) {
            queryWrapper.lambda().eq(TFixedAssetFlow::getSerialNumber, tFixedAssetFlowPageParam.getSerialNumber());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetFlowPageParam.getStartStartTime()) && ObjectUtil.isNotEmpty(tFixedAssetFlowPageParam.getEndStartTime())) {
            queryWrapper.lambda().between(TFixedAssetFlow::getStartTime, tFixedAssetFlowPageParam.getStartStartTime(), tFixedAssetFlowPageParam.getEndStartTime());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetFlowPageParam.getStartEndTime()) && ObjectUtil.isNotEmpty(tFixedAssetFlowPageParam.getEndEndTime())) {
            queryWrapper.lambda().between(TFixedAssetFlow::getEndTime, tFixedAssetFlowPageParam.getStartEndTime(), tFixedAssetFlowPageParam.getEndEndTime());
        }
        if(ObjectUtil.isAllNotEmpty(tFixedAssetFlowPageParam.getSortField(), tFixedAssetFlowPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tFixedAssetFlowPageParam.getSortOrder());
            queryWrapper.orderBy(true, tFixedAssetFlowPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tFixedAssetFlowPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TFixedAssetFlow::getPkId);
        }
        final Page<TFixedAssetFlow> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        for (TFixedAssetFlow faf : page.getRecords()){
            if (faf.getLoanee() != null && !faf.getLoanee().isEmpty()){
                SysUserIdListParam sysUserIdListParam = new SysUserIdListParam();
                sysUserIdListParam.setIdList(JSONArray.parseArray(faf.getLoanee(), String.class));
                faf.setLoaneeUserList(sysUserService.getUserListByIdList(sysUserIdListParam));
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TFixedAssetFlowAddParam tFixedAssetFlowAddParam) {
        TFixedAssetFlow tFixedAssetFlow = BeanUtil.toBean(tFixedAssetFlowAddParam, TFixedAssetFlow.class);
        this.save(tFixedAssetFlow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TFixedAssetFlowEditParam tFixedAssetFlowEditParam) {
        TFixedAssetFlow tFixedAssetFlow = this.queryEntity(tFixedAssetFlowEditParam.getPkId());
        BeanUtil.copyProperties(tFixedAssetFlowEditParam, tFixedAssetFlow);
        this.updateById(tFixedAssetFlow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TFixedAssetFlowIdParam> tFixedAssetFlowIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tFixedAssetFlowIdParamList, TFixedAssetFlowIdParam::getPkId));
    }

    @Override
    public TFixedAssetFlow detail(TFixedAssetFlowIdParam tFixedAssetFlowIdParam) {
        return this.queryEntity(tFixedAssetFlowIdParam.getPkId());
    }

    @Override
    public TFixedAssetFlow queryEntity(String id) {
        TFixedAssetFlow tFixedAssetFlow = this.getById(id);
        if(ObjectUtil.isEmpty(tFixedAssetFlow)) {
            throw new CommonException("固定资产借还流水不存在，id值为：{}", id);
        }
        return tFixedAssetFlow;
    }

    @Override
    public TFixedAssetFlow SelectNewIdxFixedAssetId(String idxFixedAssetId) {
        QueryWrapper<TFixedAssetFlow> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TFixedAssetFlow::getIdxFixedAssetId, idxFixedAssetId)
                .orderByDesc(TFixedAssetFlow::getPkId)
                .last("limit 1");
        final TFixedAssetFlow one = this.getOne(queryWrapper);
        if (one.getLoanee() != null && !one.getLoanee().isEmpty()){
            SysUserIdListParam sysUserIdListParam = new SysUserIdListParam();
            sysUserIdListParam.setIdList(JSONArray.parseArray(one.getLoanee(), String.class));
            one.setLoaneeUserList(sysUserService.getUserListByIdList(sysUserIdListParam));
        }
        return one;
    }
}
