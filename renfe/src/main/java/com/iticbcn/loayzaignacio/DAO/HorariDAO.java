package com.iticbcn.loayzaignacio.DAO;

import com.iticbcn.loayzaignacio.Classes.Horari;
import org.hibernate.SessionFactory;

public class HorariDAO extends GenericDAO {

    public HorariDAO(SessionFactory sessionFactory) {
        super(Horari.class, sessionFactory);
    }
}
