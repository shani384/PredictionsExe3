package servlet.admin;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.UserManager;
import utils.Constants;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;

import static utils.Constants.USERNAME;


@WebServlet("/admin/login")
public class AdminLogin extends HttpServlet {
    // urls that starts with forward slash '/' are considered absolute
    // urls that doesn't start with forward slash '/' are considered relative to the place where this servlet request comes from
    // you can use absolute paths, but then you need to build them from scratch, starting from the context path
    // ( can be fetched from request.getContextPath() ) and then the 'absolute' path from it.
    // Each method with it's pros and cons...

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        if(userManager.isAdminLoggedIn()){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getOutputStream().print("one admin is already logged in");
            System.out.println("admin try to log in - denied");
        }
        else {
            System.out.println("admin try to log in - authorized");
            userManager.adminLogin();
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }



}
