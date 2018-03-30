TODO #2: Keywords & Testfälle
=============================

Durch Alt + 6 kannst du dir am unteren Rand von IntelliJ alle offenen Todos ansehen - hier siehst du, dass in ``ProfileMenuUiKeywords``, ``UiEspoKeywords`` und ``LoginTestCase`` noch ein Todo #2 offen ist.


Keywords implementieren
-----------------------

Die Methode ``getUsername()`` liefert im Moment immer einen leeren String ``""`` zurück, ohne überhaupt Selenium zu verwenden um den Usernamen auszulesen.

Um dies zu ändern, lösche die zwei Anführungszeichen in der ``return``-Zeile und schreibe stattdessen ``selenium.``, um alle Methoden des Selenium-Clients angezeigt zu bekommen. Uns interessiert die ``getText(...)``-Methode, welche genau einen Parameter erwartet: eine Lokalisierungsstrategie, um das Element zur Textauslese zu finden. Dieses hast du in der vorherigen Übung bereits ausgefüllt: ``USERNAME_LINK``. Damit kannst du das return-Statement wie folgt vervollständigen:

.. code-block:: java

    return selenium.getText(USERNAME_LINK);

Nun sollte der Testfall endlich funktionieren!


Für den nächsten Schritt in diesem Todo musst du ein weiteres Keyword implementieren: ``loginButtonVisible()`` in den ``UiEspoKeywords``. Dazu kannst du genauso vorgehen wie zuvor: statt dem generellen ``false`` als Rückgabewert kannst du wieder ``selenium.`` tippen und die korrekte Methode suchen, um zu überprüfen, ob ein Element angezeigt wird!


Negativtestfall schreiben
-------------------------

Damit bleibt nur noch das Hinzufügen eines weiteren Testfalles!
Die Grundstruktur von Testmethoden ist folgende:

.. code-block:: java

    @Test
    public void testName() {
        // Aktionen
    }

Zu beachten: Die Methode ist mit ``@Test`` annotiert, gibt nichts zurück (``void``, sowie kein ``return`` in der Methode), und hat keine Argumente (``testName()`` statt etwa ``testName(String s)``. Dies gilt für alle Testmethoden, damit sie vom Testframework als solche erkannt und behandelt werden können.
Den Testnamen kannst du frei wählen - achte hier darauf, einen sprechenden Namen zu verwenden, damit sofort klar ist, was eigentlich getestet wird.
Da die Methode auch sonst nirgends im Code aufgerufen wird, kann der Name auch ruhig länger werden!

Für den Negativtestfall, der in ``LoginTestCase`` anzulegen ist, könnte die Testmethode wie folgt aussehen:

.. code-block:: java

    @Test
    public void assertThatLoggingInWithInvalidCredentialsDoesntWork() {
        // ...
    }

Als ersten Schritt muss der Login mit ungültigen Zugangsdaten versucht werden:


.. code-block:: java

    apps().espo().ui().login("INVALID_USERNAME", "WRONG_PASSWORD");

.. note::

    Für den Testfall wäre ``...login("Admin", "swqd2015")`` ebenfalls korrekt - allerdings verliert der Testfall beim Lesen an Eindeutigkeit. Wird hier absichtlich *swqd2015* statt *swqd2016* verwendet, oder ist das ein Tippfehler?

    Außerdem ist die Wahrscheinlichkeit, dass *INVALID_USERNAME*:*WRONG_PASSWORD* unerwarteterweise **doch** gültig wird, deutlich geringer als bei *Admin*:*swqd2015* :)

Nachdem der Login versucht wurde, können wir das ``loginButtonVisible()``-Keyword aufrufen, um zu überprüfen, ob der Button immer noch angezeigt wird. Dazu gibt es eine eigene Assert-Methode:

.. code-block:: java

    Assert.assertTrue(boolscherWert, "Was wird überprüft?")

Statt ``boolscherWert`` kannst du nun den Keywordaufruf von ``loginButtonVisible()`` einfügen - und damit sollte dann auch die Übung, sowie ein weiterer Testfall fertig sein!