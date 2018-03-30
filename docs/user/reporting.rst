Reporting
=========

Reporting ist ein wichtiger Teil der Automatisierung - einerseits hilft es dir beim Suchen von Fehlern,
andererseits soll es auch für weniger technisch versierte Menschen eine Möglichkeit geben, einen Automatisierungslauf zu
beurteilen.

Hierfür sind unterschiedliche Betrachtungsebenen & Präsentationen notwendig - beim Debuggen sind andere Infos gewünscht
als etwa vom Fachbereich, der sehen will, was in welchem Test durchgeführt wird.

Loggen
^^^^^^

Zum Loggen soll immer ein slf4j-Logger verwendet werden - dieser wird am Besten in der ersten Zeile einer Klasse
folgendermaßen instanziert:

.. code-block:: java
    private static final Logger log = LoggerFactory.getLogger(BeinhaltendeKlasse.class);
..

Anschließend können folgende Methoden verwendet werden - die Verwendung kann als Orientierungshilfe gesehen werden, welche
Infos auf welchem Level geloggt werden:

- **Name:** ``M2_HOME`` | **Wert:** ``C:\Tools\apache-maven-3.3.9``
- **Name:** ``JAVA_HOME`` | **Wert:** ``C:\Program Files\Java\jdk1.8.0_91``

- **Aufruf** | **Verwendung**
- log.trace(...) | Auf diesem Loglevel kann der gesamte Programmablauf protokolliert werden - etwa jeder Methodenaufruf mit den übergebenen Parametern.
- log.debug(...) | Dieses Loglevel soll beim Debuggen helfen - was passiert technisch, was tut der Client denn gerade für den Testfall, ...
- log.info(...) | Auf diesem Level soll der fachliche Ablauf des Testfalls geloggt werden - "Lege Kunde an", "Sende Bestellung", ...
- log.warn(...) | Wenn im Code ein merkwürdiger Zustand festgestellt wird, welcher aber nicht zwangsläufig einen Fehler darstellen muss, kann dies über WARN ausgegeben werden.
- log.error(...) | Auf diesem Level sollen alle Fehler und Exceptions geloggt werden - ein ERROR im Log weist immer auf ein definitives Problem hin.

slf4j kann Strings interpolieren, wodurch ein manuelles konkatenieren nicht notwendig ist - im Gegenteil, dadurch kann slf4j
selbst entscheiden, ob eine eventuell aufwändige `toString()` tatsächlich aufgerufen werden muss, oder das jeweilige Loglevel
ohnehin nicht aktiv ist.

Statt ``log.info("Anzahl an Einträgen: " + count)`` kann also ``log.info("Anzahl an Einträgen: {}", count)`` geschrieben werden.

Zusätzlich kann als letztes Argument eine Exception mitgegeben werden - dadurch wird automatisch der Stacktrace geloggt.


Sound
^^^^^

Beim Durchführen von Tests kann es hilfreich sein, einen akustischen Hinweis zu bekommen, ob ein Testlauf gerade gut oder schlecht läuft.
Hierfür gibt es einen Sound Reporter, welcher bei Testende einen entsprechenden Klang abspielt.

Um ihn zu verwenden genügt es, im jeweiligen Test-`project` folgende Dependency hinzuzufügen:

.. code-block:: xml

   <dependency>
        <groupId>com.anecon.taf</groupId>
        <artifactId>reporter-audio</artifactId>
    </dependency>


HTML-Report & Screenshots
^^^^^^^^^^^^^^^^^^^^^^^^^

Bei Testende wird ein HTML-Report generiert - um in diesen einzelne Meldungen hinzuzufügen, kann die ``Report``-Klasse
und deren statische Methoden verwendet werden: `pass`, `info`, `skip`, `error`

Als Übergabeparameter wird zumindest eine Beschreibung benötigt - zusätzlich können auch noch weitere Details angegeben werden,
welche im Report in einer zweiten Zeile dargestellt werden.
Zusätzlich lassen sich Screenshots mit Hilfe von `Report.takeScreenshot("Description")` im Report hinzufügen.

(Keyword)-Methoden lassen sich mit ``@Keyword("Description")`` annotieren, wodurch automatisch beim Betreten des Keywords ein
entsprechender Report-Eintrag generiert wird.

Außerdem wird jede Assertion ebenfalls geloggt - hierzu sollte unbedingt der `message`-Parameter der Assertions verwendet
werden, da dieser String ebenfalls im Report landet.

Jede reportete Meldung landet automatisch auch im Log, ein doppeltes Loggen ist also nicht notwendig.