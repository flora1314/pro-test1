package com.flora.web.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flora.domain.common.RemoteResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSON;

public class ExceptionResolver extends SimpleMappingExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (isAjaxRequest(request, handler)) {
			return doResolveAjaxException(response, ex);
		}
		if (shouldApplyTo(request, handler)) {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("Resolving exception from handler [" + handler + "]: " + ex);
			}
			logException(ex, request);
			prepareResponse(ex, response);
			return doResolveException(request, response, handler, ex);
		}
		return null;
	}

	/**
	 * 判断是否是ajax请求
	 * 
	 * @param request
	 * @param handler
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request, Object handler) {
		if (request.getHeader("accept").indexOf("application/json") > -1) {
			return true;
		}
		if (request.getHeader("X-Requested-With") == null) {
			return false;
		}
		if (request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1) {
			return true;
		}
		return false;
	}

	/**
	 * ajax时，写出异常信息到response中
	 * 
	 * @param response
	 * @param ex
	 * @return
	 */
	private ModelAndView doResolveAjaxException(HttpServletResponse response, Exception ex) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			String jsonStr = JSON.toJSONString(RemoteResult.failure(ex));
			if (StringUtils.isNotBlank("jsonStr")) {
				writer.write(jsonStr);
			}
			writer.flush();
		} catch (IOException e) {
			logger.error("处理ajax请求 返回异常信息失败:" + e);
		}
		return new ModelAndView();
	}
}
