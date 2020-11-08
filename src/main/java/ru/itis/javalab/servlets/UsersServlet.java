package ru.itis.javalab.servlets;

import ru.itis.javalab.models.User;
import ru.itis.javalab.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private UsersService usersService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext servletContext = servletConfig.getServletContext();
        this.usersService = (UsersService) servletContext.getAttribute("usersService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .id(1L)
                .UUID(UUID.randomUUID().toString())
                .password("123456")
                .email("java@mail.ru")
                .build());
        users.add(User.builder()
                .id(2L)
                .UUID(UUID.randomUUID().toString())
                .password("111111")
                .email("java2@mail.ru")
                .build());

        request.setAttribute("usersForJsp", users);
        try {
            request.getRequestDispatcher("/jsp/users.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String color = request.getParameter("color");
        Cookie cookie = new Cookie("color", color);
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        try {
            response.sendRedirect("/users");
            return;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
