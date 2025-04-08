<%@ page import="java.util.List" %>
<%@ page import="mg.itu.demoservlet.credit.Credit" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<% List<Credit> credits = (List<Credit>) request.getAttribute("credits"); %>
<form action="/ETU003345/save-depense" method="post">
    <label>Choisir ligne de credit
        <select name="id_credit" required>
            <% for (Credit credit : credits) { %>
            <option value="<%= credit.getId() %>">
                <%= credit.getLibelle() %>
            </option>
            <% } %>
        </select>
    </label>
    <label>Montant
        <input type="number" name="montant" placeholder="Ex. 1000" required>
    </label>
    <label>Date
        <input type="date" name="dates">
    </label>
    <button type="submit">Valider</button>
</form>
<p style="color: red">
    <%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>
</p>
<a href="/ETU003345/">Retour</a>
</body>
</html>