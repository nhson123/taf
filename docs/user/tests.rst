Tests schreiben
===============

Tests wissen was für Daten sie benötigen und stellen den gewünschten Zustand her, führen mit diesen bestimmte Aktionen am SUT durch, und überprüfen anschließend das Ergebnis.
Dieses Pattern ist auch unter "Arrange, Act, Assert" bekannt und stellt einen guten Richtwert dar, sollte jedoch nicht als Dogma befolgt werden.

So kann etwa Anlage & Löschen von Daten so aufwändig sein, dass es sich lohnt, dies in einem Testfall zu behandeln - in solchen Fällen ist "Arrange, Act, Assert, Act, Assert" auch in Ordnung.

Wichtig ist jedoch, dass alle Tests unabhängig voneinander Laufen können, also auch keine bestimmte Reihenfolge voraus setzen.
Außerdem **müssen** die Ergebnisse überprüft werden, wozu meist Assertions am Besten geeignet sind.

Basisklasse
^^^^^^^^^^^

Jedes Projekt definiert eine eigene Test-Basisklasse, welche projektspezifische Vor- und Nachbereitungen spezifizieren,
sowie Hilfsmethoden bereit stellen kann, die von (fast) allen Tests benötigt werden.


Testfall anlegen
^^^^^^^^^^^^^^^^

Beim Anlegen von Testmethoden stellt sich oft die Frage, wieviele Tests pro Klasse geschrieben werden sollen.
Da dies sehr stark vom Projekt und der Art der Tests abhängt, kann hier keine generelle Aussage getroffen werden.
Solang die Klasse selbst jedoch übersichtlich genug bleibt, und die Tests identische Vorbereitungsschritte teilen,
können ruhig mehrere Testmethoden in einer Klasse verwendet werden.

Bei komplexeren Testszenarios über >1 Bildschirmseite sollte allerdings nicht mehr als 1 Testfall pro Klasse enthalten sein.

Before/After
^^^^^^^^^^^^

Vor allem der Arrange-Teil eines Testfalles sollte in einer Before-Methode erledigt werden.
In TestNG wäre dies etwa ``@BeforeMethod`` - diese Methode wird also **vor** jedem Aufruf einer @Test-Methode gestartet.
Dies hat den Vorteil, dass der Test bei einem Fehler im Sicherstellen der Vorbedingungen nicht den Status *failed*,
sondern *skipped* bekommt - schließlich kann keine Aussage über die fachliche Korrektheit eines konkreten Features getroffen werden,
wenn etwa die Testumgebung insgesamt gerade nicht verfügbar ist.

Eventuell notwendige Nacharbeiten, welche nicht von einem Data Provider automatisch erledigt werden, können in
``AfterMethod(alwaysRun = true)``-annotierten Methoden erledigt werden. Der Parameter ``alwaysRun = true`` sorgt an dieser
Stelle dafür, dass die Methode auch im Fehlerfall aufgerufen wird.

Gruppierung
^^^^^^^^^^^

Um nur einen bestimmten Teilbereich des Testsets auszuführen, sollte jeder Test mit möglichst vielen Gruppen / Tags annotiert werden:
``@Test(groups = { "group1", "group2" })``.

Welche Gruppen sinnvoll sind unterscheiden sich von Projekt zu Projekt - folgende Gruppen können etwa praktisch für ein Projekt "Bla" sein:

- "bla.release.5.1.2" (dadurch lassen sich mit "release.bla.5.*" etwa alle Tests des 5. Releases starten)
- "bla.story.1234" (so lassen sich alle Tests die zu einer bestimmten Story gehören starten)
- "bla.feature.search" (starten von Tests die ein bestimmtes Feature abtesten, eventuell nach Refactorings praktisch)
- "bla.technical.popup" (alle Tests, die mit Popups arbeiten - etwa nach Refactoring des Testframeworks praktisch, um bestimmte technische Besonderheiten zu testen)
- "bla.layer.rest" (alle Tests startbar, die die REST-Schnittstelle verwenden)
- "bla.test.rest" (alle Tests startbar, welche reine REST-Tests darstellen)
- "bla.test.smoke" (wenige Tests starten, um schnell eine Aussage über den prinzipiellen Zustand des SUT treffen zu können)

testng.xml
^^^^^^^^^^

Welche Tests gestartet werden sollen wird in einer testng.xml-Datei konfiguriert.
Die Dokumentation dazu lässt sich auf `testng.org`_ finden.

.. _testng.org: http://testng.org/doc/documentation-main.html#testng-xml