package com.pankaj.myfancypdfinvoices.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pankaj.myfancypdfinvoices.context.ApplicationConfiguration;
import com.pankaj.myfancypdfinvoices.model.Invoice;
import com.pankaj.myfancypdfinvoices.services.InvoiceService;
import com.pankaj.myfancypdfinvoices.services.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MyFancyPdfInvoiceServlet extends HttpServlet {

    //private final InvoiceService invoiceService = new InvoiceService();
    //private final ObjectMapper objectMapper = new ObjectMapper();

    private UserService userService;
    private InvoiceService invoiceService;
    private ObjectMapper objectMapper;


    @Override
    public void init() throws ServletException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        this.userService =ctx.getBean(UserService.class);
        this.invoiceService = ctx.getBean(InvoiceService.class);
        this.objectMapper = ctx.getBean(ObjectMapper.class);

        ctx.registerShutdownHook();

//        System.out.println(ctx.getBean(UserService.class));
//        System.out.println(ctx.getBean(UserService.class));
//        System.out.println(ctx.getBean(UserService.class));
//
//        System.out.println(ctx.getBean(InvoiceService.class).getUserService());
//        System.out.println(ctx.getBean(InvoiceService.class).getUserService());
//        System.out.println(ctx.getBean(InvoiceService.class).getUserService());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getRequestURI().equalsIgnoreCase("/invoices")) {
            String userId = req.getParameter("user_id");
            Integer amount = Integer.valueOf(req.getParameter("amount"));

            Invoice invoice = invoiceService.create(userId, amount);
            resp.setContentType("application/json;charset=UTF-8");
            String json = objectMapper.writeValueAsString(invoice);
            resp.getWriter().print(json);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().equalsIgnoreCase("/")) {

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(
                    "<html>\n" +
                            "<body>\n" +
                            "<h1>Hello World</h1>" +
                            "<p>This is my very first, embedded Tomcat, pankaj, HTML Page!</p>" +
                            "</body>\n" +
                            "</html>"
            );

        } else if (request.getRequestURI().equalsIgnoreCase("/invoices")) {
            response.setContentType("application/json; charset=UTF-8");
            List<Invoice> invoices = invoiceService.findAll();
            response.getWriter().print(objectMapper.writeValueAsString(invoices));

        }
    }
}
