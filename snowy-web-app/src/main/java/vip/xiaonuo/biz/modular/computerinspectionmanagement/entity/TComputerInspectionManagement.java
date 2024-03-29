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
package vip.xiaonuo.biz.modular.computerinspectionmanagement.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.user.result.SysUserMini;

import java.util.List;

/**
 * 机房巡检管理实体
 *
 * @author scx
 * @date  2024/01/17 08:46
 **/
@Getter
@Setter
@TableName("t_computer_inspection_management")
public class TComputerInspectionManagement {

    /** id */
    @TableId
    @ApiModelProperty(value = "id", position = 1)
    private String pkId;

    /** 项目名称 存储项目列表的id*/
    @ApiModelProperty(value = "项目名称", position = 2)
    private String inspectionName;

    /** 巡检人员 */
    @ApiModelProperty(value = "巡检人员", position = 3)
    @JsonRawValue
    private String inspectionUsers;

    /** 经度 */
    @ApiModelProperty(value = "经度", position = 4)
    private String longitude;

    /** 纬度 */
    @ApiModelProperty(value = "纬度", position = 5)
    private String latitude;

    /** 巡检项目 */
    @ApiModelProperty(value = "巡检项目", position = 6)
    private String inspectionType;

    /** 巡检报告 */
    @ApiModelProperty(value = "巡检报告", position = 7)
    @JsonRawValue
    private String remarkReport;

    /** 作业计划 */
    @ApiModelProperty(value = "作业计划", position = 8)
    private String workPlan;

    /* ====额外的字段==== */
    @TableField(exist = false)
    private List<SysUserMini> userList;
    @TableField(exist = false)
    private String projectName;
    @TableField(exist = false)
    private String inspectionTypeName;
    public String getNameName() {
        return projectName + "-" + inspectionTypeName;
    }
}
