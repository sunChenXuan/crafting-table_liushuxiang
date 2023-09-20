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
package vip.xiaonuo.biz.modular.customerinspection.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户巡检添加参数
 *
 * @author scx
 * @date  2023/09/20 17:03
 **/
@Getter
@Setter
public class TCustomerInspectionAddParam {

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 2)
    private String inspectionName;

    /** 巡检类型 */
    @ApiModelProperty(value = "巡检类型", position = 3)
    private String inspectionType;

    /** 联系人 */
    @ApiModelProperty(value = "联系人", position = 4)
    private String userName;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 5)
    private String phone;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 6)
    private Date startTime;

    /** 经度 */
    @ApiModelProperty(value = "经度", position = 7)
    private String longitude;

    /** 纬度 */
    @ApiModelProperty(value = "纬度", position = 8)
    private String latitude;

    /** 巡检内容 */
    @ApiModelProperty(value = "巡检内容", position = 9)
    private String remark;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 12)
    private String createdBy;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 13)
    private Date updatedTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 14)
    private String updatedBy;

}
