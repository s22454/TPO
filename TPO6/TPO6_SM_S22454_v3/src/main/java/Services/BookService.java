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

    public String bookListToHTML(){

        ArrayList<Book> books = this.getBookList();
        StringBuilder result = new StringBuilder();

        int numberOfFullRows        = Math.round(books.size() / 3);
        int numberOfBooksInLastRow  = books.size() - (numberOfFullRows * 3);

        for (int i = 0; i < numberOfFullRows; i++) {
            result.append("""
                          <div class="row">

                          		<div class="book">

                          		    <p class="bookTitle">
                          		        """ + books.get(i * 3).getTitle() + """
                                    <p>

                                    <p class="author">
                                        Autor: """ + " " + books.get(i * 3).getAuthor() + """
                                    </p>
                                    
                                    <p class="releaseDate">
                                        Data wydania: """ + " " + books.get(i * 3).getReleaseDate() + """
                                    </p>

                                    <p class="bookDescription">
                                        """ + books.get(i * 3).getDescription() + """
                                    </p>

                                </div>

                                <div style="padding-left: 5%; display: inline-block"></div>

                                <div class="book">

                                    <p class="bookTitle">
                                        """ + books.get((i * 3) + 1).getTitle() + """
                                    <p>
                                    
                                    <p class="author">
                                        Autor: """ + " " + books.get((i * 3) + 1).getAuthor() + """
                                    </p>
                                    
                                    <p class="releaseDate">
                                        Data wydania: """ + " " + books.get((i * 3) + 1).getReleaseDate() + """
                                    </p>

                                    <p class="bookDescription">
                                        """ + books.get((i * 3) + 1).getDescription() + """
                                    </p>

                                </div>

                                <div style="padding-left: 5%; display: inline-block"></div>

                                <div class="book">

                                    <p class="bookTitle">
                                        """ + books.get((i * 3) + 2).getTitle() + """
                                    <p>
                                    
                                    <p class="author">
                                        Autor: """ + " " + books.get((i * 3) + 2).getAuthor() + """
                                    </p>
                                    
                                    <p class="releaseDate">
                                        Data wydania: """ + " " + books.get((i * 3) + 2).getReleaseDate() + """
                                    </p>
                                    
                                    <p class="bookDescription">
                                        """ + books.get((i * 3) + 2).getDescription() + """
                                    </p>

                                </div>

                          </div>
            """);
        }

        result.append("""
                          <div class="row">
                      """);

        for (int i = books.size() - numberOfBooksInLastRow; i < books.size(); i++){
            result.append("""
                            <div class="book">

                                <p class="bookTitle">
                                    """ + books.get(i).getTitle() + """
                                <p>
                                
                                <p class="author">
                                    Autor: """ + " " + books.get(i).getAuthor() + """
                                </p>
                                    
                                <p class="releaseDate">
                                    Data wydania: """ + " " + books.get(i).getReleaseDate() + """
                                </p> 

                                <p class="bookDescription">
                                    """ + books.get(i).getDescription() + """
                                </p>

                            </div>

                            <div style="padding-left: 5%; display: inline-block"></div>
                          """);
        }

        result.append("""
                      </div>
                      """);

        return result.toString();
    }
}
