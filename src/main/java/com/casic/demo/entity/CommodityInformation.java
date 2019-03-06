package com.casic.demo.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author
 * @date 2019/1/3 14:48
 */
@Entity
@Data
public class CommodityInformation{
    @Id
    @GeneratedValue
    private Long id;
    @Column(columnDefinition = "varchar(50) comment '条码号'")
    private String barcode;
    @Column(columnDefinition = "varchar(50) comment '商品名称'")
    private String name;
    @Column(columnDefinition = "DECIMAL(16,2) comment '价格'")
    private BigDecimal price;
    @Column(columnDefinition = "varchar(50) comment '商品规格'")
    private String spec;
    @Column(columnDefinition = "varchar(50) comment '商标'")
    private String brand;
    @Column(columnDefinition = "varchar(100) comment '国家、地区或应用领域'")
    private String country;
    @Column(columnDefinition = "varchar(100) comment '生产公司名称'")
    private String company;
    @Column(columnDefinition = "varchar(200) comment '公司地址'")
    private String addr;
    @Column(columnDefinition = "varchar(200) comment '条形码图片链接'")
    private String gtin;
    @Column(columnDefinition = "varchar(200) comment '商品图片链接'")
    private String imgurl;
    @Column(columnDefinition = "tinyint comment '商品是否有条形码'")
    private Boolean  haveBarcode;
    @Column(columnDefinition = "tinyint comment '是否删除'")
    private Boolean  isDeleted;
    @Column(columnDefinition = "varchar(50) comment '创建人'")
    private String createPerson;
    @Column(columnDefinition = "timestamp comment '创建时间'")
    private Date createDate;
    @Column(columnDefinition = "varchar(50) comment '创建人'")
    private String updatePerson;
    @Column(columnDefinition = "timestamp comment '创建时间'")
    private Date updateDate;
    @Column(columnDefinition = "int(6) comment '商品库存'")
    private int repertory;
    @Column(columnDefinition = "varchar(200) comment '备注信息'")
    private String remark;

}
