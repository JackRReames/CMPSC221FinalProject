package final221.contactList;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;

import final221.database.CustomerBean;
import final221.database.ProductBean;
import final221.database.TableAccess;
import java.util.List;
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
//        HttpSession session = request.getSession(true);
        //get the date when session built


        
        String url = "/productListPage.jsp";
        

        // get current action
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "buy";  // default action
        }
        

            if (action.equals("purchase")) {
//                if (session.isNew()){ 
                    url = "/contact.html";    // the "join" page
//                }
//                else{
//                    url = "/thanks.jsp";
//                }
//            } else if (action.equals("add")) {
//                url = "/thanks.jsp";
           }
            
            else if (action.equals("add")) {                
                // get parameters from the request
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String phoneNumber = request.getParameter("phoneNumber");
                String email = request.getParameter("email");
                int id = 0;

                // store data in User object and save User object in database
                CustomerBean user = new CustomerBean(id, firstName, lastName, phoneNumber, email);
                TableAccess userDB = new  TableAccess();
                userDB.customerInsert(user);
            
                // set User object in request object and set URL
                request.setAttribute("user", user);
//                session.setAttribute("user", user);

                url = "/thanks.jsp";   // the "thanks" page
            
            }
            
            /*
                else if (action.equals("join")){
                
                session.setMaxInactiveInterval(0);
                url = "/purchase.jsp";
                
            }
            */
            
            else if (action.equals("buy")){
                
                url = "/productListPage.jsp";
                
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

