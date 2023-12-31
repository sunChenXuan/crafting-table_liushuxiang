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
package vip.xiaonuo.biz.modular.equipmentmaintenance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 设备维保添加参数
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Getter
@Setter
public class TEquipmentMaintenanceAddParam {

    /** 项目id */
    @ApiModelProperty(value = "项目id", position = 2)
    private String idxProjectId;

    /** 设备名称 */
    @ApiModelProperty(value = "设备名称", position = 3)
    private String equipmentName;

    /** 设备类型 */
    @ApiModelProperty(value = "设备类型", position = 4)
    private String equipmentType;

    /** 设备厂家 */
    @ApiModelProperty(value = "设备厂家", position = 5)
    private String equipmentManufacturer;

    /** 序列号 */
    @ApiModelProperty(value = "序列号", position = 6)
    private String serialNumber;

    /** 授权开始时间 */
    @ApiModelProperty(value = "授权开始时间", position = 7)
    private Date authorizationStartTime;

    /** 授权结束时间 */
    @ApiModelProperty(value = "授权结束时间", position = 8)
    private Date authorizationEndTime;

    /** 设备提醒人 */
    @ApiModelProperty(value = "设备提醒人", position = 10)
    private String equipmentUsers;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String remark;


}
