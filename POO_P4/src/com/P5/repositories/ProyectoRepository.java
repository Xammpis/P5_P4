package com.P5.repositories;

import com.P5.db.DBConnection;
import com.P5.entities.Proyecto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class ProyectoRepository {

    public static List<Proyecto> getAll() {
        Session session = DBConnection.getSession();
        session.beginTransaction();
        List<Proyecto> proyectos = session.createQuery("FROM Proyecto", Proyecto.class).list();
        session.close();

        return proyectos;
    }

    public static Proyecto save(Proyecto proyecto) throws ConstraintViolationException {
        Session session = DBConnection.getSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(proyecto);
        transaction.commit();
        session.close();
        return proyecto;
    }

    public static void delete(Proyecto proyecto) {
        Session session = DBConnection.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(proyecto);
        transaction.commit();
        session.close();
    }
}
