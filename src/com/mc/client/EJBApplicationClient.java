package com.mc.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
 
import com.mc.businesslogic.IAttachments;
import com.mc.businesslogic.AttachmentsBean;
import com.mc.clientutility.JNDILookupClass;
import com.mc.entities.Attachments;
 
public class EJBApplicationClient {
     
    public static void main(String[] args) {
    	System.out.println("What the hell is forbidden? (in EJB Client main)");
        IAttachments bean = doLookup();
         
        Attachments p1 = new Attachments();
        p1.setPcnt_no(21);
        p1.setPinsertdate("22.11.1962");
        p1.setPorg_attachment("This is at least a very logn string that goes into the database, hopefully");
         
        Attachments p2 = new Attachments();
        p2.setPcnt_no(23);
        p2.setPinsertdate("10.12.1997");
        p2.setPorg_attachment("And again: this is at least a very logn string that goes into the database, hopefully");
 
        System.out.println("End of setting up the attachements");
        // 4. Call business logic
        //Saving new Attachmentss
        /*
        bean.saveAttachments(p1);
        bean.saveAttachments(p2);
         */
        //Find a Attachments
        p1.setPcnt_no(6);
        Attachments p3 = bean.findAttachments(p1);
        System.out.println(p3);
         
        //Retrieve all Attachmentss
       System.out.println("List of Attachmentss:");
        List<Attachments> Attachmentss = bean.retrieveAllAttachmentss();
        for(Attachments Attachments : Attachmentss)
            System.out.println(Attachments);
         
         
    }
 
    private static IAttachments doLookup() {
        Context context = null;
        IAttachments bean = null;
        try {
            // 1. Obtaining Context
            context = JNDILookupClass.getInitialContext();
            // 2. Generate JNDI Lookup name
            String lookupName = getLookupName();
            // 3. Lookup and cast
            bean = (IAttachments) context.lookup(lookupName);
 
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return bean;
    }
 
    private static String getLookupName() {
    	System.out.println("Entered main");
        /*The app name is the EAR name of the deployed EJB without .ear 
        suffix. Since we haven't deployed the application as a .ear, the app 
        name for us will be an empty string */
        String appName = "";
 
        /* The module name is the JAR name of the deployed EJB without the 
        .jar suffix.*/
        String moduleName = "FirstJPAAttachments";
 
        /* AS7 allows each deployment to have an (optional) distinct name. 
        This can be an empty string if distinct name is not specified.*/
        String distinctName = "";
 
        // The EJB bean implementation class name
        String beanName = AttachmentsBean.class.getSimpleName();
 
        // Fully qualified remote interface name
        final String interfaceName = IAttachments.class.getName();
 
        // Create a look up string name
        String name = "ejb:" + appName + "/" + moduleName + "/" + 
                distinctName    + "/" + beanName + "!" + interfaceName;
        return name;
    }
}