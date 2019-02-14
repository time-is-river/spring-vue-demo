package com.casic.demo.service.commodity;

import com.casic.demo.entity.CommodityInformation;
import com.casic.demo.entity.RestResult;
import com.casic.demo.request.CommodityRequest;

/**
 * @author
 * @date 2019/1/3 14:47
 */
public interface CommodityService {

    /**
     * 获取商品信息列表
     * @param commodityRequest
     * @return
     */
    RestResult queryCommodityInformationList(CommodityRequest commodityRequest);

    /**
     * 保存或更新商品信息
     */
    RestResult saveOrUpdate(CommodityInformation commodityInformation);

    /**
     * 删除商品信息
     */
    RestResult remove(Long id);

}
