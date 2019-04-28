package final221.contactList;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;

import final221.database.customerBean;
import final221.database.DBAccessClass;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class contactListServlet extends HttpServlet {
        
    @Override
    protected void doPost(HttpServletRequest request, 
                          HttpServletResponse response) 
                          throws ServletException, IOException {
        //if session do not exist, create a session object
        HttpSession session = request.getSession(true);
        //get the date when session built


        
        String url = "/purchase.jsp";
        

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "buy";  // default action
        }
        

            if (action.equals("purchase")) {
                if (session.isNew()){ 
                    url = "/contact.html";    // the "join" page
                }
                else{
                    url = "/thanks.jsp";
                }
            }
            else if (action.equals("add")) {                
                // get parameters from the request
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String phoneNumber = request.getParameter("phoneNumber");
                String email = request.getParameter("email");

                // store data in User object and save User object in database
                customerBean user = new customerBean(firstName, lastName,phoneNumber, email);

                // UserDB.insert(user);
            
                // set User object in request object and set URL
                request.setAttribute("user", user);
                session.setAttribute("user", user);

                url = "/thanks.jsp";   // the "thanks" page
            
            }
            /*
                else if (action.equals("join")){
                
                session.setMaxInactiveInterval(0);
                url = "/purchase.jsp";
                
            }
            */
            
            else if (action.equals("buy")){
                
                url = "/purchase.jsp";
                
            }
 
            // perform action and set URL to appropriate page
       
        
  
        
        // forward request and response objects to specified URL
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }    
    
    @Override
    protected void doGet(HttpServletRequest request, 
                          HttpServletResponse response) 
                          throws ServletException, IOException {
        doPost(request, response);
       
    }    
    


}

