package com.example.yyh.test3.com.example.yyh.utils;

import java.io.Serializable;

/**
 * 用于传输的数据类
 *
 */
public class TransmitBean implements Serializable{

	private String msg = "";

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

}
