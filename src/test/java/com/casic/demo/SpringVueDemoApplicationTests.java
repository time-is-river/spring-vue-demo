package com.casic.demo;

import com.casic.demo.entity.CommodityInformation;
import com.casic.demo.utils.BarcodeUtils;
import com.casic.demo.utils.POIUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = SpringRunner.class)
public class SpringVueDemoApplicationTests {

	@Test
	public void contextLoads() {
		 CommodityInformation commodityInformation = BarcodeUtils.getGoodsInfoByBarcode("6907992504476");
		 System.out.println("ok");
	}
	@Test
	public void readExcel() {
		POIUtil.readExcel("C:/Users/chenxx/Desktop/银行代码.xlsx");
		System.out.println("");
	}

}
