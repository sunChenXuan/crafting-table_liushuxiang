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
package vip.xiaonuo.biz.modular.fixedassethardwareflow.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.common.pojo.CommonEntity;

import java.util.Date;

/**
 * 固定资产硬件流水实体
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Getter
@Setter
@TableName("t_fixed_asset_hardware_flow")
public class TFixedAssetHardwareFlow extends CommonEntity {

    /** id */
    @TableId
    @ApiModelProperty(value = "id", position = 1)
    private String pkId;

    /** 固定资产id */
    @ApiModelProperty(value = "固定资产id", position = 2)
    private String idxFixedAssetId;

    /** 序列号 */
    @ApiModelProperty(value = "序列号", position = 3)
    private String serialNumber;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 4)
    private String remark;

    /** 硬件修改前信息 */
    @ApiModelProperty(value = "硬件修改前信息", position = 5)
    private String oldJson;

    /** 硬件修改后信息 */
    @ApiModelProperty(value = "硬件修改后信息", position = 6)
    private String newJson;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 7)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 9)
    private String createdBy;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 10)
    private Date updatedTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 11)
    private String updatedBy;
}
