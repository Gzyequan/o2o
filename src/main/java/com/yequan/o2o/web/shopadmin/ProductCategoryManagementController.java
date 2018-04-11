package com.yequan.o2o.web.shopadmin;

import com.yequan.o2o.dto.Result;
import com.yequan.o2o.entity.ProductCategory;
import com.yequan.o2o.entity.Shop;
import com.yequan.o2o.enums.ProductCategoryStateEnum;
import com.yequan.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

}
