package com.realfuturecrm.servlet.infusion;
import com.realfuture.model.Contact2;
import com.realfuture.service.ContactService;
import com.realfuture.service.IContactService;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class InfusionServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String code = req.getParameter("code");
        String state = req.getParameter("state");//abId
        Map<String, Object> map = InfusionSignUtils.paramsMap(false);
        JSONObject json = null;
        try {
            json = WS.POST(map, InfusionSignUtils.accountUrl(), "client_id=" + InfusionSignUtils.clientId() + "&client_secret=" + InfusionSignUtils.secret() +"&grant_type=authorization_code&code=" + code + "&redirect_uri=" + InfusionSignUtils.redirectURL()).getJSONObject();
            InfusionSignUtils.storeSetting(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(json != null){
            try {
                System.out.println(json);
            } catch(Exception e) {
            }
        }
    }

    public void init(){
        ApplicationContext ctx = new GenericXmlApplicationContext("classpath:META-INF/spring/persistance.xml");
        IContactService service = ctx.getBean("contactService", IContactService.class);
        List<Contact2> all = service.findAll();
        System.out.print(all.size());
        getServletContext().setAttribute("contactService", service);
        getServletContext().setAttribute("appcontext", ctx);
    }
}