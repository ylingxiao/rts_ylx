package cn.ylx.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理器
 * @author Yang
 *
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handle,
			Exception ex) {
		//打印控制台
		ex.printStackTrace();
		//写日志
		logger.error("系统发生异常", ex);
		//发邮件，发短信
		//使用jmail工具包
		//错误页面
		ModelAndView model = new ModelAndView();
		model.setViewName("exception");
		return null;
	}

}
