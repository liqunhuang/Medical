package controllers;

import com.google.gson.Gson;
import dao.DBHelper;
import models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: jenifer
 * Date: 7/4/2020
 * Time: 8:59 PM
 * Project: Medical
 **/
@Controller
@RequestMapping(value ={"/user"})
public class UserController {
//    @RequestMapping("/login")
//    public void login(HttpServletRequest request, HttpServletResponse response)
//    {
//
////        ModelAndView view  = new ModelAndView("/login.jsp");
////        return view;
//
//        Hashtable ht = new Hashtable();
//        ht.put("success",false);
//        ht.put("message","Login failed!");
//        Gson gson = new Gson();
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        if(username==null || password==null)
//        {
//            ht.replace("success",false);
//            ht.replace("message","参数错误");
//        }
//        else {
//            if (username.isEmpty() || password.isEmpty()) {
//                ht.replace("success",false);
//                ht.replace("message","用户名和密码不能为空");
//            }
//            else
//            {
//                try {
//                    //查数据 库
//                    User user =  DBHelper.getUser(username);
//                    if(user.getUsername()==null || user.getUsername().isEmpty())
//                    {
//                        ht.replace("success",false);
//                        ht.replace("message","User not exist");
//                    }
//                    else
//                    {
//                        if(user.getPassword().equals(password))
//                        {
//                            ht.replace("success",true);
//                            ht.replace("message","Login OK");
//                            //  Set Session
//                        }
//                        else
//                        {
//                            ht.replace("success",false);
//                            ht.replace("message","Password Error");
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//            }
//            }
//        }
//        try {
//            String result = gson.toJson(ht);
//            response.getWriter().write(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    @RequestMapping(value = {"/login"})
    public void login(User user, HttpSession session, HttpServletResponse response)
    {
        Hashtable ht = new Hashtable();
        ht.put("success",false);
        ht.put("message","Login failed!");
        Gson gson = new Gson();

        String username = user.getUsername();
        String password = user.getPassword();
        if(username==null || password==null)
        {
            ht.replace("success",false);
            ht.replace("message","参数错误");
        }
        else {
            if (username.isEmpty() || password.isEmpty()) {
                ht.replace("success",false);
                ht.replace("message","用户名和密码不能为空");
            }
            else
            {
                try {
                    //查数据 库
                    User userDb =  DBHelper.getUser(username);
                    if(userDb.getUsername()==null || userDb.getUsername().isEmpty())
                    {
                        ht.replace("success",false);
                        ht.replace("message","User not exist");
                    }
                    else
                    {
                        if(userDb.getPassword().equals(password))
                        {
                            ht.replace("success",true);
                            ht.replace("message","Login OK");
                            //  Set Session
                            session.setAttribute("user",userDb);
                        }
                        else
                        {
                            ht.replace("success",false);
                            ht.replace("message","Password Error");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            String result = gson.toJson(ht);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(result);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = {"/adduser"})
    public void addUser(User userinfo, HttpSession session, HttpServletResponse response)
    {
        Hashtable ht = new Hashtable();
        ht.put("success",false);
        ht.put("message","");
        Gson gson = new Gson();
        response.setContentType("text/html;charset=utf-8");
        try
        {
            if(null == session.getAttribute("user"))   //未登录，
            {
                ht.replace("success",false);
                ht.replace("message","用户未登录，请先登录。");
                response.getWriter().write(gson.toJson(ht));
                return;
            }
            else
            {
                //插入用户到数据库，先判断新插入的用户是否已存在。
                if(DBHelper.isUserExist(userinfo.getUsername()))
                {
                    ht.replace("success",false);
                    ht.replace("message","该用户已存在，请重试。");
                    response.getWriter().write(gson.toJson(ht));
                    return;
                }
                if(DBHelper.addUser(userinfo))
                {
                    ht.replace("success",true);
                    ht.replace("message","添加用户成功。");
                    response.getWriter().write(gson.toJson(ht));
                    return;
                }
                else
                {
                    ht.replace("success",false);
                    ht.replace("message","添加用户失败，请重试。");
                    response.getWriter().write(gson.toJson(ht));
                    return;
                }
            }
        }
        catch (Exception e)
        {
            ht.replace("success",false);
            ht.replace("message","添加用户失败，请重试。");
            try {
                response.getWriter().write(gson.toJson(ht));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        }
    }
}
