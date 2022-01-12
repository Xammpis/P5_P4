package com.P5.repositories;

import com.P5.entities.Delegacion;
import com.P5.utils.Dialog;
import org.hibernate.Session;
import com.P5.db.DBConnection;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class DelegacionRepository {

    public static List<Delegacion> getAll() {
        Session session = DBConnection.getSession();
        session.beginTransaction();
        List<Delegacion> delegaciones = session.createQuery("FROM Delegacion", Delegacion.class).list();
        session.close();

        return delegaciones;
    }

    public static Delegacion save(Delegacion delegacion) throws ConstraintViolationException {
        Session session = DBConnection.getSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(delegacion);
        transaction.commit();
        session.close();

        return delegacion;
    }

    public static void delete(Delegacion delegacion) {
        Session session = DBConnection.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(delegacion);
        transaction.commit();
        session.close();
    }
}
