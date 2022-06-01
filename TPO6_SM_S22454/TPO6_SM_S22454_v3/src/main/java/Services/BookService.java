package Services;

import entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;

public class BookService {

    SessionFactory sessionFactory;

    public BookService(){
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();
    }

    public ArrayList<Book> getBookList(){
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            ArrayList<Book> list = (ArrayList<Book>) session.createQuery("FROM Book").getResultList();
            session.close();
            return list;
        }
    }

    public Book getBook(String title){
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            Book book = null;

            if (session.createQuery("FROM Book WHERE title = '" + title + "'").getResultList().size() > 0)
                book = (Book) session.createQuery("FROM Book WHERE title = '" + title + "'").getSingleResult();

            session.close();
            return book;
        }
    }
}
