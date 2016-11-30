package com.flora.web.controller;

import com.flora.service.utils.ExportUtils;
import com.wm.nb.web.CustomDateEditor;
import com.wm.nb.web.WmBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 数据导出
 */
@Controller
@RequestMapping(value = "/export")
public class ExportController extends WmBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExportController.class);

	/**
	 * 导出自定义数组数据
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fromArrayJson", method = RequestMethod.GET)
	public String list(HttpServletResponse response)
			throws Exception {
		// 导出相关操作
		Map<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("rd", "日期");
		String fileName = "test";
		List<Map<String, Object>> exlDataList = new ArrayList<Map<String, Object>>(); // excel数据
		// 无数据
		ExportUtils.exportExcel(response, fileName, exlDataList, headers);
		return null;
	}


	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(true));
	}
}
