Projektbeginn
=============

Vor der ersten Verwendung des A2A.TAF in einem neuen Projekt sind ein paar Schritte zu erledigen, welche auf dieser Seite näher beschrieben werden.

Java
----
Um mit A2A.TAF.Java zu beginnen checke dir am Besten das Repository aus und passe die Namen der sample-Module (und der darin enthaltenen Klassen) an.


Module
^^^^^^
Im neuen Projektordner kannst du nun viele Unterordner sehen, wovon viele für dich erst mal wenig Relevanz haben werden. Erklärt sollen hier trotzdem mal alle werden:

* `core`: Enthält alle Kernkomponenten des Frameworks, wie die Basisklasse, oder notwendige Implementierungen für Aufräummechanismen. Hier solltest du erstmal nichts ändern müssen.
* `clients`: Enthält alle Clients, die aktuell mit A2A.TAF.Java ausgeliefert werden. Hier findest du zum Beispiel die Abstraktion zum Zugriff auf Selenium. Auch hier sind Änderungen am Anfang nicht notwendig.
* `keywords-sample`: Der erste Ordner, der für dich wirklich interessant ist! Hier kannst du alle Keywords anlegen, damit diese in Testfällen verwendet werden können!
* `data-sample`: Hier kannst du später Data Provider ablegen.
* `test-sample`: Der eigentliche Bereich für deine automatisierten Tests! Neben diesen befinden sich hier nur wenig weitere Java-Klassen, wie etwa eine projektspezifische Testfallbasisklasse, oder sonstige Hilfsmittel, die nur dein konkretes Testprojekt benötigen kann.


Dependencies
^^^^^^^^^^^^
Das du all diese Ordner nun angelegt hast, heißt noch nicht, dass du auch alle Module sofort verwenden kannst. Dies musst du erst explizit mit `dependency`-Einträgen in einer `pom.xml` eintragen.

Hierfür öffnest du die `pom.xml` im `keywords`-Ordner und fügst folgenden XML-Tag ein:

.. code-block:: xml

   <dependency>
       <groupId>com.anecon.taf</groupId>
       <artifactId>client-selenium</artifactId>
   </depencency>

Dies führt dazu, dass Maven weiß, dass du den Selenium-Client für deine Keywords verwenden willst.

Abhängigkeiten in Maven sind `transitiv`: sobald deine Tests Keywords verwenden, welche client-selenium benötigen, ist auch dein Testprojekt (indirekt) von Selenium abhängig - hier musst du den dependency-Tag für `client-selenium` also nicht noch einmal eintragen.

Keywordstruktur
^^^^^^^^^^^^^^^
Ein weiterer Schritt, der sich zu Projektbeginn anbietet, ist ein Festlegen der Keywordstruktur - also auf welche Art und Weise du auf deine Bausteine aus Testfällen heraus zugreifen können willst.

Hier kannst du dich etwa bei UI-Keywords im Großen und Ganzen an zwei Sachen orientieren: einzelnen Entities in der zu automatisierenden Applikation, oder der Useroberfläche.

Dies könnte zum Beispiel so aussehen:

.. code-block:: java

   // Orientierung an Entities
   apps().espoCrm().ui().accounts().create("Accountname");

   // Orientierung an Oberfläche
   apps().espoCrm().ui().goToAccounts().enterName("Accountname").clickCreate();

Während beide Arten Keywords zu schreiebn das selbe erledigen, unterscheiden sie sich doch:

* Bei der Orientierung an Entities hast du eventuell kürzere Aufrufe und kommst dem reinen Konzept von fachlichen Keywords näher, büßt dafür aber auch etwas an Funktionalität ein. Wenn du kleine Variationen testen willst, musst du dafür unterschiedliche Methoden schreiben.
* Orientierst du dich an der Oberfläche hast du dadurch mehr Schritte, aber auch höhere Flexibilität. Die fachlichen Aktionen werden erst im Testfall durch Aneinanderreihung der möglichen UI-Interaktionen gebildet. Testfälle werden länger und es benötigt etwas Zeit um sich an die Schreibweise zu gewöhnen, jedoch lassen sich leichter verschiedene Variationen an Keywords in Testfällen zusammenstellen.

Die Wahl bleibt bei dir und hängt auch immer stark von der Gestaltung der Oberfläche ab; für den weiteren Verlauf dieser Anleitung wird der zweite Ansatz verwendet.