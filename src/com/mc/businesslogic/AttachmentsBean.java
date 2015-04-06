package com.mc.businesslogic;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
 
import com.mc.businesslogic.IAttachments;
import com.mc.entities.Attachments;
 
@Stateless
public class AttachmentsBean implements IAttachments {
 
    @PersistenceContext(unitName = "JPADB")
    private EntityManager entityManager;
     
    public AttachmentsBean() {   }
 
    @Override
    public void saveAttachments(Attachments Attachments) {
        entityManager.persist(Attachments);
    }
 
    @Override
    public Attachments findAttachments(Attachments Attachments) {
        Attachments p = entityManager.find(Attachments.class, Attachments.getPcnt_no());
        return p;
    }
 
    @Override
    public List<Attachments> retrieveAllAttachmentss() {
         
        String q = "SELECT a from " + Attachments.class.getName() + " a";
        Query query = entityManager.createQuery(q);
        List<Attachments> Attachmentss = query.getResultList();
        return Attachmentss;
    }
}