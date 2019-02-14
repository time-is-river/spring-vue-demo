package com.casic.demo.dao.repository;

import com.casic.demo.entity.CommodityInformation;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @author
 * @date 2019/1/5 9:32
 */
public interface CommodityInformationRepository extends JpaRepository<CommodityInformation,Integer> {

    /**
     * 根据条形码 获取商品信息
     * @param barcode
     * @return
     */
    CommodityInformation findCommodityInformationByBarcode(String barcode);

    /**
     * 根据商品id 获取商品信息
     */
    CommodityInformation findByIdAndIsDeleted(Long id, boolean isDeleted);

    /**
     * 修改商品信息 删除状态
     * @param isDeleted
     * @param id
     * @return
     */
    @Transactional
    @Modifying
    @Query( value = "UPDATE CommodityInformation c SET c.isDeleted = ?1 WHERE c.id = ?2")
    int updateIsDeleted(@Param(value = "isDeleted") Boolean isDeleted, @Param(value = "id") Long id);
}
