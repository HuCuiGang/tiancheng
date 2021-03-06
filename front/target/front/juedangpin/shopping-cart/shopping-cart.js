/**
 * Created by supersoup on 15/12/17.
 */

require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        product: '../core/js/module/product'
    }
});

require(['jquery', 'component', 'product'], function($, Cpn, Prd) {
    $(document).ready(function() {

        //tab切换
        Cpn.tab({
            tabArea: $('#recommend>div'),
            tabLi: $('#tab>li'),
            tabTarget: $('#tab h2')
        });

        $('#popSubmit').click(function (e) {
            e.preventDefault();
            var $this = $(this);
            if ($this.data('pending')) {
                return false;
            }
            $this.data('pending', true);
            var form = $('#popForm'),
                action = form.attr('action'),
                data = form.serialize();
            form.find('span.js-error-msg').remove();
            $.post(action, data).done(function (result) {
                if (result.success == 'true') {
                    window.location.reload();
                } else {
                    if (result.redirectUrl) {
                        window.location.href = result.redirectUrl;
                    } else {
                        var errors = result.errors;
                        $.each(errors, function (key, value) {
                            form.find('[name=' + key + ']').closest('div').append('<span class="js-error-msg">' + value + '</span>');
                        });
                    }
                }
                $this.data('pending', false);
            });
        });


        //三个rowList的水平移动
        Cpn.rowList({
            list: $('#rowList1'),
            left: $('#moveRight1'),
            right: $('#moveLeft1')
        });
        Cpn.rowList({
            list: $('#rowList2'),
            left: $('#moveRight2'),
            right: $('#moveLeft2')
        });
        Cpn.rowList({
            list: $('#rowList3'),
            left: $('#moveRight3'),
            right: $('#moveLeft3')
        });

        //事件委托:加入购物车, 添加收藏
        $(document).on('click', '.addCart', addCart);
        $(document).on('click', '.addEnjoy', addEnjoy);

        //加入购物车
        function addCart(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToOrder(productId, productMessage, $('#asideCartCount, #fixedCartCount, #cartCount'));
        }

        //添加收藏
        function addEnjoy(event) {
            var that = $(this);
            var productId = that.attr('data-value');
            var productMessage = that.parent().siblings().filter('.product-message');

            event.preventDefault();

            Prd.addItemToFavourite(productId, productMessage);
        }

        $(document).on('click', '.removeCart', removeCart);
        $(document).on('click', '.cartToFavourite', cartToFavourite);

        //删除该条
        function removeCart(event) {
            event.preventDefault();
            var that = $(this);
            var tr = that.parents('tr');

            $.ajax({
                type: 'get',
                url: Prd.baseUrl + '/shoppingCart/ajaxRemoveItemFromOrder',
                data: {
                    productId: tr.attr('data-value')
                },
                success: function(param) {
                    if (param.success == 1) {
                        tr.fadeOut(200).remove();
                        $(".order-sub-total").text(param.data.subtotalDisplay);
                        $(".order-total").text(param.data.totalDisplay);
                    }
                }
            });
        }
        //移入收藏
        function cartToFavourite(event) {
            event.preventDefault();
            var that = $(this);
            var tr = that.parents('tr');

            $.ajax({
                type: 'get',
                url: Prd.baseUrl + '/myAccount/favouriteItemFromCart',
                data: {
                    productId: tr.attr('data-value')
                },
                success: function(param) {
                    if (param.success == 1) {
                        tr.fadeOut(200).remove();
                        $(".order-sub-total").text(param.data.subtotalDisplay);
                        $(".order-total").text(param.data.totalDisplay);
                    }
                }
            });
        }
    });
});

