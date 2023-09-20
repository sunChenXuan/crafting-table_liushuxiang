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
package vip.xiaonuo.biz.modular.equipmentmaintenancefile.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.entity.TEquipmentMaintenanceFile;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.param.TEquipmentMaintenanceFileAddParam;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.param.TEquipmentMaintenanceFileEditParam;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.param.TEquipmentMaintenanceFileIdParam;
import vip.xiaonuo.biz.modular.equipmentmaintenancefile.param.TEquipmentMaintenanceFilePageParam;

import java.util.List;

/**
 * 设备维保文件Service接口
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
public interface TEquipmentMaintenanceFileService extends IService<TEquipmentMaintenanceFile> {

    /**
     * 获取设备维保文件分页
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    Page<TEquipmentMaintenanceFile> page(TEquipmentMaintenanceFilePageParam tEquipmentMaintenanceFilePageParam);

    /**
     * 添加设备维保文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void add(TEquipmentMaintenanceFileAddParam tEquipmentMaintenanceFileAddParam);

    /**
     * 编辑设备维保文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void edit(TEquipmentMaintenanceFileEditParam tEquipmentMaintenanceFileEditParam);

    /**
     * 删除设备维保文件
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    void delete(List<TEquipmentMaintenanceFileIdParam> tEquipmentMaintenanceFileIdParamList);

    /**
     * 获取设备维保文件详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     */
    TEquipmentMaintenanceFile detail(TEquipmentMaintenanceFileIdParam tEquipmentMaintenanceFileIdParam);

    /**
     * 获取设备维保文件详情
     *
     * @author scx
     * @date  2023/09/20 17:02
     **/
    TEquipmentMaintenanceFile queryEntity(String id);
}
