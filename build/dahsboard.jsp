<%@ page import="java.util.HashMap" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dahsboard</title>
</head>
<body>
<a href="/etu3345/new-credit">Creer ligne de credit</a>
<a href="/etu3345/new-depense">Creer ligne de depense</a>
<table style="border: 1px solid">
    <tr>
        <th>Ligne de credit</th>
        <th>Montant total dépense</th>
        <th>Reste</th>
    </tr>
    <% HashMap<String, Double[]> data = (HashMap<String, Double[]>) request.getAttribute("data");
        for (HashMap.Entry<String, Double[]> entry : data.entrySet()) { %>
    <tr>
        <td>
            <%= entry.getKey() %>
        </td>
        <td>
            <%= entry.getValue()[0] %>
        </td>
        <td>
            <%= entry.getValue()[1] %>
        </td>
    </tr>
    <% } %>

</table>
</body>
</html>