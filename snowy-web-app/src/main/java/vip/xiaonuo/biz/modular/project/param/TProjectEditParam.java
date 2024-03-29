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
package vip.xiaonuo.biz.modular.project.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 项目编辑参数
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
@Getter
@Setter
public class TProjectEditParam {

    /** id */
    @ApiModelProperty(value = "id", required = true, position = 1)
    @NotBlank(message = "pkId不能为空")
    private String pkId;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", required = true, position = 2)
    @NotBlank(message = "projectName不能为空")
    private String projectName;

    /** 项目描述 */
    @ApiModelProperty(value = "项目描述", required = true, position = 3)
    @NotBlank(message = "projectDescription不能为空")
    private String projectDescription;

    /** 联系人 */
    @ApiModelProperty(value = "联系人", required = true, position = 4)
    @NotBlank(message = "projectContacts不能为空")
    private String projectContacts;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", required = true, position = 5)
    @NotBlank(message = "projectPhone不能为空")
    private String projectPhone;

    /** 所属公司 */
    @ApiModelProperty(value = "所属公司", required = true, position = 13)
    @NotBlank(message = "projectCompany不能为空")
    private String projectCompany;


    /** 负责人 */
    @ApiModelProperty(value = "负责人", required = true, position = 6)
    @NotBlank(message = "projectHeadUsers不能为空")
    private String projectHeadUsers;

    /** 职工 */
    @ApiModelProperty(value = "职工", position = 7)
    private String projectUsers;

    /** 项目开始时间 */
    @ApiModelProperty(value = "项目开始时间", position = 11)
    private Date projectStartTime;

    /** 项目结束时间 */
    @ApiModelProperty(value = "项目结束时间", position = 12)
    private Date projectEndTime;


}
