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
package vip.xiaonuo.sys.modular.user.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户结果集
 *
 * @author xuyuxiang
 * @date 2022/7/8 13:22
 **/
@Getter
@Setter
public class SysUserMini {

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 33)
    private String orgId;

    /** 账号 */
    @ApiModelProperty(value = "账号", position = 5)
    private String account;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 7)
    private String name;

    /** 排序码 */
    @ApiModelProperty(value = "排序码", position = 46)
    private Integer sortCode;
}
