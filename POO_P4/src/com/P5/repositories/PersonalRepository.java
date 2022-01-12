package com.P5.repositories;

import com.P5.db.DBConnection;
import com.P5.entities.Personal;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class PersonalRepository {

    public static List<Personal> getAll() {
        Session session = DBConnection.getSession();
        session.beginTransaction();
        List<Personal> personal = session.createQuery("FROM Personal", Personal.class).list();
        session.close();

        return personal;
    }

    public static Personal save(Personal personal) throws ConstraintViolationException {
        Session session = DBConnection.getSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(personal);
        transaction.commit();
        session.close();

        return personal;
    }

    public static void delete(Personal personal) {
        Session session = DBConnection.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(personal);
        transaction.commit();
        session.close();
    }
}
