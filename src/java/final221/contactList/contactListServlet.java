/**
 * CMPSC 221 Final Program
 * CustomerBean.java
 * Purpose: The controller in MVC architecture. Deals with sending the user to
 * views and using the model classes.
 * 
 * @author Nicholas Hutton
 * @author Yuxin Liu
 * @version 1.3 4/30/2019
 */
package final221.contactList;

import java.io.*;
import javax.servlet.*;
import java.util.Date;

import final221.database.CustomerBean;
import final221.database.ProductBean;
import final221.database.OrderBean;
import final221.database.TableAccess;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class contactListServlet extends HttpServlet {
        
    @Override
    protected void doPost(HttpServletRequest request, 
                          HttpServletResponse response) 
                          throws ServletException, IOException {

        String url = "/productListPage.jsp";
        

        // get current action
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "buy";  // default action
        }

        // get the action from the order page, grab its attributes, and try to do some stuff in the db. Then try to send the user to the thanks page
        if (action.equals("purchase")) {
            String numPurchased = request.getParameter("quantity");
            String prodID = request.getParameter("prodID");
            request.setAttribute("quantity", numPurchased);
            request.setAttribute("prodID", prodID);
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phoneNumber = request.getParameter("phoneNumber");
            String email = request.getParameter("email");
            int id = 0;
            // make Integer versions of the quantity and product ID for the product table update function
            Integer numBought = new Integer(numPurchased);
            Integer productID = new Integer(prodID);

            // store data in User object and save User object in database
            CustomerBean user = new CustomerBean(id, firstName, lastName, phoneNumber, email);
            TableAccess userDB = new  TableAccess();
            userDB.customerInsert(user);
            // product table update function. doesnt work
            userDB.productSale(numBought, productID);
            // get everything ready for making an order object
            user = userDB.customerSearch(lastName);
            ProductBean product = userDB.productSearch(prodID);
            Date date = new Date();
            // attempt to make an order object
            OrderBean order = new OrderBean(0, date.getTime(), user, product, numBought);
            // causes server error, NullPointerException
            //  userDB.orderInsert(order);

            // set User object, quantity, and product id in request object and set URL
            request.setAttribute("user", user);
            request.setAttribute("number", numPurchased);
            request.setAttribute("prodID", prodID);

            url = "/thanks.jsp";   // the "thanks" page


        } else if (action.equals("buy")){
                
                url = "/productListPage.jsp";
                
            }
        
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

