<%@ page import="java.util.HashMap" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dahsboard</title>
    <style>
        table {
            width: 500px;
        }

        table td, table th {
            padding: 10px 5px;
        }
    </style>
</head>
<body>
<a href="/ETU003345/new-credit">Creer ligne de credit</a>
<a href="/ETU003345/new-depense">Creer ligne de depense</a>
<table border="1" style="border-collapse: collapse">
    <tr>
        <th>Ligne de credit</th>
        <th>Montant total depense</th>
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