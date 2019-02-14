package com.casic.demo.controller;

import com.casic.demo.entity.CommodityInformation;
import com.casic.demo.entity.RestResult;
import com.casic.demo.request.CommodityRequest;
import com.casic.demo.service.commodity.CommodityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @date 2019/1/3 13:51
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommodityService commodityService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public RestResult list(@RequestBody(required = false) CommodityRequest commodityRequest) {
        return commodityService.queryCommodityInformationList(commodityRequest);
    }

    @PostMapping("/saveOrUpdate")
    public RestResult saveOrUpdate(@RequestBody CommodityInformation commodityInformation) {
        return commodityService.saveOrUpdate(commodityInformation);
    }

    @DeleteMapping
    public RestResult remove(@RequestParam("id") Long id) {
        return commodityService.remove(id);
    }
}
