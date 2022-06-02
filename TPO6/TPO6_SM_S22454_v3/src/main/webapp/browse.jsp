<%@ page import="Services.BookService" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html">
        <title>Przeglądanie książek</title>
        <link rel="stylesheet" href="Styles/style.css">
    </head>

    <body class="body">
        <!-- Main section of page -->
        <div class="mainSection">

            <!-- Message to choose what user want to do -->
            <div class="message">Dostępne książki</div>
            <div style="padding: 15px"></div>

            <%--List of books--%>
            <div>
                <%BookService bookService = new BookService();%>
                <%=bookService.bookListToHTML()%>
            </div>

        </div>
    </body>
</html>