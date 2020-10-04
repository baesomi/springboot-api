package com.prj.sm.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.prj.sm.util.*;

@Controller
public class MemberController {

	@ResponseBody
	@RequestMapping(value="/v1/member/join")
	public String join() {
		int rc = CommonConst.SUCCESS;
		
		
		return null;
	}
}
