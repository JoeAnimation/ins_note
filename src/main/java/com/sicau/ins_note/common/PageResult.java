package com.sicau.ins_note.common;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageResult{

	private static int pageSize;

	public static int getPageSize() {
		return pageSize;
	}

	static {
		Properties properties = new Properties();
		try {
			properties.load(PageResult.class.getClassLoader().getResourceAsStream("property/page.properties"));
			pageSize = Integer.valueOf(properties.getProperty("page_size"));
		} catch (Exception e) {
			log.error("出错啦，具体信息："+e.getMessage());
		}
	}

}
