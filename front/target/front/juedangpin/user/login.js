/**
 * Created by carl on 15/10/30.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        product: '../core/js/module/product'
    }
});

require(['jquery', 'product'], function($, Prd) {
    //登陆信息
    var loginInfo = {
        times: $('#loginCount').val(),
        needAuth: false
    };

    if(loginInfo.times >= 3) {
        $('#authBox').removeClass('hide');
        loginInfo.needAuth = true;
    }



    //username获得、失去焦点
    $('#username')
        .focus(function() {
            $(this).parent().addClass('username-focus');
        })
        .blur(function() {
            $(this).parent().removeClass('username-focus');
        });
    //password获得、失去焦点
    $('#password')
        .focus(function() {
            $(this).parent().removeClass('password-wrong');
            $(this).parent().addClass('password-focus');
        })
        .blur(function() {
            $(this).parent().removeClass('password-focus');
        });

    //提交登陆信息
    $('#loginSub').click(function(event) {
        event.preventDefault();

        if (loginInfo.needAuth) {
            if ($('#authNum').val() != '') {
                 // $('#login').get(0).submit();

                loginSubmit();


            } else {
                $('#loginPrompt').removeClass('hide');
            }
        } else {
            // $('#login').get(0).submit();
            loginSubmit();
        }

        function loginSubmit() {
		 $.ajax({
                    type:"get",
                    dataType:"jsonp",
                    url:"http://sso.yufan.com/user/login",
		            data:$("#login").serialize(),
                    success:function (data) {
console.log(data);
                        if(data.status=="success"){
                            var token=data.data;
                            function setCookie(c_name,value,expiredays)
                            {
                                var exdate=new Date()
                                exdate.setDate(exdate.getDate()+expiredays)
                                document.cookie=c_name+ "=" +escape(value)+
                                    ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
                            }
                            //设置cookie
                            setCookie("token",data.data,2);
                            document.location.href="http://www.yufan.com";
                        }else{
                            alert("登录失败："+data.message);
                        }
                    }
                });    
        };
        
    });

    //
    $('#loginCode').on('click', function() {
        $(this).attr('src', Prd.baseUrl + '/code/login?'+Math.ceil(Math.random()*100000000000000));
    });

});
