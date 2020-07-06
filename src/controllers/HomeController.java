package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: jenifer
 * Date: 7/6/2020
 * Time: 7:47 PM
 * Project: Medical
 **/
@Controller
@RequestMapping(value ={"/home"})
public class HomeController {
    @RequestMapping(value = {"/index"})
    public ModelAndView home(HttpSession session)
    {

        ModelAndView view = new ModelAndView("../index.jsp");
        if(null!= session.getAttribute("user"))
        {
            return view;
        }
        else
        {
            view.setViewName("../login.jsp");
            return view;
        }
    }
}
