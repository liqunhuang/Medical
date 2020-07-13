package dao;

import models.User;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jenifer
 * Date: 7/4/2020
 * Time: 9:46 PM
 * Project: Medical
 **/
public class DBHelper {
    private static String dbuser="test";
    private static String dbpass="test";
    private static String dbDriver="com.mysql.cj.jdbc.Driver";
    private static String dbConnectStr="jdbc:mysql://localhost:3306/medical?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    public static User getUser(String username)
    {
        String sql = "SELECT\n" +
                "`操作人员`.`姓名` AS `name`,\n" +
                "`操作人员`.`密码` AS pass\n" +
                "FROM\n" +
                "`操作人员`\n" +
                "WHERE\n" +
                "`操作人员`.`姓名` = '"+username+"'";
         User user = new User();
         try {
             Class.forName(dbDriver);
             Connection conn = DriverManager.getConnection(dbConnectStr, dbuser, dbpass);
             PreparedStatement ps = conn.prepareStatement(sql);
             ps.clearParameters();
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {
                 user = new User();
                 user.setUsername(rs.getString("name"));
                 user.setPassword(rs.getString("pass"));
             }
             ps.closeOnCompletion();
             conn.close();

         }catch (Exception e)
         {

         }
        return  user;

    }

    public static boolean addUser(User user)
    {
//        String insertSql = "insert into `操作人员` values
//        (@name,@sex,@age,@keshi,@zhiwu,@tel,@pic,@passwd,@right,@ext);";
        boolean ret = false;
        String insertSql = "insert into `操作人员` values (null,?,?,?,?,?,?,?,?,?,?);";
        String sql = "select * from aaa";
        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbConnectStr, dbuser, dbpass);
            PreparedStatement ps = conn.prepareStatement(insertSql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getSex());
            ps.setInt(3,user.getAge());
            ps.setString(4,user.getDepartment());
            ps.setString(5,user.getJob());
            ps.setString(6,user.getCellphone());
            ps.setString(7,user.getPhoto());
            ps.setString(8,user.getPassword());
            ps.setString(9,user.getRight());
            ps.setString(10,user.getExt());
            int count = ps.executeUpdate();
            if(count>0)   //插入成功数>0 刚，插入成功，(count =1);
            {
                ret = true;
            }
            else
            {
                ret = false;
            }
            ps.closeOnCompletion();
            conn.close();

        }catch (Exception e)
        {
            //数据库操作出现 异常，插入未成功。
            ret = false;
        }
        return  ret;
    }

    public static boolean updateUser(User user)
    {

        boolean ret = false;
        String sql = "update `操作人员` AS tuser \n" +
                "set tuser.ID=?,\n" +
                "tuser.`姓名`=?,\n" +
                "tuser.`性别`=?,\n" +
                "tuser.`年龄`=?,\n" +
                "tuser.`科室`=?,\n" +
                "tuser.`职务`=?,\n" +
                "tuser.`电话`=?,\n" +
                "tuser.`照片`=?,\n" +
                "tuser.`密码`=?,\n" +
                "tuser.`权限`=?,\n" +
                "tuser.`备注`=?\n" +
                "where tuser.ID=?";
        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbConnectStr, dbuser, dbpass);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,user.getId());
            ps.setString(2,user.getUsername());
            ps.setString(3,user.getSex());
            ps.setInt(4,user.getAge());
            ps.setString(5,user.getDepartment());
            ps.setString(6,user.getJob());
            ps.setString(7,user.getCellphone());
            ps.setString(8,user.getPhoto());
            ps.setString(9,user.getPassword());
            ps.setString(10,user.getRight());
            ps.setString(11,user.getExt());
            ps.setInt(12,user.getId());
            int count = ps.executeUpdate();
            if(count>0)   //插入成功数>0 刚，插入成功，(count =1);
            {
                ret = true;
            }
            else
            {
                ret = false;
            }
            ps.closeOnCompletion();
            conn.close();

        }catch (Exception e)
        {
            //数据库操作出现 异常，插入未成功。
            ret = false;
        }
        return  ret;
    }
    public static boolean deleteUser(User user)
    {

        boolean ret = false;
        String sql = "delete from `操作人员` as tuser where tuser.ID="+user.getId();

        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbConnectStr, dbuser, dbpass);
            Statement st = conn.createStatement();

            int count = st.executeUpdate(sql);
            if(count>0)   //插入成功数>0 刚，插入成功，(count =1);
            {
                ret = true;
            }
            else
            {
                ret = false;
            }
            st.closeOnCompletion();
            conn.close();

        }catch (Exception e)
        {
            //数据库操作出现 异常，插入未成功。
            ret = false;
        }
        return  ret;
    }

    public static boolean isUserExist(String username)
    {
        boolean ret = false;
        String querySql = "select * from `操作人员` where `姓名`='"+username+"';";
        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbConnectStr, dbuser, dbpass);
            Statement st = conn.createStatement();
            ResultSet rt =  st.executeQuery(querySql);
            if(rt.next())
            {
                ret = true;
            }
            else
            {
                ret  = false;
            }
            st.closeOnCompletion();
            conn.close();
        }catch (Exception e)
        {
            //数据库操作出现 异常，插入未成功。
            ret = false;
        }
        return  ret;
    }

    public static ArrayList<User> getUsers()
    {
        ArrayList<User> userList = new ArrayList<User>();
        String sql = "select * from `操作人员`";
        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbConnectStr, dbuser, dbpass);
            Statement st = conn.createStatement();
            ResultSet rt =  st.executeQuery(sql);
            while (rt.next())
            {
                User u = new User();
                u.setId(rt.getInt(1));
                u.setUsername(rt.getString(2));
                u.setSex(rt.getString(3));
                u.setAge(rt.getInt(4));
                u.setDepartment(rt.getString(5));
                u.setJob(rt.getString(6));
                u.setCellphone(rt.getString(7));
                u.setPhoto(rt.getString(8));
                u.setPassword(rt.getString(9));
                u.setRight(rt.getString(10));
                u.setExt(rt.getString(11));
                userList.add(u);
            }

            st.closeOnCompletion();
            conn.close();
        }catch (Exception e)
        {
            //数据库操作出现 异常，插入未成功。
            e.printStackTrace();
        }
        return userList;
    }

}
