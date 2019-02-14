package com.casic.demo.dao.mapper;

import com.casic.demo.entity.CommodityInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @date 2019/1/5 9:56
 */
@Mapper
@Repository
public interface CommodityInformationMapper {
    /**
     * 根据条件查询商品列表
     * @param name
     * @param createDate
     * @return
     */
    List<CommodityInformation> pageQueryCommodityInformation(@Param("name") String name, @Param("createDate") String createDate, @Param("haveBarcode") boolean haveBarcode);
}
