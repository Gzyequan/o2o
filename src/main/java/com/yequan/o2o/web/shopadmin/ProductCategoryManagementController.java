package com.yequan.o2o.web.shopadmin;

import com.yequan.o2o.dto.ProductCategoryExecution;
import com.yequan.o2o.dto.Result;
import com.yequan.o2o.entity.ProductCategory;
import com.yequan.o2o.entity.Shop;
import com.yequan.o2o.enums.ProductCategoryStateEnum;
import com.yequan.o2o.exceptions.ProductCategoryOperationException;
import com.yequan.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/shopadmin")
@Controller
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping("/getproductcategorylist")
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (null != currentShop && null != currentShop.getShopId()) {
            try {
                List<ProductCategory> productCategoryList =
                        productCategoryService.getProductCategoryList(currentShop.getShopId());
                return new Result<List<ProductCategory>>(true, productCategoryList);
            } catch (Exception e) {
                return new Result<List<ProductCategory>>(false,
                        e.getMessage(), ProductCategoryStateEnum.INNER_ERROR.getState());
            }
        } else {
            return new Result<List<ProductCategory>>(false,
                    ProductCategoryStateEnum.INNER_ERROR.getStateInfo(),
                    ProductCategoryStateEnum.INNER_ERROR.getState());
        }
    }

    @RequestMapping("/addproductcategorys")
    @ResponseBody
    private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
                                                    HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (null != productCategoryList && productCategoryList.size() > 0) {
            Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
            for (ProductCategory productCategory : productCategoryList) {
                productCategory.setShopId(currentShop.getShopId());
                productCategory.setCreateTime(new Date());
            }
            try {
                ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少添加一个商品类别");
        }
        return modelMap;
    }

    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (null != productCategoryId && productCategoryId > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请选择一个商品类别");
        }
        return modelMap;
    }

}
