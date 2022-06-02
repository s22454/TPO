<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="pl">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
<%--    <meta charset="utf-8">--%>
    <title>Szukanie książki</title>
    <link rel="stylesheet" href="Styles/style.css">
</head>

<body class="body">

<div style="margin: auto; width: 60%;">
    <div class="message">Szukaj książki</div><br>

    <div class="input_section">
        <form action="#">
            <label class="bait" for="title">Podaj tytuł:</label>
            <input size="70px" type="text" id="title" name="title" placeholder="wpisz tytuł" required>
            <button class="button" type="submit">Szukaj</button>
        </form>
    </div>
</div>