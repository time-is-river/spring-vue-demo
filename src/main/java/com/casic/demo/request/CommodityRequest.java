package com.casic.demo.request;

import lombok.Data;

/**
 * @author
 * @date 2019/1/5 14:26
 */
@Data
public class CommodityRequest {
    private String name;
    private String createDate;
    private Boolean haveBarcode;
    private int page;
    private int size;
}
