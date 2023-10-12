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
package vip.xiaonuo.biz.modular.refundflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.refundflow.entity.TRefundFlow;
import vip.xiaonuo.biz.modular.refundflow.mapper.TRefundFlowMapper;
import vip.xiaonuo.biz.modular.refundflow.param.TRefundFlowAddParam;
import vip.xiaonuo.biz.modular.refundflow.param.TRefundFlowEditParam;
import vip.xiaonuo.biz.modular.refundflow.param.TRefundFlowIdParam;
import vip.xiaonuo.biz.modular.refundflow.param.TRefundFlowPageParam;
import vip.xiaonuo.biz.modular.refundflow.service.TRefundFlowService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 续费流水Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Service
public class TRefundFlowServiceImpl extends ServiceImpl<TRefundFlowMapper, TRefundFlow> implements TRefundFlowService {
    @Resource
    private SysUserService sysUserService;

    @Override
    public Page<TRefundFlow> page(TRefundFlowPageParam tRefundFlowPageParam) {
        QueryWrapper<TRefundFlow> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tRefundFlowPageParam.getIdxEquipmentMaintenanceId())) {
            queryWrapper.lambda().eq(TRefundFlow::getIdxEquipmentMaintenanceId, tRefundFlowPageParam.getIdxEquipmentMaintenanceId());
        }
        if(ObjectUtil.isNotEmpty(tRefundFlowPageParam.getSerialNumber())) {
            queryWrapper.lambda().eq(TRefundFlow::getSerialNumber, tRefundFlowPageParam.getSerialNumber());
        }
        if(ObjectUtil.isNotEmpty(tRefundFlowPageParam.getStartAuthorizationStartTime()) && ObjectUtil.isNotEmpty(tRefundFlowPageParam.getEndAuthorizationStartTime())) {
            queryWrapper.lambda().between(TRefundFlow::getAuthorizationStartTime, tRefundFlowPageParam.getStartAuthorizationStartTime(), tRefundFlowPageParam.getEndAuthorizationStartTime());
        }
        if(ObjectUtil.isNotEmpty(tRefundFlowPageParam.getStartAuthorizationEndTime()) && ObjectUtil.isNotEmpty(tRefundFlowPageParam.getEndAuthorizationEndTime())) {
            queryWrapper.lambda().between(TRefundFlow::getAuthorizationEndTime, tRefundFlowPageParam.getStartAuthorizationEndTime(), tRefundFlowPageParam.getEndAuthorizationEndTime());
        }
        if(ObjectUtil.isAllNotEmpty(tRefundFlowPageParam.getSortField(), tRefundFlowPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tRefundFlowPageParam.getSortOrder());
            queryWrapper.orderBy(true, tRefundFlowPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tRefundFlowPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TRefundFlow::getPkId);
        }
        final Page<TRefundFlow> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        for (TRefundFlow rf: page.getRecords()) {
            if (rf.getCreateUser() != null && !rf.getCreateUser().isEmpty()){
                rf.setCreateUserName(sysUserService.queryEntity(rf.getCreateUser()).getName());
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TRefundFlowAddParam tRefundFlowAddParam) {
        TRefundFlow tRefundFlow = BeanUtil.toBean(tRefundFlowAddParam, TRefundFlow.class);
        this.save(tRefundFlow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TRefundFlowEditParam tRefundFlowEditParam) {
        TRefundFlow tRefundFlow = this.queryEntity(tRefundFlowEditParam.getPkId());
        BeanUtil.copyProperties(tRefundFlowEditParam, tRefundFlow);
        this.updateById(tRefundFlow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TRefundFlowIdParam> tRefundFlowIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tRefundFlowIdParamList, TRefundFlowIdParam::getPkId));
    }

    @Override
    public TRefundFlow detail(TRefundFlowIdParam tRefundFlowIdParam) {
        return this.queryEntity(tRefundFlowIdParam.getPkId());
    }

    @Override
    public TRefundFlow queryEntity(String id) {
        TRefundFlow tRefundFlow = this.getById(id);
        if(ObjectUtil.isEmpty(tRefundFlow)) {
            throw new CommonException("续费流水不存在，id值为：{}", id);
        }
        return tRefundFlow;
    }
}
