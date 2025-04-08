<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="/ETU003345/save-credit" method="post">
    <label>Libelle
        <input type="text" name="libelle" placeholder="Ex. Frais bus">
    </label>
    <label>Montant
        <input type="number" name="montant" placeholder="Ex. 1000" min="0">
    </label>
    <label>
        <input type="date" name="date_debut">
    </label>
    <label>
        <input type="date" name="date_fin">
    </label>

    <button type="submit">Valider</button>
</form>
<a href="/ETU003345/">Retour</a>
</body>
</html>