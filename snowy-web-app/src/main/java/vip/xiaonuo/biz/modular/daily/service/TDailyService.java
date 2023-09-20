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
package vip.xiaonuo.biz.modular.daily.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.daily.entity.TDaily;
import vip.xiaonuo.biz.modular.daily.param.TDailyAddParam;
import vip.xiaonuo.biz.modular.daily.param.TDailyEditParam;
import vip.xiaonuo.biz.modular.daily.param.TDailyIdParam;
import vip.xiaonuo.biz.modular.daily.param.TDailyPageParam;

import java.util.List;

/**
 * 日报Service接口
 *
 * @author scx
 * @date  2023/09/20 17:03
 **/
public interface TDailyService extends IService<TDaily> {

    /**
     * 获取日报分页
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    Page<TDaily> page(TDailyPageParam tDailyPageParam);

    /**
     * 添加日报
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    void add(TDailyAddParam tDailyAddParam);

    /**
     * 编辑日报
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    void edit(TDailyEditParam tDailyEditParam);

    /**
     * 删除日报
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    void delete(List<TDailyIdParam> tDailyIdParamList);

    /**
     * 获取日报详情
     *
     * @author scx
     * @date  2023/09/20 17:03
     */
    TDaily detail(TDailyIdParam tDailyIdParam);

    /**
     * 获取日报详情
     *
     * @author scx
     * @date  2023/09/20 17:03
     **/
    TDaily queryEntity(String id);
}
