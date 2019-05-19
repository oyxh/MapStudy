package com.oyxh.map.controller;

import org.springframework.stereotype.Controller;

import com.oyxh.map.common.utils.ShiroUtils;
import com.oyxh.map.domain.UserDO;


@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}

}