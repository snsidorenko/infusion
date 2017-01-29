package com.realfuture.service;

import com.realfuture.model.Contact2;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

@Repository
@Transactional
@Service("contactService")
public class ContactService implements IContactService {
    @PersistenceContext(type= PersistenceContextType.EXTENDED)
    EntityManager em;

    public Contact2 findById(Long id){
        return em.find(Contact2.class, id);
    }

    public List<Contact2> findAll(){
        return em.createQuery("select c from Contact2 c").getResultList();
    }

    public Contact2 save(Contact2 c){
        if(c.getId() == null)
            em.persist(c);
        else
            c = em.merge(c);
        return c;
    }
}
