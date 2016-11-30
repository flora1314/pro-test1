package com.flora.web.controller;

import com.flora.domain.common.PageVO;
import com.flora.domain.common.RemoteResult;
import com.wm.nb.web.WmBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/common")
public class CommonController extends WmBaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	/**
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET })
	public String demoList(String key) {

		return "demo/demo-list";
	}

	/**
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/loadData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody RemoteResult loadData(String param, PageVO<Map<String, Object>> page) {
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(new HashMap<String, Object>());
			list.add(new HashMap<String, Object>());
			list.add(new HashMap<String, Object>());
			list.add(new HashMap<String, Object>());
			page.setData(list);
			page.setTotalCount(100);
			return RemoteResult.success(page);
		} catch (Exception e) {
			logger.error("发生异常.", e);
			return RemoteResult.failure("系统异常");
		}
	}

}