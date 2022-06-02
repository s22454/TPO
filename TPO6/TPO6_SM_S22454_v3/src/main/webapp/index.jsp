<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="pl">
<head>
    <title>Książkoland</title>
    <link rel="stylesheet" href="Styles/style.css">
</head>

    <body class="body">

        <!-- Main section of page -->
        <div class="mainSection" style="width: 35%">

            <!-- Message to choose what user want to do -->
            <div class="message">
                Wybierz co chcesz zrobić
            </div>

            <!-- Empyt block for spaceing -->
            <div style="padding: 15px"></div>

            <!-- Button to see all books -->
            <a href="browse">
                <div class="allBooksButton">
                    Przeglądanie książek
                </div>
            </a>

            <!-- Button to search a book -->
            <a href="search">
                <div class="searchBookButton">
                    Szukaj książki
                </div>
            </a>

        </div>

    </body>
</html>