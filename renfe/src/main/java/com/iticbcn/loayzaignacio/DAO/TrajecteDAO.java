package com.iticbcn.loayzaignacio.DAO;

import com.iticbcn.loayzaignacio.Classes.Trajecte;
import org.hibernate.SessionFactory;

public class TrajecteDAO extends  GenericDAO{

    public TrajecteDAO(SessionFactory sessionFactory) {
        super(Trajecte.class, sessionFactory);
    }
}
