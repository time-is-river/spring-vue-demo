package com.casic.demo.request;

import lombok.Data;


/**
 * @author
 * @date 2019/1/5 14:26
 */
@Data
public class CommodityRequest {
    private String barcode;
    private String name;
    private String createDate;
    private Boolean haveBarcode;
    private int page;
    private int size;

    public CommodityRequest(String barcode, String name, String createDate, Boolean haveBarcode, int page, int size) {
        this.barcode = barcode;
        this.name = name;
        this.createDate = createDate;
        this.haveBarcode = haveBarcode;
        this.page = page;
        this.size = size;
    }

    public CommodityRequest() {
    }
}
