@echo off
setlocal

:: Définition des dossiers à créer
set BASE_DIR=%CD%
set DIRS=build lib src\main\java\mg\itu\demoservlet src\main\webapp\WEB-INF\classes

:: Création des dossiers
for %%D in (%DIRS%) do (
    if not exist "%BASE_DIR%\%%D" mkdir "%BASE_DIR%\%%D"
)

:: Création du fichier web.xml s'il n'existe pas
if not exist "%BASE_DIR%\src\main\webapp\WEB-INF\web.xml" (
    echo ^<?xml version="1.0" encoding="UTF-8"?^> > "%BASE_DIR%\src\main\webapp\WEB-INF\web.xml"
    echo ^<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee" version="3.1"^> >> "%BASE_DIR%\src\main\webapp\WEB-INF\web.xml"
    echo ^</web-app^> >> "%BASE_DIR%\src\main\webapp\WEB-INF\web.xml"
)

:: Création du fichier sources.txt
if not exist "%BASE_DIR%\sources.txt" (
    type nul > "%BASE_DIR%\sources.txt"
)

echo Structure de projet créée avec succès.
endlocal
