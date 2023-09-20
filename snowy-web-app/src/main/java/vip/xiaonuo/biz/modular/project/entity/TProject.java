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
package vip.xiaonuo.biz.modular.project.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目实体
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Getter
@Setter
@TableName("t_project")
public class TProject {

    /** id */
    @TableId
    @ApiModelProperty(value = "id", position = 1)
    private String pkId;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 2)
    private String projectName;

    /** 项目描述 */
    @ApiModelProperty(value = "项目描述", position = 3)
    private String projectDescription;

    /** 联系人 */
    @ApiModelProperty(value = "联系人", position = 4)
    private String projectContacts;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 5)
    private String projectPhone;

    /** 负责人 */
    @ApiModelProperty(value = "负责人", position = 6)
    private String projectHeadUsers;

    /** 职工 */
    @ApiModelProperty(value = "职工", position = 7)
    private String projectUsers;

    /** 项目沟通 */
    @ApiModelProperty(value = "项目沟通", position = 8)
    private String projectCommunication;

    /** 项目风险 */
    @ApiModelProperty(value = "项目风险", position = 9)
    private String projectRisk;

    /** 采购管理 */
    @ApiModelProperty(value = "采购管理", position = 10)
    private String purchasingManagement;

    /** 项目开始时间 */
    @ApiModelProperty(value = "项目开始时间", position = 11)
    private Date projectStartTime;

    /** 项目结束时间 */
    @ApiModelProperty(value = "项目结束时间", position = 12)
    private Date projectEndTime;

    /** 项目成本 */
    @ApiModelProperty(value = "项目成本", position = 13)
    private String projectCost;

    /** 项目质量 */
    @ApiModelProperty(value = "项目质量", position = 14)
    private String projectQuality;

    /** 项目收获 */
    @ApiModelProperty(value = "项目收获", position = 15)
    private String projectDelivery;

    /** 存在问题 */
    @ApiModelProperty(value = "存在问题", position = 16)
    private String existingProblems;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 17)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 18)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 19)
    private String createdBy;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 20)
    private Date updatedTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 21)
    private String updatedBy;
}
