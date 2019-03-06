package com.casic.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.casic.demo.entity.CommodityInformation;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @date 2019/3/6 13:43
 */
public class BarcodeUtils {
    private static  String URL_BARCODE = "https://302301.market.alicloudapi.com";
    private static  String PATCH = "/barcode/barcode";
    private static  String APPCODE = "c6a8f29593914e4e856eb175cb1549f8";

    public static CommodityInformation getGoodsInfoByBarcode(String barcode) {
        CommodityInformation commodityInformation = new CommodityInformation();
        String method = "GET";
        Map<String, String> headers = new HashMap(8);
        headers.put("Authorization", "APPCODE " + APPCODE);
        headers.put("Content-Type","application/json; charset=utf-8");
        Map<String, String> query = new HashMap(8);
        query.put("barcode", barcode);
        try {
            HttpResponse response = HttpUtils.doGet(URL_BARCODE,PATCH,method,headers,query);
            System.out.println(response.toString());
            //获取response的body
            HttpEntity entity = response.getEntity();
            String strResult = EntityUtils.toString(entity,"utf-8");
            System.out.println(strResult);
            JSONObject jsonObject = JSONObject.parseObject(strResult);
            commodityInformation.setCountry(jsonObject.getString("country"));
            commodityInformation.setCompany(jsonObject.getString("company"));
            commodityInformation.setGtin(jsonObject.getString("gtin"));
            commodityInformation.setPrice(jsonObject.getBigDecimal("price"));
            commodityInformation.setName(jsonObject.getString("name"));
            commodityInformation.setAddr(jsonObject.getString("addr"));
            commodityInformation.setBarcode(jsonObject.getString("barcode"));
            commodityInformation.setBrand(jsonObject.getString("brand"));
            commodityInformation.setSpec(jsonObject.getString("spec"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commodityInformation;
    }
}
