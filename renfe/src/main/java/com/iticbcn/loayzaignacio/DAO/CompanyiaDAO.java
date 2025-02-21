package com.iticbcn.loayzaignacio.DAO;

import com.iticbcn.loayzaignacio.Classes.Companyia;
import org.hibernate.SessionFactory;

public class CompanyiaDAO extends GenericDAO{

    public CompanyiaDAO(SessionFactory sessionFactory) {
        super(Companyia.class, sessionFactory);
    }
}
