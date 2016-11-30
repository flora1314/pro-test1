package com.flora.domain.common;


import java.io.PrintWriter;
import java.io.StringWriter;


public class RemoteResult implements java.io.Serializable {

	private static final long serialVersionUID = -2467880394623001425L;

	public static final String CODE_PREFIX = "CEM";

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 描述信息
	 */
	private String msg;

	/**
	 * 接口数据
	 */
	private Object data;

	/**
	 * 是否成功
	 */
	private boolean success;

	public RemoteResult() {
		// 默认构造方法
	}

	public RemoteResult(boolean success, String code, String msg) {
		this.success = success;
		this.code = code;
		this.msg = msg;
	}

	public RemoteResult(boolean success, String code, String msg, Object data) {
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public RemoteResult setCode(String code) {
		this.code = code;
		return this;
	}

	public Object getData() {
		return data;
	}

	public RemoteResult setData(Object data) {
		this.data = data;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public RemoteResult setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public RemoteResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public static RemoteResult create(boolean success, String code, String msg, Object obj) {
		return new RemoteResult(success, code, msg, obj);
	}

	public static RemoteResult create(boolean success, String code, String msg) {
		return create(success, code, msg, null);
	}

	public static RemoteResult success() {
		return success("操作成功");
	}

	public static RemoteResult success(Object obj) {
		return create(true, "0000", "操作成功", obj);
	}

	public static RemoteResult success(String msg, Object obj) {
		return create(true, "0000", msg, obj);
	}

	public static RemoteResult failure() {
		return failure("操作失败");
	}

	public static RemoteResult failure(String msg) {
		return create(false, "-1", msg);
	}

	public static RemoteResult failure(Exception ex) {
		return failure("异常：" + ex.getMessage(), ex);
	}

	public static RemoteResult failure(String message, Exception ex) {
		if (ex == null) {
			return failure();
		}
		RemoteResult msg = failure(message);
		try {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			msg.setData(sw.toString());
		} catch (Exception e) {
		}
		return msg;
	}
}
