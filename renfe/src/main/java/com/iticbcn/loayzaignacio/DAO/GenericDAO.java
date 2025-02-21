package com.iticbcn.loayzaignacio.DAO;

import java.util.List;

import org.hibernate.PropertyValueException;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;

import org.hibernate.Transaction;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;

public abstract class GenericDAO<T> implements GenericInterfaceDAO<T> {
    private Class<T> entityClass;
    private static Session session;

    public GenericDAO(Class<T> entityClass, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        if (session == null) session = sessionFactory.openSession();
    }

    public static void endSession(){
        if(session.isOpen()){
            session.close();
        }
    }

    public void save(T entity) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            System.out.println(entity.getClass().getSimpleName() + " afegida a la base de dades: " + entity);
            // Algunes Exceptions per fer el debugging
        } catch (ConstraintViolationException e) {
            System.err.println("Violación de restricción en la base de datos: " + e.getMessage());
        } catch (PropertyValueException e) {
            System.err.println("Propiedad nula detectada en: " + e.getPropertyName());
        } catch (TransientPropertyValueException e) {
            System.err.println("Entidad transitoria no persistida: " + e.getMessage());
        } catch (JDBCConnectionException e) {
            System.err.println("Problema de conexión con la base de datos: " + e.getMessage());
        } catch (QueryException e) {
            System.err.println("Error en la consulta HQL: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(T entity) {
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
            System.out.println(entity.getClass().getSimpleName() + " actualitzada a la base de dades: " + entity);
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void delete(T entity) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
            System.out.println(entity.getClass().getSimpleName() + " esborrada la base de dades");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public T findById(int id) {
        return session.get(entityClass, id);
    }

    public T findByName(String name) {
        Query<T> q = session.createQuery("FROM " + entityClass.getSimpleName() + " WHERE name = :nom", entityClass);
        q.setParameter("nom", name);
        return q.uniqueResult();
    }

    public List<T> findAll() {
        Query<T> query = session.createQuery("FROM " + entityClass.getSimpleName(), entityClass);
        return query.list();
    }

    public static List<Object[]> groupComp(){
        Query<Object[]> query = session.createQuery("SELECT c.name, COUNT(t) FROM Companyia c JOIN c.trajectescom t GROUP BY c.name", Object[].class);
        return query.list();
    }
}
