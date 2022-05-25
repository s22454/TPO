package pjatk.tpo.tpo6_sm_s22454;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.BookModel;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "searchServlet", value = "/search")
public class SearchServlet extends HelloServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArrayList<BookModel> lista = new ArrayList<>();
        lista.add(new BookModel("Ksiazka 1", "Opis 1"));
        lista.add(new BookModel("Ksiazka 2", "Opis 2"));
        lista.add(new BookModel("Ksiazka 3", "Opis 3"));
        int[] x = new int[3];

        response.getWriter().println(
                """
                <!DOCTYPE html>
                <html lang="pl">
                 
                <head>
                 
                	<meta charset="utf-8">
                	<title>Przeglądanie książek</title>
                	
                </head>
                 
                <style>
                 
                	.body{
                		background-color: #4c566a;
                		padding-top: 50px;
                	}
                	
                	.mainSection{
                		width: 1300px;
                		margin: auto;
                	}
                	
                	.message{
                		margin: auto;
                		text-align: center;
                		padding: 10px;
                		color: #38d9a9;
                		font-size: 40px
                	}
                	
                	.row{
                		margin: auto;
                		display: flex;
                		padding-bottom: 20px;
                	}
                	
                	.book{
                		width: 400px;
                		background-color: #EDD19C;
                		display: inline-block;
                	}
                	
                	.bookTitle{
                		text-align: center;
                		font-size: 30px;
                	}
                	
                	.bookDescription{
                		padding-left: 15px;
                		padding-right: 15px;
                		font-size: 20px;
                		word-wrap: break-word;
                	}
                	
                </style>
                 
                <body class="body">
                 
                	<!-- Main section of page -->
                	<div class="mainSection">
                		
                		<!-- Message to choose what user want to do -->
                		<div class="message">
                			Dostępne książki
                		</div>
                		
                		<div style="padding: 15px"></div>
                	
                		<!-- Available books -->
                		""" +
                    bookListToHTML(lista)
                	+	"""
                	</div>
                 
                </body>
                 
                </html>
                """);

    }

    private String bookListToHTML(ArrayList<BookModel> books){

        StringBuilder result = new StringBuilder();

        int numberOfFullRows        = Math.round(books.size() / 3);
        int numberOfBooksInLastRow  = books.size() - (numberOfFullRows * 3);

        for (int i = 0; i < numberOfFullRows; i++) {
            result.append("""
                          <div class="row">
                          		
                          		<div class="book">
                          			
                          		    <p class="bookTitle"> 
                          		        """ + books.get(i * 3).getName() + """
                                    <p>
                                                                                                         				
                                    <p class="bookDescription">
                                        """ + books.get(i * 3).getDescription() + """
                                    </p>
                                                                                                                                       	
                                </div>
                                                                                                                                                             			
                                <div style="padding: 21px; display: inline-block"></div>
                                                                                                                                                             			
                                <div class="book">
                                                                                                                                                             			
                                    <p class="bookTitle">
                                        """ + books.get((i * 3) + 1).getName() + """
                                    <p>
                                                                                                                                                                                                              				
                                    <p class="bookDescription">
                                        """ + books.get((i * 3) + 1).getDescription() + """
                                    </p>
                                                                                                                                                                                                                                            	
                                </div>
                                                                                                                                                                                                                                                                  			
                                <div style="padding: 21px; display: inline-block"></div>
                                                                                                                                                                                                                                                                  			
                                <div class="book">
                                                                                                                                                                                                                                                                  			
                                    <p class="bookTitle">
                                        """ + books.get((i * 3) + 2).getName() + """
                                    <p>
                                                                                                                                                                                                                                                                                                                   				
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
                                    """ + books.get(i).getName() + """
                                <p>
                                                                                                                                   				
                                <p class="bookDescription">
                                    """ + books.get(i).getDescription() + """
                                </p>
                                                                                                                                                                 	
                            </div>
                                                                                                                                                                                       			
                            <div style="padding: 21px; display: inline-block"></div>
                          """);
        }

        result.append("""
                      </div>
                      """);

        return result.toString();
    }
}