/**
 * Created by Administrator on 2018/3/29 0029.
 */
function Product(option) {
    this.info =option.info||'';
    this.address = option.address||"";
    this.phone = option.phone||"";
    this.price = option.price||"";
    this.num = option.num||1;
    this.images =option.images||[];
    this.config = {
        swipe_wrap: document.getElementsByClassName('swipe-wrap')[0],//轮播图
        goodInfo :  document.getElementById('goodInfo'),//商品信息
        price :  document.getElementById('price'),//价格
        phone :  document.getElementById('phone'),//电话
        address :  document.getElementById('address')//店铺地址
    };
    this.init=function () {
        this.bindDOM();
        this.bindEvent();
    };

}
Product.prototype={
    //加入购物车， 写入session
    addCar:function (that) {
        var memberfilter = new Array();//商品信息
        memberfilter[0] = "info";
        memberfilter[1] = "price";
        memberfilter[2] = "images";
        memberfilter[3] = "num";
        var good = new Array();
        var jsonText = JSON.stringify(that, memberfilter);
        if ((typeof (sessionStorage.good) == "undefined")){//没有商品
            good.push(JSON.parse(jsonText));
        }else{//有商品
            //先取出缓存
            good = JSON.parse(sessionStorage.good);
            good.push(JSON.parse(jsonText));
        }
        sessionStorage.good= JSON.stringify(good, memberfilter);
    },
    bindDOM:function () {
        /*绑定元素*/
        /*通过点语法访问对象中的属性或者方法*/
        this.config.goodInfo.innerHTML=this.info;
        this.config.price.innerHTML=this.price;
        this.config.phone.innerHTML=this.phone;
        this.config.address.innerHTML=this.address;
        var str ="";
        for(var i=0;i<3;i++){
            str+='<div><a href="javascript:;"><img class="img-responsive" src="'+this.images[i]+'"/></a></div>';
        }
        this.config.swipe_wrap.innerHTML=str;
    },
    bindEvent:function () {
        var that = this;
        var btn = document.getElementById('addCarBtn');
        btn.onclick=function () {
            that.addCar(that);
        }

    },

}