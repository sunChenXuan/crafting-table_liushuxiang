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
package vip.xiaonuo.biz.modular.equipmentmaintenance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.common.pojo.CommonEntity;
import vip.xiaonuo.sys.modular.user.result.SysUserMini;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 设备维保实体
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Getter
@Setter
@TableName("t_equipment_maintenance")
public class TEquipmentMaintenance extends CommonEntity {

    /** id */
    @TableId
    @ApiModelProperty(value = "id", position = 1)
    private String pkId;

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

    /** 系统设备提醒人 */
    @ApiModelProperty(value = "系统设备提醒人", position = 9)
    @JsonRawValue
    private String equipmentSysUsers;

    /** 设备提醒人 */
    @ApiModelProperty(value = "设备提醒人", position = 10)
    @JsonRawValue
    private String equipmentUsers;

    /* ====额外的字段==== */
    @TableField(exist = false)
    private List<SysUserMini> equipmentSysUserList;
    @TableField(exist = false)
    private List<SysUserMini> equipmentUserList;
    @TableField(exist = false)
    private String projectName;
    @TableField(exist = false)
    private String projectContacts;
    @TableField(exist = false)
    private String projectPhone;

    public String getColor() {
        final long l = authorizationEndTime.getTime() - new Date().getTime();
        if (l > 7 * 24 * 60 * 60 * 1000) {
            return "#000000";
        }
        if (l > 0) {
            return "#FF9900";
        }
        return "#FF0000";
    }
}
