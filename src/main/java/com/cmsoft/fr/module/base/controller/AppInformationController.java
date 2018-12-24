package com.cmsoft.fr.module.base.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/app/info")
public class AppInformationController {

	@GetMapping("/version")
	public String getVersion() {
		return "{" + " 0.0.0-{11122018-1554}" + "}";
	}
}
