<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--shortcut start-->
<jsp:include page="shortcut.jsp" />
<!--shortcut end-->
<div id="header">
  <div class="header_inner">
    <div class="logo">
			<a name="sfbest_hp_hp_head_logo" href="http://www.e3mall.cn" class="trackref logoleft">
		</a>
			<div class="logo-text">
				<img src="/images/html/logo_word.jpg">
			</div>
		</div>
    <div class="index_promo"></div>
    <div class="search">
      <form action="/productlist/search" id="searchForm" name="query" method="GET">
        <input type="hidden" name="inputBox" value="1"><input type="hidden" name="categoryId" value="0">
        <input type="text" class="text keyword ac_input" name="keyword" id="keyword" value="" style="color: rgb(153, 153, 153);" onkeydown="javascript:if(event.keyCode==13) search_keys('searchForm');" autocomplete="off">
        <input type="button" value="" class="submit" onclick="search_keys('searchForm')">
      </form>
      <div class="search_hot"><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E5%A4%A7%E9%97%B8%E8%9F%B9#trackref=sfbest_hp_hp_head_Keywords1">瓦当</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E7%9F%B3%E6%A6%B4#trackref=sfbest_hp_hp_head_Keywords2">碑帖</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E6%9D%BE%E8%8C%B8#trackref=sfbest_hp_hp_head_Keywords3">经文</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E7%89%9B%E6%8E%92#trackref=sfbest_hp_hp_head_Keywords4">摩崖</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E7%99%BD%E8%99%BE#trackref=sfbest_hp_hp_head_Keywords5">文字砖</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E5%85%A8%E8%84%82%E7%89%9B%E5%A5%B6#trackref=sfbest_hp_hp_head_Keywords6">画像砖</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E6%B4%8B%E6%B2%B3#trackref=sfbest_hp_hp_head_Keywords7">佛像</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E7%BB%BF%E8%B1%86#trackref=sfbest_hp_hp_head_Keywords8">吉祥</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E4%B8%80%E5%93%81%E7%8E%89#trackref=sfbest_hp_hp_head_Keywords9">汉代精品</a></div>
    </div>
    <div class="shopingcar" id="topCart">
      <s class="setCart"></s><a href="http://localhost:8091/cart/cart.html" class="t" rel="nofollow">我的购物车</a><b id="cartNum">0</b>
      <span class="outline"></span>
      <span class="blank"></span>
      <div id="cart_lists">
        <!--cartContent-->   
        <div class="clear"></div>
      </div>
    </div>
  </div>
</div>