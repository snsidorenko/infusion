<%@ page import="com.realfuturecrm.servlet.infusion.InfusionSignUtils" %>
<%@ page import="org.apache.xmlrpc.client.XmlRpcClientConfigImpl" %>
<%@ page import="java.net.URL" %>
<%@ page import="org.apache.xmlrpc.client.XmlRpcClient" %>
<%@ page import="java.util.*" %>
<%@ page import="org.joda.time.LocalDate" %>
<%@ page import="com.realfuture.model.Lead2" %>
<%@ page import="org.apache.commons.beanutils.PropertyUtils" %>
<%@ page import="java.lang.reflect.InvocationTargetException" %>
<%@ page import="com.realfuture.model.Contact2" %>
<%@ page import="com.realfuture.model.PostalAddress" %>
<%@ page import="com.realfuture.model.PersonName" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.realfuture.service.IContactService" %>
<%!
    class Mapping{
        String infusion;
        String rf;
        String value;
        public boolean hasField(){ return rf != null;}
        public Mapping(String infusion, String rf, String value) {
            this(infusion, rf);
            this.value = value;
        }

        public Mapping(String infusion, String rf) {
            this.infusion = infusion;
            this.rf = rf;
        }
    }

    Map toImport(Mapping[] fields, Lead2 lead){
        Map contactData = new HashMap();
        for(Mapping m : fields){
            if(m.hasField()){
                try {
                    Object value = PropertyUtils.getProperty(lead, m.rf);
                    if(value != null)
                        contactData.put(m.infusion, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            } else {
                if(m.value != null)
                    contactData.put(m.infusion, m.value);
            }
        }
// assign new contact for user.id=54
        contactData.put("OwnerID", 54);
        return contactData;
    }
%>
<%
    Mapping[] mfields = new Mapping[]{
            new Mapping("Address1Type",null,"Primary Address"),
            new Mapping("Address2Type",null,"Secondary Address"),
            new Mapping("Address2Street1","contact.postalAddress2.street"),
    new Mapping("Address2Street2","contact.postalAddress2.aptUnit"),
    new Mapping("Anniversary","contact.anniversaryDate"),
    new Mapping("Birthday","contact.birthday"),
    new Mapping("City","contact.postalAddress.city"),
    new Mapping("City2","contact.postalAddress2.city"),
    new Mapping("ContactNotes","contact.note"),
    new Mapping("Country","contact.postalAddress.country"),
    new Mapping("Country2","contact.postalAddress2.country"),
    new Mapping("Email","contact.email1"),
    new Mapping("EmailAddress2","contact.email2"),
    new Mapping("EmailAddress3","contact.spouseemail"),
    new Mapping("Fax1","contact.fax"),
    new Mapping("FirstName","contact.personName.fname"),
    new Mapping("JobTitle","contact.employerInfo.empposition"),
    new Mapping("Language","contact.language"),
    new Mapping("LastName","contact.personName.lname"),
    new Mapping("Leadsource","contact.source"),
    new Mapping("MiddleName","contact.personName.mi"),
    new Mapping("Phone1","contact.preferedPhone"),
    new Mapping("Phone2","contact.hphone"),
    new Mapping("Phone2Type",null,"Home"),
    new Mapping("Phone3","contact.cphone"),
    new Mapping("Phone3Type",null,"Mobile"),
    new Mapping("Phone4","contact.wphone"),
    new Mapping("Phone4Type",null,"Work"),
    new Mapping("Phone5","contact.hphone2"),
    new Mapping("Phone5Type",null,"Other"),
    new Mapping("PostalCode","contact.postalAddress.zip"),
    new Mapping("PostalCode2","contact.postalAddress2.zip"),
    new Mapping("SpouseName","contact.spouseName.name"),
    new Mapping("State","contact.postalAddress.state"),
    new Mapping("State2","contact.postalAddress2.state"),
    new Mapping("StreetAddress1","contact.postalAddress.street"),
    new Mapping("StreetAddress2","contact.postalAddress.aptUnit"),
    new Mapping("Title","contact.personName.title"),
    new Mapping("Website","contact.clientsite"),
    new Mapping("ZipFour1","zip4")
    };
    Contact2 contact = new Contact2();
    String key = InfusionSignUtils.clientId();
    String token = InfusionSignUtils.authToken();
    XmlRpcClientConfigImpl conf = new XmlRpcClientConfigImpl();
    conf.setServerURL(new URL("https://api.infusionsoft.com/crm/xmlrpc/v1?access_token=" + token));
    XmlRpcClient client = new XmlRpcClient();
    client.setConfig(conf);
    if(token != null){
        if("POST".equals(request.getMethod().toUpperCase())){
            Integer contactId = null;
            try {
                Lead2 lead = new Lead2();

                contact.getPersonName().setFname(request.getParameter("first"));
                contact.getPersonName().setLname(request.getParameter("last"));
                contact.setEmail1(request.getParameter("email"));
                contact.setPostalAddress(new PostalAddress("1", "12", "Minnetonka", "bld12", "Minnetonka", "MN", "50098", "US"));
                contact.setPostalAddress2(new PostalAddress("4", "90", "Backer Str", "210", "London", "AL", "10293", "GB"));
                contact.setAnniversaryDate(LocalDate.now().minusYears(10).toDate());
                contact.setBirthday(LocalDate.now().minusYears(30).minusMonths(2).toDate());
                contact.setNote("simply note");
                contact.setEmail2("email@mm.cc");
                contact.setSpouseemail("lpalmer@mm.cc");
                contact.setFax("+37529456789");
                contact.getEmployerInfo().setEmpposition("enginer");
                contact.setLanguage("Albanian");
                contact.setSource("Adigida");
                contact.setPreferedPhone("+37529456781");
                contact.setHphone("+37529456782");
                contact.setCphone("+37529456783");
                contact.setWphone("+37529456784");
                contact.setHphone2("+37529456785");
                contact.setSpouseName(new PersonName("Lora", "", "Palmer"));
                contact.getPersonName().setTitle("Dr.");
                contact.setClientsite("http://tut.by");
                lead.setZip4("4001");
                lead.setContact(contact);
                List parameters = new ArrayList();
                Map contactData = toImport(mfields, lead);
                parameters.add(key); //The secure key
                parameters.add(contactData); //The data to be added
                contactId = (Integer) client.execute("ContactService.add", parameters);
                out.print(contactId);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        try {
// get users list
            List fields = new ArrayList(); //What fields we will be selecting
            fields.add("Email");
            fields.add("FirstName");
            fields.add("LastName");
            fields.add("GlobalUserId");
            fields.add("Id");

            List parameters = new ArrayList();
            parameters.add(key); //The secure key
            parameters.add("User");
            parameters.add(1000);
            parameters.add(0);

            Map contactData = new HashMap();
            contactData.put("Email", "%@%");
            parameters.add(contactData);
            parameters.add(fields);
            Object[] contacts = (Object[])client.execute("DataService.query", parameters);
            System.out.println(contacts.length);
            for (Object o : contacts) {
                Map mcontact = (Map) o;
                for(Object f : fields)
                    out.print(mcontact.get(f) + " ");
                out.println("<br/>");
            }
        } catch(Exception e) {
            out.print(e.getMessage());
        }
    }
%>
<style>
    label{padding-right:20px; min-width:200px; width: 210px;}
</style>
<form method="post" action="test.jsp">
    <label>First Name</label><input name="first" value="Slava"><br/>
    <label>Last Name</label><input name="last" value="Losev"><br/>
    <label>Email</label><input name="email" value="slosev@mmc.cc"><br/>
    <input type="submit"/>
</form>
<div><a href="<%=InfusionSignUtils.oauthUrl("12345aaa")%>" target="_blank">reload setting</a></div>
<% ApplicationContext ctx = (ApplicationContext)application.getAttribute("appcontext");
    IContactService serv = ctx.getBean("contactService", IContactService.class);
    out.print(serv.findAll());
    contact = serv.save(contact);
    out.print("size:=" + serv.findAll().size() + "...ID=" + contact.getId());
%>