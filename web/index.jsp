<%@page import="Connections.AuthorizationSingleton"%>
<%@page import="Connections.ConnectionSingleton"%>
<%@page import="javax.faces.context.FacesContext"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="UserBeans.Auth" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<jsp:useBean class="UserBeans.Auth" scope="request" id="auth" />

<%
            System.out.println("Welcome in index!");
                       
            if (AuthorizationSingleton.isFacesContext()) {
                AuthorizationSingleton.goToIndexPage(response);
            } else if(AuthorizationSingleton.isSessionValid(session)) {
               AuthorizationSingleton.goToWelcomePage(response);
            } else {
               AuthorizationSingleton.checkCookies(request, response, session);
                
        %>

<f:view>

    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>JSP Page</title>
        </head>
        <body>
            <h:form>
                <h:panelGrid id="lpg" columns="2">
                    <h:outputText value="Username: "/>
                    <h:inputText id="username"
                                 value="#{auth.username}"/>

                    <h:outputText value="Password: "/>
                    <h:inputSecret id="password"
                                   value="#{auth.password}" />

                    <h:commandButton action="#{auth.validate}"
                                     value = "Log"/>
                    <h:commandButton action="#{auth.newUser}"
                                     value = "Add New User"/>
                </h:panelGrid>
            </h:form>


        </body>
    </html>
</f:view>
    <% } %>
