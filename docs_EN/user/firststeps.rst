Erste Schritte
==============

Zuerst muss der PC, welcher für die Arbeit an der Testautomatisierung verwendet, eingerichtet werden. Dies beinhaltet den Download und die Konfiguration der notwendigen Tools - je nach verwendetem Technologie-Stack, Java oder .NET, unterscheiden sich diese.

Java
----
Arbeitsplatz einrichten
^^^^^^^^^^^^^^^^^^^^^^^

Installation Tools
******************

JDK
~~~
Für das Java-Framework muss JDK8 installiert sein. Dies installiert man wie folgt:

- `Oracleseite`_ öffnen
- Radio Button bei "Accept License Agreement" markieren
- Downloadlink bei "Windows x64" klicken, etwa ``jdk-8u131-windows-x64.exe``
- Download abwarten
- JDK mit den Standardeinstellungen installieren

.. note::

    Solltest du bereits ein älteres Java 1.8 installiert haben reicht das aus!
    *Java 1.8.0_25* ist für diese Zwecke gleich gut wie *Java 1.8.0_131*, oder eine andere Version. Wichtig ist nur, dass es mit *1.8* beginnt!

.. _Oracleseite: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html


IDE
~~~
Als Entwicklungsumgebung für das A2A-Framework wird JetBrains IntelliJ IDEA empfohlen. Hier gibt es eine kostenlose `Community Edition`_. Die Standardeinstellungen können belassen werden.

.. _Community Edition: https://www.jetbrains.com/idea/download/
    
Git
~~~
Als Versionskontrollsystem kommt Git zum Einsatz. Die aktuelle Version kann auf `git-scm.com`_ runtergeladen und mit den Standardeinstellungen installiert werden.

.. _git-scm.com: https://git-scm.com/download/win

Maven
~~~~~
Als Buildwerkzeug verwenden wir Maven. Für A2A.TAF wird dies zwar automatisch über das mvnw Wrapper Script runtergeladen,
eine manuelle Installation für andere Projekte wird aber dennoch empfohlen.

- Öffnen der `Maven-Projektseite`_
- Download des Ziparchivs, Anzeigename etwa ``apache-maven-3.5.0-bin.zip``
- Entpacken des beinhaltenden Ordners, zB. nach ``C:\Tools``

.. _Maven-Projektseite: https://maven.apache.org/download.cgi


Einrichten Tools
****************
Umgebungsvariablen
~~~~~~~~~~~~~~~~~~
Um Variablen zu setzen, am Besten den Systemdialog mit Windows+Pause öffnen, und dort auf "Erweiterte Systemeinstellungen" klicken.
Im Dialogfeld "Systemeigenschaften" kann man nun unten auf den Button "Umgebungsvariablen" klicken.

Die folgenden Variablen müssen im Bereich Systemvariablen angelegt werden (unter Umständen müssen die Pfade an euer System angepasst werden):

- **Name:** ``M2_HOME`` | **Wert:** ``C:\Tools\apache-maven-3.3.9``
- **Name:** ``JAVA_HOME`` | **Wert:** ``C:\Program Files\Java\jdk1.8.0_91``

.. note::

    Achte hier darauf, dass du nicht einfach die Maven-ZIP-Datei nach Tools extrahierst, sondern den enthaltenen Ordner reinkopierst.
    Wenn du die ZIP-Datei einfach über den Windows-Explorer extrahierst, müsstest du für ``M2_HOME`` etwas wie ``C:\Tools\apache-maven-3.5.0-bin\apache-maven-3.5.0`` als Systemvariable eintragen - was allerdings ebenfalls funktioniert!

Weiters sollte die PATH-Variable angepasst werden.
Dazu dem Wert der Path-Variable folgenden Text **voranstellen**: ``%M2_HOME%\bin;%JAVA_HOME%\bin;``

.. note::

    Die ``PATH``-Variable bestimmt, in welchen Verzeichnissen das Betriebssystem nach ausführbaren Dateien suchst, wenn du einfach Befehle wie "``java``" ausführst. Die Variable besteht aus einer langen Liste an Verzeichnissen, die mit Strichpunkten voneinander getrennt sind. Die Reihenfolge ist hierbei relevant: sobald das Betriebssystem eine passende, ausführbare Datei findet, hört es mit der Suche auf. Deshalb ist es wichtig, dass beide Einträge **vor** den bestehenden Pfaden kommen - ansonsten würde etwa ein anderer, bestehender Pfad zu Java dem von dir eingetrageneen bevorzugt werden!

Zum Testen kann man nun die Eingabeaufforderung öffnen und probieren, ob folgende zwei Befehle ausgeführt werden können:

- ``mvn -v``
- ``java -version``

.. note::

    Die Eingabeaufforderung findest du einfach, wenn du im Startmenü danach suchst :)
    Ansonsten kannst du auch den Ausführen-Dialog öffnen (ebenfalls über das Startmenü, oder mittels Windows + R), und dort "``cmd``" eingeben und ausführen.

Einrichten GitLab
~~~~~~~~~~~~~~~~~

Der Git Server wird mit `GitLab`_ betrieben - einloggen kannst du dich mit deinen normalen Anecon-Windows-Login-Daten!
Sobald du dich einmal eingeloggt hast, wird dein User von GitLab erkannt und solltest nun mit IntelliJ Git-Projekte auschecken und kompilieren können.

.. _GitLab: https://code.anecon.com/