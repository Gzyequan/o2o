$(function () {
    var shopId = getQueryString('shopId');
    var isEdit = !!shopId;
    var initUrl = '/o2o/shopadmin/getshopinitinfo';
    var registerShopUrl = '/o2o/shopadmin/registershop';
    var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
    var editShopUrl = '/o2o/shopadmin/modifyshop';
    if (!isEdit) {
        getShopInitInfo();
    } else {
        getShopInfo();
    }

    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempCategoryHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function (item, index) {
                    tempCategoryHtml
                        += '<option data-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>'
                });
                data.shopAreaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
                });
                $('#shop-category').html(tempCategoryHtml);
                $('#area').html(tempAreaHtml);
            }
        });
    }

    function getShopInfo() {
        $.getJSON(shopInfoUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-address').val(shop.address);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                // 给店铺类别选定原先的店铺类别值
                var shopCategory = '<option data-id="'
                    + shop.shopCategory.shopCategoryId + '" selected>'
                    + shop.shopCategory.shopCategoryName + '</option>';
                var tempAreaHtml = '';
                // 初始化区域列表
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#shop-category').html(shopCategory);
                // 不允许选择店铺类别
                $('#shop-category').attr('disabled', 'disabled');
                $('#area').html(tempAreaHtml);
                // 给店铺选定原先的所属的区域
                $("#area option[data-id='" + shop.area.areaId + "']").attr(
                    "selected", "selected");
            }
        });
    }

    $('#submit').click(function () {
        var shop = {};
        if (isEdit) {
            shop.shopId = shopId;
        }
        shop.shopName = $('#shop-name').val();
        shop.address = $('#shop-address').val();
        shop.phone = $('#shop-phone').val();
        shop.shopDesc = $('#shop-desc').val();
        // 选择选定好的店铺类别
        shop.shopCategory = {
            shopCategoryId: $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        // 选择选定好的区域信息
        shop.area = {
            areaId: $('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };

        var shopImg = $('#shop-image')[0].files[0];
        var formData = new FormData();
        formData.append('shopImg', shopImg);
        formData.append('shopStr', JSON.stringify(shop));
        var verifyCodeActual = $('#j-captcha').val();
        if (!verifyCodeActual) {
            $.toast('验证码不能为空！');
            return;
        }
        formData.append('verifyCodeActual', verifyCodeActual);
        $.ajax({
            url: isEdit ? editShopUrl : registerShopUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功！');
                } else {
                    $.toast('提交失败！' + data.errMsg);
                }
                $('#captcha-img').click();
            }
        })
    })

});