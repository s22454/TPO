<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="pl">

<head>

    <meta charset="utf-8">
    <title>Szukanie książek</title>

</head>

<style>

    .body{
        background-color: #4c566a;
        padding-top: 50px;
    }

    .mainSection{
        width: 600px;
        margin: auto;
    }

    .message{
        margin: auto;
        text-align: center;
        padding: 10px;
        color: #38d9a9;
        font-size: 40px
    }

    .allBooksButton{
        background-color: #3b5bdb;
        text-align: center;
        color: #eceff4;
        font-size: 25px;
        padding: 8px;
        float: left;
        width: 240px;
    }

    .allBooksButton:hover{
        background-color: #5c7cfa;
    }


    .searchBookButton{
        background-color: #e8590c;
        text-align: center;
        color: #eceff4;
        font-size: 25px;
        padding: 8px;
        float: right;
        width: 240px;
    }

    .searchBookButton:hover{
        background-color: #ff922b;
    }

</style>

<body class="body">

<!-- Main section of page -->
<div class="mainSection">

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
    <a href="hello-servlet">
        <div class="searchBookButton">
            Szukaj książki
        </div>
    </a>

</div>

</body>

</html>