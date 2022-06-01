package Services;

import entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookService {

    SessionFactory sessionFactory;

    public BookService(){
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();
    }

    public Book getBook(int id){
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            Book book = (Book) session.createQuery("FROM Book WHERE Id = 1").getSingleResult();
            return book;
        }
    }
}
