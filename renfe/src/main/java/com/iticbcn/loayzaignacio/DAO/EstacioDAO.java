package com.iticbcn.loayzaignacio.DAO;

import com.iticbcn.loayzaignacio.Classes.Estacio;
import org.hibernate.SessionFactory;

public class EstacioDAO extends GenericDAO{

    public EstacioDAO(SessionFactory sessionFactory) {
        super(Estacio.class, sessionFactory);
    }
}
