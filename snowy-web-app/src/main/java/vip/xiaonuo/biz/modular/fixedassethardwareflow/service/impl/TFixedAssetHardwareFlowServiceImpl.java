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
package vip.xiaonuo.biz.modular.fixedassethardwareflow.service.impl;

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
import vip.xiaonuo.biz.modular.fixedassethardwareflow.entity.TFixedAssetHardwareFlow;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.mapper.TFixedAssetHardwareFlowMapper;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.param.TFixedAssetHardwareFlowAddParam;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.param.TFixedAssetHardwareFlowEditParam;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.param.TFixedAssetHardwareFlowIdParam;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.param.TFixedAssetHardwareFlowPageParam;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.service.TFixedAssetHardwareFlowService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 固定资产硬件流水Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Service
public class TFixedAssetHardwareFlowServiceImpl extends ServiceImpl<TFixedAssetHardwareFlowMapper, TFixedAssetHardwareFlow> implements TFixedAssetHardwareFlowService {

    @Resource
    private SysUserService sysUserService;
    @Override
    public Page<TFixedAssetHardwareFlow> page(TFixedAssetHardwareFlowPageParam tFixedAssetHardwareFlowPageParam) {
        QueryWrapper<TFixedAssetHardwareFlow> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tFixedAssetHardwareFlowPageParam.getIdxFixedAssetId())) {
            queryWrapper.lambda().eq(TFixedAssetHardwareFlow::getIdxFixedAssetId, tFixedAssetHardwareFlowPageParam.getIdxFixedAssetId());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetHardwareFlowPageParam.getSerialNumber())) {
            queryWrapper.lambda().eq(TFixedAssetHardwareFlow::getSerialNumber, tFixedAssetHardwareFlowPageParam.getSerialNumber());
        }
        if(ObjectUtil.isAllNotEmpty(tFixedAssetHardwareFlowPageParam.getSortField(), tFixedAssetHardwareFlowPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tFixedAssetHardwareFlowPageParam.getSortOrder());
            queryWrapper.orderBy(true, tFixedAssetHardwareFlowPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tFixedAssetHardwareFlowPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TFixedAssetHardwareFlow::getPkId);
        }
        final Page<TFixedAssetHardwareFlow> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        for (TFixedAssetHardwareFlow fahf : page.getRecords()){
            if (fahf.getCreateUser() != null && !fahf.getCreateUser().isEmpty()){
                fahf.setCreateUserName(sysUserService.queryEntity(fahf.getCreateUser()).getName());
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TFixedAssetHardwareFlowAddParam tFixedAssetHardwareFlowAddParam) {
        TFixedAssetHardwareFlow tFixedAssetHardwareFlow = BeanUtil.toBean(tFixedAssetHardwareFlowAddParam, TFixedAssetHardwareFlow.class);
        this.save(tFixedAssetHardwareFlow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TFixedAssetHardwareFlowEditParam tFixedAssetHardwareFlowEditParam) {
        TFixedAssetHardwareFlow tFixedAssetHardwareFlow = this.queryEntity(tFixedAssetHardwareFlowEditParam.getPkId());
        BeanUtil.copyProperties(tFixedAssetHardwareFlowEditParam, tFixedAssetHardwareFlow);
        this.updateById(tFixedAssetHardwareFlow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TFixedAssetHardwareFlowIdParam> tFixedAssetHardwareFlowIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tFixedAssetHardwareFlowIdParamList, TFixedAssetHardwareFlowIdParam::getPkId));
    }

    @Override
    public TFixedAssetHardwareFlow detail(TFixedAssetHardwareFlowIdParam tFixedAssetHardwareFlowIdParam) {
        return this.queryEntity(tFixedAssetHardwareFlowIdParam.getPkId());
    }

    @Override
    public TFixedAssetHardwareFlow queryEntity(String id) {
        TFixedAssetHardwareFlow tFixedAssetHardwareFlow = this.getById(id);
        if(ObjectUtil.isEmpty(tFixedAssetHardwareFlow)) {
            throw new CommonException("固定资产硬件流水不存在，id值为：{}", id);
        }
        return tFixedAssetHardwareFlow;
    }
}
