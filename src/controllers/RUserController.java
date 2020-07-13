package controllers;

import com.google.gson.Gson;
import dao.DBHelper;
import models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jenifer
 * Date: 7/4/2020
 * Time: 8:59 PM
 * Project: Medical
 **/
@Controller
@RequestMapping(value ={"/ruser"})
public class RUserController {
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView userAdd(User userinfo,HttpSession session)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg","");
        MappingJackson2JsonView retJson = new MappingJackson2JsonView();
        try
        {
//            if(null == session.getAttribute("user"))   //未登录，
//            {
//                map.replace("success",false);
//                map.replace("msg","用户未登录，请先登录。");
//
//            }
            if(false)
            {}
            else
            {
                if (userinfo == null) {
                map.replace("success",false);
                map.replace("msg","User information parameters error.");
            } else {
                    //assert userinfo != null;
                    if (DBHelper.addUser(userinfo)) {
                        map.replace("success", true);
                        map.replace("msg", "Add userinfo successfully.");
                    } else {
                        map.replace("success", false);
                        map.replace("msg", "Add userinfo failed.");
                    }
                }

            }
        }catch (Exception e)
        {
            map.replace("success",false);
            map.replace("msg","internal error.");
        }
        ModelAndView retView = new ModelAndView(retJson,map);
        return retView;
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ModelAndView userUpdate(User userinfo,HttpSession session)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg","");
        MappingJackson2JsonView retJson = new MappingJackson2JsonView();
        try
        {
//            if(null == session.getAttribute("user"))   //未登录，
//            {
//                map.replace("success",false);
//                map.replace("msg","用户未登录，请先登录。");
//
//            }
            if(false)
            {}
            else
            {
                if (userinfo == null) {
                    map.replace("success",false);
                    map.replace("msg","User information parameters error.");
                } else {
                    //assert userinfo != null;
                    //do update user
                    if (DBHelper.updateUser(userinfo)) {
                        map.replace("success", true);
                        map.replace("msg", "Update userinfo successfully.");
                    } else {
                        map.replace("success", false);
                        map.replace("msg", "Update userinfo failed.");
                    }


                }
            }
        }catch (Exception e)
        {
            map.replace("success",false);
            map.replace("msg","internal error.");
        }
        ModelAndView retView = new ModelAndView(retJson,map);
        return retView;
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public ModelAndView userDelete(User userinfo,HttpSession session)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg","");
        MappingJackson2JsonView retJson = new MappingJackson2JsonView();
        try
        {
//            if(null == session.getAttribute("user"))   //未登录，
//            {
//                map.replace("success",false);
//                map.replace("msg","用户未登录，请先登录。");
//
//            }
            if(false)
            {}
            else
            {
                if (userinfo == null) {
                    map.replace("success",false);
                    map.replace("msg","User information parameters error.");
                } else {
                    //assert userinfo != null;
                    //do delete user
                    if (DBHelper.deleteUser(userinfo)) {
                        map.replace("success", true);
                        map.replace("msg", "Delete userinfo successfully.");
                    } else {
                        map.replace("success", false);
                        map.replace("msg", "Delete userinfo failed.");
                    }
                }
            }
        }catch (Exception e)
        {
            map.replace("success",false);
            map.replace("msg","internal error.");
        }
        ModelAndView retView = new ModelAndView(retJson,map);
        return retView;
    }
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getUsers(User user,HttpSession session,HttpServletResponse response)
    {
        response.setHeader("Access-Control-Allow-Credentials", "true");       // 是否支持cookie跨域
        response.setHeader("Access-Control-Allow-Origin","*");
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg","");
        try
        {
//            if(null == session.getAttribute("user"))   //未登录，
//            {
//                map.replace("success",false);
//                map.replace("msg","用户未登录，请先登录。");
//
//            }
            if(false)
            {

            }
            else {
                map.replace("success", true);
                map.replace("msg", "Get users successfully");
                map.put("data", DBHelper.getUsers());
            }
        }
        catch (Exception e)
        {
            map.replace("success",false);
            map.replace("msg","internal error.");
        }
        ModelAndView retView = new ModelAndView(new MappingJackson2JsonView(),map);
        return retView;
    }

}
