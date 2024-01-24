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
package vip.xiaonuo.biz.modular.daily.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.daily.entity.TDaily;
import vip.xiaonuo.biz.modular.daily.mapper.TDailyMapper;
import vip.xiaonuo.biz.modular.daily.param.TDailyAddParam;
import vip.xiaonuo.biz.modular.daily.param.TDailyEditParam;
import vip.xiaonuo.biz.modular.daily.param.TDailyIdParam;
import vip.xiaonuo.biz.modular.daily.param.TDailyPageParam;
import vip.xiaonuo.biz.modular.daily.service.TDailyService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.user.result.SysLoginUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日报Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:03
 **/
@Service
public class TDailyServiceImpl extends ServiceImpl<TDailyMapper, TDaily> implements TDailyService {
    @Resource
    private SysUserService sysUserService;
    @Override
    public Page<TDaily> page(TDailyPageParam tDailyPageParam) {
        QueryWrapper<TDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(TDaily::getPkId);
        if(ObjectUtil.isNotEmpty(tDailyPageParam.getObjectName())) {
            queryWrapper.lambda().like(TDaily::getObjectName, tDailyPageParam.getObjectName());
        }
        if(ObjectUtil.isNotEmpty(tDailyPageParam.getStartTime()) && ObjectUtil.isNotEmpty(tDailyPageParam.getEndTime())) {
            queryWrapper.lambda().between(TDaily::getTime, tDailyPageParam.getStartTime(), tDailyPageParam.getEndTime());
        }
        if(ObjectUtil.isNotEmpty(tDailyPageParam.getRemark())) {
            queryWrapper.lambda().like(TDaily::getRemark, tDailyPageParam.getRemark());
        }
        if(ObjectUtil.isAllNotEmpty(tDailyPageParam.getSortField(), tDailyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tDailyPageParam.getSortOrder());
            queryWrapper.orderBy(true, tDailyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tDailyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TDaily::getPkId);
        }
        final Page<TDaily> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        for (TDaily d : page.getRecords()) {
            if (d.getCreateUser() != null && !d.getCreateUser().isEmpty()){
                final SysLoginUser user = sysUserService.getUserById(d.getCreateUser());
                d.setCreateUserName(user.getName());
                d.setCreateUserPhone(user.getPhone());
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TDailyAddParam tDailyAddParam) {
        TDaily tDaily = BeanUtil.toBean(tDailyAddParam, TDaily.class);
        this.save(tDaily);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TDailyEditParam tDailyEditParam) {
        final TDaily byId = this.getById(tDailyEditParam.getPkId());
        if (byId == null){
            throw new CommonException("系统错误, 无ID为{}的数据", tDailyEditParam.getPkId());
        }
        if (!byId.getCreateUser().equals(StpLoginUserUtil.getLoginUser().getPhone())){
            throw new CommonException("仅限创建人修改, 无修改此条权限");
        }
        TDaily tDaily = this.queryEntity(tDailyEditParam.getPkId());
        BeanUtil.copyProperties(tDailyEditParam, tDaily);
        this.updateById(tDaily);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TDailyIdParam> tDailyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tDailyIdParamList, TDailyIdParam::getPkId));
    }

    @Override
    public TDaily detail(TDailyIdParam tDailyIdParam) {
        return this.queryEntity(tDailyIdParam.getPkId());
    }

    @Override
    public TDaily queryEntity(String id) {
        TDaily tDaily = this.getById(id);
        if(ObjectUtil.isEmpty(tDaily)) {
            throw new CommonException("日报不存在，id值为：{}", id);
        }
        if (!tDaily.getCreateUser().equals(StpLoginUserUtil.getLoginUser().getPhone())){
            throw new CommonException("仅限创建人修改, 无修改此条权限");
        }
        return tDaily;
    }
}
