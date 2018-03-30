TODO #1: Selektoren
===================

Beginnen wir am Besten bei den vorhandenen Testfällen - das ist nämlich ohnehin nur einer der aktiviert ist :)

Alle Testfälle findest du in der Klasse ``LoginTestCase`` - die Testmethode heißt ``testLoginExplicitCredentials``.

.. note::

    IntelliJ besitzt einige praktische Tastenkombinationen, die du zwischendurch in Bemerkungen wie dieser nachlesen kannst.

    Die erste interessante ist "*Alt* + *1*", mit der du die Projektübersicht links einblenden kannst. Generell kannst du mit den Zahlentasten, in Kombination mit Alt, verschiedene Ansichten rund um den Editor ein- und ausblenden lassen. Dies geht auch mit einem doppelten Druck auf Alt, wobei du beim zweiten Druck Alt gedrückt hälst (du siehst dann an den Bildschirmrändern die entsprechenden Ansichten, die du aktivieren kannst), oder über "View -> Tool Windows".

    Eine weitere Funktionalität, die immer praktisch ist, ist beim Öffnen/Wechseln von Dateien im Editor automatisch zur entsprechenden Datei in der Projektansicht zu scrollen. Hierzu klickst du auf das Zahnrad links oben neben den offenen Tabs und aktivierst "Autoscroll from Source", wie am Screenshot sichtbar.

    .. image:: img/task1_autoscroll.png

    Ein weiterer Tipp: Mit Doppelklick auf die Shift-Taste öffnet sich ein Suchfenster - hier kannst du nach mehr oder weniger **allem** suchen: gibst du "*LoginTestCase*" ein kannst du direkt zur Klasse springen, "*testLoginExplicitCredentials*" führt dich zur Methode - auch allgemeine Dateinamen funktionieren ebenfalls.
    Wenn der Suchbegriff eindeutig genug ist, reicht es sogar, wenn du nur die Initialen zum Suchen eintippst - also etwa "*LTC*" oder "*tLEC*".


Was macht der Testfall?
Die erste Zeile legt lediglich den Usernamen, "*Admin*", in eine entsprechende Variable.
Anschließend werden über ``apps()`` bereits Keywords aufgerufen - nämlich UI-Keywords, welche die espo-Applikation anzielen. Das konkrete Keyword, ``login(...)``, bekommt Username und Passwort überreicht, um den Login auszuführen.

Anschließend wird der Username aus der rechten oberen Ecke ausgelesen um zu überprüfen, ob der Login funktioniert hat.

Links neben der Testmethode siehst du einen kleinen grünen Pfeil - wenn du diesen anklickst, kannst du den Testfall direkt ausführen. Daraufhin wird das Projekt kompiliert und der Testfall gestartet - allerdings wird er fehlschlagen! Der Browser öffnet sich, espoCRM wird aufgerufen, aber der Login funktioniert nicht.

Damit kommen wir auch schon zum ersten Todo: es wurde noch nirgends definiert, wie Selenium die Loginfelder finden kann.
Diese Definition befindet sich in der Keyword-Klasse, in der auch ``login(...)`` definiert ist.

.. note::

    Um zu den Code-Stellen zu springen, in denen eine Klasse, Methode, Variable, ... definiert wird, drücke entweder mit der mittleren Maustaste auf den jeweiligen Teil (in dem Fall auf ``login``), oder stelle den Cursor in das Wort und drücke **F4**.

Die Selektoren werden ganz oben in der ``UiEspoKeywords`` Klasse definiert, wo du auch schon das erste Todo findest.
Die HTML-Elemente für den Usernamen, das Passwort und den Login-Button sind bereits angelegt, aber es ist noch keine ID hinterlegt, mit denen Selenium die Elemente finden könnte.


IDs von HTML-Elementen herausfinden
-----------------------------------
Um die richtigen IDs zu finden, öffne am besten Chrome und surfe die Loginseite von espoCRM an. Klickst du mit der rechten Maustaste etwa auf das Username-Feld, siehst du im Kontextmenü ganz unten den Punkt "Untersuchen". Mit diesem kannst du die Developer Tools von Chrome öffnen, wo auch schon der entsprechende HTML-Tag markiert ist (das kannst du überprüfen in dem du die Maus über den Tag bewegst - auf der Webseite wird dann das Feld aufleuchten!).

Der input-Tag enthält ein id-Attribut, welches sich perfekt für die Automatisierung eignet, da dieses immer eindeutig ist (bzw. sein sollte...). Mit einem Doppelklick auf die ID ``field-userName`` kannst du den Wert markieren und in die Keywordklasse kopieren - die Zeile sollte dann wie folgt aussehen:

.. code-block:: java

    // TODO #1 fix identifier
    private static final By USERNAME_FIELD = By.id("field-userName");
    private static final By PASSWORD_FIELD = By.id("");
    private static final By LOGIN_BUTTON = By.id("");

Nachdem du das selbe für das Passwortfeld und den Loginbutton wiederholt hast, kannst du den Testfall erneut starten - allerdings fehlt noch etwas!
Später im Testfall wird rechts oben das Usermenü geöffnet, um den Usernamen-Text auszulesen. Diese zwei Elemente (Menü-Button und der HTML-Tag, der den Usernamen enthält), müssen ebenfalls definiert werden, und zwar in ``ProfileMenuUiKeywords``.

CSS-Selektoren
--------------
Hier gibt es zwei "Probleme":
Untersuchst du den Menübutton in der rechten oberen Ecke, so markiert Chrome unter Umständen einen ``span``-Tag, welcher keine ID enthält - dieser Tag ist jedoch nur für die Darstellung der drei Balken zuständig. Ein Element darüber befindet sich ein a-Tag, welcher eine verwendbare ID enthält!

Der Username hat allerdings keine verwendbare ID - hier müssen wir uns mit CSS-Selektoren weiterhelfen. Eine Übersicht zu diesen findest du auf `MDN - Selectors`_, in diesem Fall sind vor allem die "Attribute Selectors" interessant.

Unser Ziel ist es, das Element zu lokalisieren, welches zum Userprofil linkt, also etwas wie "#User/view/1" im ``href``-Attribut hat. Der CSS-Selektor, den du benötigst, hat in etwa folgenden Aufbau: *element[attributOPERATOR"wert"]* - für einen a-Tag, der einen bestimmten Titel hat, könntest du etwa "*a[title="Linktitel"]*" verwenden.

.. note::

    In Chrome kannst du selbstgeschriebene CSS-Selektoren direkt in den Developer Tools testen!
    Dafür öffnest du die Console, in dem du entweder in den Developer Tools Escape drückst, oder den entsprechenden Tab öffnest. Anschließend kannst du folgendes eingeben (achte aber darauf, Anführungszeichen entweder mit einem Backslash zu escapen oder verschiedene zu verwenden!):

    ``$("<füge hier deinen CSS-Selektor ein>")``

    zB: ``$("a[title=\"Linktitel\"]")`` oder ``$('a[title="Linktitel"]')`` oder ``$("a[title='Linktitel']")``

    Nach einer Bestätigung mit Enter wird dir Chrome direkt eine Liste aller passenden, gefundenen Elemente anzeigen - konnte nichts gefunden werden, bekommst du die leere Liste: ``[]``
    Mit der Pfeiltaste nach Oben kannst du die letzte Eingabe direkt wieder aufrufen und erneut bearbeiten - fährst du mit der Maus über ein gefundenes Element, wird es dir im Browser markiert!

Sobald du auch für den Usernamen den korrekten CSS-Selektor hast, kannst du diesen in die Keywords-Klasse kopieren - das erste Todo hast du erledigt, du kannst die Zeile auch gleich aus dem Code löschen!

Allerdings funktioniert der Testfall immer noch nicht... In der nächsten Übung :doc:`/tutorial/task2` kannst du das beheben!

.. _MDN - Selectors: https://developer.mozilla.org/en-US/docs/Web/Guide/CSS/Getting_started/Selectors