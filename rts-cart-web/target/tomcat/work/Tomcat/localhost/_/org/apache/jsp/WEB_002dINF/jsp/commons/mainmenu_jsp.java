/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-05-18 23:16:25 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.commons;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class mainmenu_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<div class=\"mainNav\">\r\n");
      out.write("  <div class=\"navmenu\">\r\n");
      out.write("    <div class=\"categories\" id=\"public_cate\">\r\n");
      out.write("      <div class=\"dt\"><a name=\"sfbest_Uhead_Uhead_menu_all\" class=\"trackref topall\" href=\"http://www.e3mall.cn/html/web/categorys.html\">精选商品分类</a></div>\r\n");
      out.write("      <div id=\"allSort\" class=\"dd\">\r\n");
      out.write("    <div id=\"booksort\">\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<!----百度统计代码开始------->\r\n");
      out.write("\r\n");
      out.write("<!----百度统计代码结束-------></div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <!----menufloat------->\r\n");
      out.write("    <div class=\"menu1\">\r\n");
      out.write("      <ul>\r\n");
      out.write("\t\t  <li><a name=\"sfbest_hp_hp_nav_nav1\" href=\"http://www.e3mall.cn\" class=\"trackref btndown\">首页</a></li>\r\n");
      out.write("\t\r\n");
      out.write("\t\t\t<li id=\"cat1\">\r\n");
      out.write("\t\t\t  <a name=\"sfbest_hp_hp_nav_nav3\" class=\"trackref\" href=\"/fresh/\">优选精品</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<a href=\"/html/activity/1449221429.html#trackref=sfbest_hp_hp_nav_nav10\" target=\"_blank\">汉代精品</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t  <a target=\"_blank\" href=\"/html/activity/1449559102.html#trackref=sfbest_hp_hp_nav_nav11\">佛教经文</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t  <a target=\"_blank\" href=\"/html/activity/1470121728.html#trackref=sfbest_hp_hp_nav_nav12\">新品查看</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li id=\"cat2\">\r\n");
      out.write("\t\t\t  <a target=\"_blank\" href=\"/ht\" rel=\"nofollow\">秦汉风光</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("                        <li id=\"cat3\">\r\n");
      out.write("\t\t\t  <a target=\"_blank\" href=\"/qiye/\" rel=\"nofollow\">企业专区</a>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t \r\n");
      out.write("\t\t  \t\t  \t\t \r\n");
      out.write("\t\t  \t\t        </ul>\r\n");
      out.write("    </div>\r\n");
      out.write("    <span class=\"clear\"></span>\r\n");
      out.write("  </div>\r\n");
      out.write("</div>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
