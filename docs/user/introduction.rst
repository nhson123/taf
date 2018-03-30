Einleitung
==========
Diese Dokumentation zeigt dir, wie du das A2A.TAF für dein Testprojekt einsetzen kannst - beginnend bei der Einrichtung des Arbeitsplatz und der Installation der notwendigen Tools, bis zum abschließenden Reporting.

Als zu automatisierende Beispielapplikation wird `espoCRM`_ verwendet - ein freies CRM Tool. Eine Variante, die bereits einen Webserver enthält, den du unter Windows einfach starten kannst, findest du `hier`_

.. _hier: https://code.anecon.com/examples/espoCRM/repository/archive.zip?ref=master
.. _espoCRM: https://www.espocrm.com/de/

Begriffsdefinitionen
--------------------
In der Verwendung des A2A.TAF können dir einige Begriffe unterkommen, welche hier beschrieben werden sollen.

* **Keywords**: Fachliche Aktionen, die (einmal automatisiert) in mehreren Tests wieder verwendet werden können.

  *Beispiel: "Bestellvorgang abschließen"*

* **Data Provider**: Data Provider verwenden Keywords, um in Testfällen Testdaten zur Verfügung zu stellen. Bei der Manipulation von Daten verwenden sie einen internen Speicher, mit dem sie nach dem Testfall alle notwendigen Schritte unternehmen können, um die Testumgebung wieder in einem akzeptablen Zustand zu hinterlassen.

  *Beispiel: "Kunde anlegen" (automatisch, nach Testende: "Kunde löschen")*

* **Clients**: Clients stellen die Schnittstelle zwischen A2A.TAF und externen Technologien dar. Durch ihre Verwendung in Keywords kann eine zusätzliche Abstraktionsstufe erreicht werden: die fachlichen Aktionen benutzen etwa nicht direkt Selenium-Code, sondern eine Kapselung dessen. Dadurch kann eine einheitliche Verwendung von Dritttools, sowie eine eventuell späterer Umstieg auf Alternativen, erleichtert werden.
  
  Je nach Projekt sind unterschiedliche Clients notwendig - für folgende Aufgaben / Technologien könntest du sie verwenden:
  
  * Selenium
  * IBM Rational Functional Tester
  * Datenbankzugriff
  * Aufrufen von Webschnittstellen
  * Interaktion mit Systemprozessen
  * ...
  
Ein weiterer Text zur Veranschaulichung findet sich am Anecon Blog mit dem Titel `Keywords, Clients, Generatoren – wovon reden wir überhaupt?`_

.. _Keywords, Clients, Generatoren – wovon reden wir überhaupt?: http://www.anecon.com/blog/keywords-clients-generatoren/