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
package vip.xiaonuo.biz.modular.fixedassetflow.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 固定资产借还流水编辑参数
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Getter
@Setter
public class TFixedAssetFlowEditParam {

    /** id */
    @ApiModelProperty(value = "id", required = true, position = 1)
    @NotBlank(message = "pkId不能为空")
    private String pkId;

    /** 固定资产id */
    @ApiModelProperty(value = "固定资产id", required = true, position = 2)
    @NotBlank(message = "idxFixedAssetId不能为空")
    private String idxFixedAssetId;

    /** 序列号 */
    @ApiModelProperty(value = "序列号", required = true, position = 3)
    @NotBlank(message = "serialNumber不能为空")
    private String serialNumber;

    /** 备注 */
    @ApiModelProperty(value = "备注", required = true, position = 4)
    @NotBlank(message = "remark不能为空")
    private String remark;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 5)
    private Date startTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间", position = 6)
    private Date endTime;

    /** 是否归还 */
    @ApiModelProperty(value = "是否归还", position = 7)
    private Integer isReturn;


}
