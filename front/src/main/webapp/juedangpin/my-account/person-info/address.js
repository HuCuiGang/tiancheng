/**
 * Created by supersoup on 15/12/3.
 */


/**
 * Created by carl on 15/10/30.
 */
require.config({
    paths: {
        jquery: '../../core/js/jquery.min',
        component: '../../core/js/module/component',
		product: '../../core/js/module/product'
	}
});

require(['jquery', 'component', 'product'], function($, Cpn, Prd) {
    $(document).ready(function() {

        //固定头部
        Cpn.fixedHead({
            head: $('#fixedHead'),
            height: 800,
            time: 200
        });

        //弹出框
        Cpn.pop({
            popUp: $('#popUp'),
            close: $('#popClose, #popReset')
        });
        $('#addAddress').click(function() {
            $('#popUp').fadeIn(300);
        });

		var areaObj = {
			province: $('#province'),
			city: $('#city'),
			country: $('#country')
		};

		//省->市
		Cpn.select2(areaObj.province, function(id) {
			var url = Prd.baseUrl + '/getCityByProvinceId/' + id;
			$.ajax({
				url: url,
				data: null,
				success: function(param) {
					var str = '';
					$.each($.parseJSON(param), function() {
						str += '<li><a class="option-view" data-value="'+ this.id+'" href="#">'+ this.name+'</a></li>'
					});
					$('#city').children('.selected').html('请选择').end().siblings('.options').html(str).next('.select-value').val('');
					$('#country').children('.selected').html('请选择').end().siblings('.options').html('').next('.select-value').val('');
				}
			})
		});

		//市->区
		Cpn.select2(areaObj.city, function(id) {
			var url = Prd.baseUrl + '/getAreaByCityId/' + id;
			$.ajax({
				type: 'get',
				url: url,
				data: null,
				success: function(param) {
					var str = '';
					$.each($.parseJSON(param), function() {
						str += '<li><a class="option-view" data-value="'+ this.id+'" href="#">'+ this.name+'</a></li>'
					});
					$('#country').children('.selected').html('请选择').end().siblings('.options').html(str).next('.select-value').val('');
				}
			})
		});

		//区
		Cpn.select2(areaObj.country);

        
        $('#popSubmit').click(function(e){
        	e.preventDefault();
        	var $this = $(this);
        	if($this.data('pending')){
        		return false;
        	}
        	$this.data('pending',true);
        	var form = $('#popForm'),
        		action = form.attr('action'),
        		data = form.serialize();
        	form.find('span.js-error-msg').remove();
        	$.post(action,data).done(function(result){
        		if(result.success == 'true'){
        			window.location.reload();
        		}else{
        			if(result.redirectUrl){
        				window.location.href = result.redirectUrl;
        			}else{
        				var errors = result.errors;
        				$.each(errors,function(key,value){
        					form.find('[name='+key+']').closest('div').append('<span class="js-error-msg">'+value+'</span>');
        				});
        			}
        		}
        		$this.data('pending',false);
        	});
        });
        
        $('.js-update-address').click(function(e) {
        	e.preventDefault();
        	var $this = $(this),
        	 	form = $('#popForm');
        	if($this.data('pending')){
        		return false;
        	}
        	$this.data('pending',true);
        	form.attr('action',$this.data('href'));
        	$.get('findAddress/'+$this.data('address-id')).done(function(result){
        		if(result.success == 'true'){
        			var data = JSON.parse(result.data);
        			$.each(data, function(key,value){
        				var el = form.find('[name='+key+']');
        				if(el){
        					el.val(value);
        				}
        			});

					var province = $('.province');
					var city = $('.city');
					var country = $('.country');
					province.find('.selected').html(province.find('.select-value').val());
					city.find('.selected').html(city.find('.select-value').val());
					country.find('.selected').html(country.find('.select-value').val());

        			$('#popUp').fadeIn(300);
        			$this.data('pending',false);
        		}
        	});
        });
        
        $('.js-delete-address').click(function(e){
        	e.preventDefault();
        	var $this = $(this);
        	$.post($this.data('href')).done(function(result){
        		if(result.success == 'true'){
        			$this.closest('div.address').remove();
        		}
        	});
        });
        
        $('.js-set-default-address').click(function(e){
        	e.preventDefault();
        	var $this = $(this);
        	$.post($this.data('href')).done(function(result){
        		if(result.success == 'true'){
        			window.location.reload();
        		}
        	});
        });


		//tab切换
		Cpn.tab({
			tabArea: $('#recommend>div'),
			tabLi: $('#tab>li'),
			tabTarget: $('#tab h2')
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

		//菜单折叠
		Cpn.foldToggle($('.menu-level-1'));

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

		//显示购物车数量
		Prd.getOrderItemCount($('#asideCartCount, #fixedCartCount, #cartCount, .right-buy1'));


    });
});