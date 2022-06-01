package pjatk.tpo.tpo6_sm_s22454_v3;

import Services.BookService;
import entity.Book;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "searchServlet", value = "/search")
public class SearchServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.getWriter().println("""
                                     <!DOCTYPE html>
                                     <html lang="pl">
                                     	<head>
                                     	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
                                                                          
                                     		<meta charset="utf-8">
                                     		<title>Szukanie książki</title>
                                                                          
                                     	</head>
                                     	
                                     	<style>
                                     	
                                     		.body{
                                     			background-color: #4c566a;
                                     			padding-top: 50px;
                                     		}
                                     		
                                     		.title{
                                     			margin: auto;
                                     			text-align: center;
                                     			padding: 10px;
                                                color: #38d9a9;
                                                font-size: 40px
                                     		}
                                     		
                                     		.input_section{
                                     			align: center;
                                     			text-align: center;
                                     			font-size: 30px;
                                     			padding-bottom: 10px;
                                     			border-bottom: 1px solid #ccc;
                                     		}
                                     		
                                     		.bait{
                                     			color: #ebcb8b;
                                     		}
                                     		
                                     		.input{
                                     			padding: 8px;
                                                                          
                                     		}
                                     		
                                     		input[type=text] {
                                     			font-size: 20px;
                                     		}
                                     		
                                     		.button{
                                     		  border: none;
                                     		  color: #00ccff;
                                     		  background-color: #434c5e;
                                     		  padding: 7px 17px;
                                     		  text-align: center;
                                     		  text-decoration: none;
                                     		  display: inline-block;
                                     		  font-size: 20px;
                                     		  margin: 4px 2px;
                                     		  transition-duration: 0.4s;
                                     		  cursor: pointer;
                                     		}
                                     		
                                     		.button:hover{
                                     			background-color: white;
                                     			color: #f08c00;
                                     		}
                                                                          
                                     	</style>
                                                                          
                                     	<body class="body">
                                     	
                                     	<div style="margin: auto; width: 60%;">
                                     		<h1 class="title">Szukaj książki</h1><br>
                                     		
                                     		<div class="input_section">
                                     			<form action="#">
                                     				<label class="bait" for="title">Podaj tytuł:</label>
                                     				<input size="70px" type="text" id="title" name="title" placeholder="wpisz tytuł" required>
                                     				<button class="button" type="submit">Szukaj</button>
                                     			</form>
                                     		</div>
                                     	</div>
                                     	
                                     """);

       Enumeration<String> parameterNames = request.getParameterNames();
       StringBuilder stringBuilder = new StringBuilder();
       while (parameterNames.hasMoreElements())
           stringBuilder.append(request.getParameter(parameterNames.nextElement()));

       BookService bookService = new BookService();
       Book result = bookService.getBook(String.valueOf(stringBuilder));

       if (result == null && !stringBuilder.isEmpty()){
           response.getWriter().println("""
                                        <p style="color: #fa5252; text-align: center; font-size: 35px; padding-top: 15px;">Nie ma takiej książki</p>
                                        """);
       } else {
           response.getWriter().println("""
                           <div style="background-color: #EDD19C; width: 60%; margin: auto;">
							
							<p style="text-align: center; font-size: 30px; padding-top: 15px;">
								""" + result.getTitle() + """
							</p>
							
							<p style="padding-left: 15px; padding-right: 15px; font-size: 25px;">
								Autor: """ + " " + result.getAuthor() + """
							</p>
							
							<p style="padding-left: 15px; padding-right: 15px; font-size: 25px;">
								Data powstania: """ + " " + result.getReleaseDate() + """
							</p>
							
							<p style="padding-left: 15px;padding-right: 15px;font-size: 20px;word-wrap: break-word; padding-bottom: 15px;">
								""" + result.getDescription() + """
							</p>
							
						</div>
                        """);
       }
    }
}