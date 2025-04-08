@echo off
setlocal enabledelayedexpansion
set "PROJECT_NAME=ETU003345"
echo Déploiement du projet %PROJECT_NAME% ...

set "ROOT=C:\Users\Tony\Desktop\Dossier personnel\ITU\S4\Mr Naina\%PROJECT_NAME%"
set "CLASS_PATH=%ROOT%\src\main\java\mg\itu\demoservlet"
set "WEB=C:\apache-tomcat-10.1.28\webapps"
set "WEBAPP=%ROOT%\src\main\webapp"
set "LIB=%ROOT%\lib\servlet-api.jar"
set "CLASSES=%WEBAPP%\WEB-INF\classes"
set "BUILD=%ROOT%\build"

if not exist "%ROOT%" (
    echo Erreur : Le répertoire source %ROOT% n'existe pas.
    exit /b 1
)

if not exist "%LIB%" (
    echo Erreur : Le fichier %LIB% n'existe pas.
    exit /b 1
)

:: Générer la liste des fichiers .java
set "SOURCE_LIST=%ROOT%\sources.txt"
del "%SOURCE_LIST%" 2>nul

for /r "%CLASS_PATH%" %%f in (*.java) do (
    set "FILE=%%f"
    call :EscapeBackslashes "%%f" EscapedPath
    echo "!EscapedPath!" >> "%SOURCE_LIST%"
)

:: Compiler les fichiers listés dans sources.txt
javac -cp "%LIB%" -d "%CLASSES%" @"%SOURCE_LIST%"
if errorlevel 1 (
    echo Erreur lors de la compilation. Abandon de l'opération.
    exit /b 1
)

echo Compilation terminée avec succès.

:: Vérifier que le répertoire WEBAPP existe
if not exist "%WEBAPP%\WEB-INF" (
    echo Erreur : Le répertoire %WEBAPP%\WEB-INF n'existe pas.
    exit /b 1
)

:: Copier les fichiers nécessaires pour le déploiement
xcopy "%WEBAPP%\WEB-INF" "%BUILD%\WEB-INF" /E /I /Y
if errorlevel 1 (
    echo Erreur lors de la copie de WEB-INF. Abandon de l'opération.
    exit /b 1
)

copy "%WEBAPP%\web.xml" "%BUILD%\WEB-INF"
if errorlevel 1 (
    echo Erreur lors de la copie de web.xml. Abandon de l'opération.
    exit /b 1
)

copy "%WEBAPP%\*.jsp" "%BUILD%"
if errorlevel 1 (
    echo Erreur lors de la copie des jsp. Abandon de l'opération.
    exit /b 1
)

:: Création du fichier .war
cd "%BUILD%"
jar -cvf "%WEB%\%PROJECT_NAME%.war" *
if errorlevel 1 (
    echo Erreur lors de la création du fichier .war. Abandon de l'opération.
    exit /b 1
)

cd ..
echo Le fichier %PROJECT_NAME%.war a été créé avec succès dans %WEB%.
pause
exit /b

:: Fonction pour échapper les backslashes
:EscapeBackslashes
setlocal
set "input=%~1"
set "output="
set "input=!input:\=\\!"  :: Double les backslashes
endlocal & set "%2=%input%"
exit /b
