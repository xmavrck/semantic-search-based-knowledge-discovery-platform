package com.sarah.web_crawler.controllers.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The class ViewController
 * @author chandan
 */
@Controller
public class ViewController {

	/**
	 * uploadZipFile
	 * 
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/upload-files")
	public String uploadZipFile() throws Exception {
		return "testupload";
	}

}
