package com.mc.businesslogic;

import java.util.List;
import javax.ejb.Remote;
 
import com.mc.entities.Attachments;
 
@Remote
public interface IAttachments {
    void saveAttachments(Attachments Attachments);
    Attachments findAttachments(Attachments Attachments);
    List<Attachments> retrieveAllAttachmentss();
}
