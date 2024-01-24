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
package vip.xiaonuo.biz.modular.fixedasset.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.fixedasset.entity.TFixedAsset;
import vip.xiaonuo.biz.modular.fixedasset.mapper.TFixedAssetMapper;
import vip.xiaonuo.biz.modular.fixedasset.param.TFixedAssetAddParam;
import vip.xiaonuo.biz.modular.fixedasset.param.TFixedAssetEditParam;
import vip.xiaonuo.biz.modular.fixedasset.param.TFixedAssetIdParam;
import vip.xiaonuo.biz.modular.fixedasset.param.TFixedAssetPageParam;
import vip.xiaonuo.biz.modular.fixedasset.service.TFixedAssetService;
import vip.xiaonuo.biz.modular.fixedassetfile.entity.TFixedAssetFile;
import vip.xiaonuo.biz.modular.fixedassetfile.param.TFixedAssetFileIdParam;
import vip.xiaonuo.biz.modular.fixedassetfile.service.TFixedAssetFileService;
import vip.xiaonuo.biz.modular.fixedassetflow.entity.TFixedAssetFlow;
import vip.xiaonuo.biz.modular.fixedassetflow.param.TFixedAssetFlowIdParam;
import vip.xiaonuo.biz.modular.fixedassetflow.service.TFixedAssetFlowService;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.entity.TFixedAssetHardwareFlow;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.param.TFixedAssetHardwareFlowIdParam;
import vip.xiaonuo.biz.modular.fixedassethardwareflow.service.TFixedAssetHardwareFlowService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 固定资产Service接口实现类
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Service
public class TFixedAssetServiceImpl extends ServiceImpl<TFixedAssetMapper, TFixedAsset> implements TFixedAssetService {

    @Resource
    private TFixedAssetHardwareFlowService tFixedAssetHardwareFlowService;
    @Resource
    private TFixedAssetFlowService tFixedAssetFlowService;
    @Resource
    private TFixedAssetFileService tFixedAssetFileService;
    @Override
    public Page<TFixedAsset> page(TFixedAssetPageParam tFixedAssetPageParam) {
        QueryWrapper<TFixedAsset> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(TFixedAsset::getPkId);
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getSerialNumber())) {
            queryWrapper.lambda().eq(TFixedAsset::getSerialNumber, tFixedAssetPageParam.getSerialNumber());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetType())) {
            queryWrapper.lambda().eq(TFixedAsset::getFixedAssetType, tFixedAssetPageParam.getFixedAssetType());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetCpu())) {
            queryWrapper.lambda().like(TFixedAsset::getFixedAssetCpu, tFixedAssetPageParam.getFixedAssetCpu());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetMemory())) {
            queryWrapper.lambda().like(TFixedAsset::getFixedAssetMemory, tFixedAssetPageParam.getFixedAssetMemory());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetSsd())) {
            queryWrapper.lambda().like(TFixedAsset::getFixedAssetSsd, tFixedAssetPageParam.getFixedAssetSsd());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetDisk())) {
            queryWrapper.lambda().like(TFixedAsset::getFixedAssetDisk, tFixedAssetPageParam.getFixedAssetDisk());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetGpu())) {
            queryWrapper.lambda().like(TFixedAsset::getFixedAssetGpu, tFixedAssetPageParam.getFixedAssetGpu());
        }
        if(ObjectUtil.isNotEmpty(tFixedAssetPageParam.getFixedAssetAccessory())) {
            queryWrapper.lambda().like(TFixedAsset::getFixedAssetAccessory, tFixedAssetPageParam.getFixedAssetAccessory());
        }
        if(ObjectUtil.isAllNotEmpty(tFixedAssetPageParam.getSortField(), tFixedAssetPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tFixedAssetPageParam.getSortOrder());
            queryWrapper.orderBy(true, tFixedAssetPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tFixedAssetPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TFixedAsset::getPkId);
        }
        final Page<TFixedAsset> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        for (TFixedAsset fa:page.getRecords()){
            final TFixedAssetFlow tFixedAssetFlow = tFixedAssetFlowService.SelectNewIdxFixedAssetId(fa.getPkId());
            if (tFixedAssetFlow != null) {
                fa.setIsReturn(tFixedAssetFlow.getIsReturn());
                fa.setLoaneeUserList(tFixedAssetFlow.getLoaneeUserList());
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TFixedAssetAddParam tFixedAssetAddParam) {
        TFixedAsset tFixedAsset = BeanUtil.toBean(tFixedAssetAddParam, TFixedAsset.class);
        this.save(tFixedAsset);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void edit(TFixedAssetEditParam tFixedAssetEditParam) {
        // 记录
        TFixedAssetHardwareFlow tFixedAssetHardwareFlow = new TFixedAssetHardwareFlow();

        TFixedAsset tFixedAsset = this.queryEntity(tFixedAssetEditParam.getPkId());

        tFixedAssetHardwareFlow.setOldJson(JSONObject.toJSONString(tFixedAsset));

        BeanUtil.copyProperties(tFixedAssetEditParam, tFixedAsset);
        this.updateById(tFixedAsset);

        tFixedAssetHardwareFlow.setNewJson(JSONObject.toJSONString(tFixedAsset));
        tFixedAssetHardwareFlow.setIdxFixedAssetId(tFixedAssetEditParam.getPkId());
        tFixedAssetHardwareFlow.setSerialNumber(tFixedAssetEditParam.getSerialNumber());
        tFixedAssetHardwareFlow.setRemark(tFixedAssetEditParam.getRemark());
        tFixedAssetHardwareFlowService.save(tFixedAssetHardwareFlow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TFixedAssetIdParam> tFixedAssetIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tFixedAssetIdParamList, TFixedAssetIdParam::getPkId));

        {
            // 删除文件
            QueryWrapper<TFixedAssetFile> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(
                    TFixedAssetFile::getIdxFixedAssetId, tFixedAssetIdParamList.stream().map(TFixedAssetIdParam::getPkId).collect(Collectors.toList())
            );
            final List<TFixedAssetFile> list = tFixedAssetFileService.list(queryWrapper);
            if (list != null && !list.isEmpty()){
                tFixedAssetFileService.delete(list.stream().map(i -> {
                    TFixedAssetFileIdParam param = new TFixedAssetFileIdParam();
                    param.setPkId(i.getPkId());
                    return param;
                }).collect(Collectors.toList()));
            }
        }

        {
            // 删除借还流水
            QueryWrapper<TFixedAssetFlow> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(
                    TFixedAssetFlow::getIdxFixedAssetId, tFixedAssetIdParamList.stream().map(TFixedAssetIdParam::getPkId).collect(Collectors.toList())
            );
            final List<TFixedAssetFlow> list = tFixedAssetFlowService.list(queryWrapper);
            if (list != null && !list.isEmpty()){
                tFixedAssetFlowService.delete(list.stream().map(i -> {
                    TFixedAssetFlowIdParam param = new TFixedAssetFlowIdParam();
                    param.setPkId(i.getPkId());
                    return param;
                }).collect(Collectors.toList()));
            }
        }

        {
            // 删除硬件流水
            QueryWrapper<TFixedAssetHardwareFlow> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(
                    TFixedAssetHardwareFlow::getIdxFixedAssetId, tFixedAssetIdParamList.stream().map(TFixedAssetIdParam::getPkId).collect(Collectors.toList())
            );
            final List<TFixedAssetHardwareFlow> list = tFixedAssetHardwareFlowService.list(queryWrapper);
            if (list != null && !list.isEmpty()){
                tFixedAssetHardwareFlowService.delete(list.stream().map(i -> {
                    TFixedAssetHardwareFlowIdParam param = new TFixedAssetHardwareFlowIdParam();
                    param.setPkId(i.getPkId());
                    return param;
                }).collect(Collectors.toList()));
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void isReturn(List<TFixedAssetIdParam> tFixedAssetIdParamList) {
        // 归还
        if (tFixedAssetIdParamList.size() == 0){
            return;
        }
        TFixedAssetFlow tFixedAssetFlow = tFixedAssetFlowService.SelectNewIdxFixedAssetId(tFixedAssetIdParamList.get(0).getPkId());
        if (tFixedAssetFlow == null){
            return;
        }
        if (tFixedAssetFlow.getIsReturn().equals(1)){
            return;
        }
        tFixedAssetFlow.setEndTime(new Date());
        tFixedAssetFlow.setIsReturn(1);
        tFixedAssetFlowService.updateById(tFixedAssetFlow);
    }

    @Override
    public TFixedAsset detail(TFixedAssetIdParam tFixedAssetIdParam) {
        return this.queryEntity(tFixedAssetIdParam.getPkId());
    }

    @Override
    public TFixedAsset queryEntity(String id) {
        TFixedAsset tFixedAsset = this.getById(id);
        if(ObjectUtil.isEmpty(tFixedAsset)) {
            throw new CommonException("固定资产不存在，id值为：{}", id);
        }
        return tFixedAsset;
    }
}
