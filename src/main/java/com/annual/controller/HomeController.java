package com.annual.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author xhb
 * @Description:
 * @date 2020/12/10 15:55
 */
@Controller
@RequestMapping()
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @GetMapping("/home")
    public String home(){
        return "home";
    }
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/prize")
    public String prize(){
        return "prize";
    }


    @RequestMapping("/getSessionId")
    @ResponseBody
    public String getSessionId(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            return session.getId();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("getSessionId异常");
        }
        return null;
    }
}
