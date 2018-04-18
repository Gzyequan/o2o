package com.yequan.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yequan.o2o.dto.ImageHolder;
import com.yequan.o2o.dto.ProductExecution;
import com.yequan.o2o.entity.Product;
import com.yequan.o2o.entity.ProductCategory;
import com.yequan.o2o.entity.Shop;
import com.yequan.o2o.enums.ProductStateEnum;
import com.yequan.o2o.exceptions.ProductOperationException;
import com.yequan.o2o.service.ProductCategoryService;
import com.yequan.o2o.service.ProductService;
import com.yequan.o2o.util.CodeUtil;
import com.yequan.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

    private static final int IMAGEMAXCOUNT = 6;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long productId = HttpServletRequestUtil.getLong(request, "productId");
        if (productId > -1) {
            Product product = productService.getProductById(productId);
            List<ProductCategory> productCategoryList =
                    productCategoryService.getProductCategoryList(product.getShop().getShopId());
            modelMap.put("success", true);
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "productId为空");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (pageIndex > -1 && pageSize > -1 && null != currentShop && null != currentShop.getShopId()) {
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "pageIndex or pageSize id null");
        }
        return modelMap;
    }

    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误！");
            return modelMap;
        }
        ImageHolder thumbnail = null;
        List<ImageHolder> productImageFileList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver commonsMultipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            if (commonsMultipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail, productImageFileList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = null;
        try {
            product = objectMapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (null != product && null != thumbnail && productImageFileList.size() > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExecution pe = productService.addProduct(product, thumbnail, productImageFileList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    @RequestMapping(value = "modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        //验证码校验
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误！");
            return modelMap;
        }
        //缩略图更新
        ImageHolder thumbnail = null;
        List<ImageHolder> imageHolderList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver commonsMultipartResolver
                = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            if (commonsMultipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail, imageHolderList);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = null;
        try {
            product = objectMapper.readValue(productStr, Product.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        if (null != product) {
            try {
                Shop currentShop = ((Shop) request.getSession().getAttribute("currentShop"));
                product.setShop(currentShop);
                ProductExecution pe = productService.modifyProduct(product, thumbnail, imageHolderList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品信息不能为空");
        }
        return modelMap;
    }

    private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> productImgList)
            throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        }
        // 取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
            if (productImgFile != null) {
                // 若取出的第i个详情图片文件流不为空，则将其加入详情图列表
                ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),
                        productImgFile.getInputStream());
                productImgList.add(productImg);
            } else {
                // 若取出的第i个详情图片文件流为空，则终止循环
                break;
            }
        }
        return thumbnail;
    }

    private Product compactProductCondition(Long shopId, Long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId > -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (null != productName) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }

}
