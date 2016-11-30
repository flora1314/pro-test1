package com.flora.web.controller;

import com.flora.domain.common.RemoteResult;
import com.wm.nb.web.WmBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;


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
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/loadData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody RemoteResult loadData(String key) {
		try {
			return RemoteResult.success(new ArrayList<Long>());
		} catch (Exception e) {
			logger.error("发生异常.", e);
			return RemoteResult.failure("系统异常");
		}
	}

}