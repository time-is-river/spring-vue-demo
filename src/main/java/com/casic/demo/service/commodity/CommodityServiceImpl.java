package com.casic.demo.service.commodity;

import com.casic.demo.dao.mapper.CommodityInformationMapper;
import com.casic.demo.dao.repository.CommodityInformationRepository;
import com.casic.demo.entity.CommodityInformation;
import com.casic.demo.entity.JwtSysUser;
import com.casic.demo.entity.RestResult;
import com.casic.demo.entity.SysUser;
import com.casic.demo.request.CommodityRequest;
import com.casic.demo.service.user.SysUserService;
import com.casic.demo.utils.Constants;
import com.github.pagehelper.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * @author
 * @date 2019/1/3 14:45
 */
@Service
public class CommodityServiceImpl implements CommodityService{

    @Autowired
    private CommodityInformationMapper commodityInformationMapper;

    @Autowired
    private CommodityInformationRepository commodityInformationRepository;

    @Autowired
    private SysUserService sysUserServiceImpl;


    @Override
    public RestResult queryCommodityInformationList(CommodityRequest commodityRequest) {
        RestResult restResult = new RestResult();
        restResult.setCode(Constants.ReturnCode.SUCCESS.getCode());
        restResult.setMessage(Constants.ReturnCode.SUCCESS.getMsg());
        try {
            PageInfo<CommodityInformation> commodityInfo =  PageHelper.startPage(commodityRequest.getPage(), commodityRequest.getSize(), true, false, false).doSelectPageInfo(()
                    -> commodityInformationMapper.pageQueryCommodityInformation(commodityRequest.getBarcode(), commodityRequest.getName(), commodityRequest.getCreateDate(), commodityRequest.getHaveBarcode()));
            restResult.setData(commodityInfo);
        } catch (RuntimeException e) {
            restResult.setCode(Constants.ReturnCode.FAILURE.getCode());
            restResult.setMessage(e.getMessage());
            //todo 记录日志信息
        }
        return restResult;
    }

    @Override
    public RestResult saveOrUpdate(CommodityInformation commodityInformationRequest) {
        SysUser currentUser = sysUserServiceImpl.currentUser();
        RestResult restResult = new RestResult();
        restResult.setCode(Constants.ReturnCode.SUCCESS.getCode());
        restResult.setSuccess(true);
        try {
            if (commodityInformationRequest != null) {
                if (commodityInformationRequest.getId() != null) {
                    //编辑信息
                    //todo 查询原有信息
                    CommodityInformation commodityInformation = commodityInformationRepository.findByIdAndIsDeleted(commodityInformationRequest.getId(), false);
                    commodityInformation.setBarcode(commodityInformationRequest.getBarcode());
                    commodityInformation.setPrice(commodityInformationRequest.getPrice());
                    commodityInformation.setName(commodityInformationRequest.getName());
                    commodityInformation.setSpec(commodityInformationRequest.getSpec());
                    commodityInformation.setRemark(commodityInformationRequest.getRemark());
                    commodityInformation.setUpdatePerson(String.valueOf(currentUser.getName()));
                    commodityInformation.setUpdateDate(new Date());
                    commodityInformationRepository.save(commodityInformation);
                } else {
                    //新增信息
                    if (commodityInformationRequest.getBarcode() != null) {
                        //todo 查询商品数据库 匹配商品相关信息

                        //有条码信息
                        commodityInformationRequest.setIsDeleted(false);
                        commodityInformationRequest.setHaveBarcode(true);
                        commodityInformationRequest.setCreatePerson(String.valueOf(currentUser.getName()));
                        commodityInformationRequest.setCreateDate(new Date());
                        commodityInformationRepository.save(commodityInformationRequest);
                    } else {
                        //无条码商品
                        commodityInformationRequest.setIsDeleted(false);
                        commodityInformationRequest.setHaveBarcode(false);
                        commodityInformationRequest.setCreatePerson(String.valueOf(currentUser.getName()));
                        commodityInformationRequest.setCreateDate(new Date());
                        commodityInformationRepository.save(commodityInformationRequest);
                    }
                }
            } else {
                restResult.setCode(Constants.ReturnCode.SUCCESS.getCode());
                restResult.setMessage("商品信息不能为空");
            }
        } catch (RuntimeException e) {
            restResult.setCode(Constants.ReturnCode.FAILURE.getCode());
            restResult.setSuccess(false);
            restResult.setMessage(e.getMessage());
            //todo 记录日志信息
        }
        return restResult;
    }

    @Override
    public RestResult remove(Long id) {
        RestResult restResult = new RestResult();
        restResult.setSuccess(true);
        try {
            int count = commodityInformationRepository.updateIsDeleted(true, id);
            if (count != 1) {
                restResult.setSuccess(false);
                restResult.setMessage("商品信息删除失败");
            }
        } catch (RuntimeException e) {
            restResult.setCode(Constants.ReturnCode.FAILURE.getCode());
            restResult.setSuccess(false);
            restResult.setMessage(e.getMessage());
        }
        return restResult;
    }
}
