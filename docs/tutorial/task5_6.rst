TODO #5 & #6: Neue Keyword-Klasse: Account-Anlage
=================================================

In dieser Übung kannst du nun dein bisheriges UI-Keyword-Wissen auf die Probe stellen - am Ende sollst du einen Test schreiben können, der einen neuen Account anlegt, nach diesem sucht und dessen Anzeige überprüft.

Das Problem: es existiert noch nichts zur Accountanlage!

In den ``AccountsUiKeywords`` findest du *TODO #5*, für das du *eigentlich* nur den Klick auf den "Create Account"-Button einbauen musst - diese Methode soll allerdings eine neue Keywordklasse, ``CreateAccountUiKeywords`` zurückliefern.
Diese kannst du auf Basis anderer Keywordklassen, wie ``ProfileMenuUiKeywords``, erstellen.

.. note::

    Um in IntelliJ eine neue Klasse zu erstellen klickst du einfach mit Rechts auf das Package/den Ordner wo sie hin soll, und wählst ganz oben bei "*New*" "*Java Class*" aus.


Für TODO #6 musst du dann die entsprechenden Methoden in der neu erstellten Keywordklasse implementieren - hier kannst du dir Aussuchen, wie du die Keywords strukturieren willst, je nach dem Aufruf aus dem Testfall:

.. code-block:: java

    // Aufruf einer einzelnen Methode
    apps().espo().ui().accounts().openTab().clickAccountCreate().createNewAccount(username, vorname, nachname, password, ...);
    // --> Es muss nur eine Methode geschrieben werden, die mehrere Selenium-Aktionen durchführt.

    // Befüllung von jedem Feld einzeln
    apps().espo().ui().accounts().openTab().clickAccountCreate()
        .fillUsername(username)
        .fillVorname(vorname)
        .fillNachname(nachname)
        .fillPassword(password)
        ...
        .clickSave();
    // --> Es müssen mehrere Methoden geschrieben werden, die jeweils nur mit einem einzelnen UI-Element interagieren

Für diese Übung wird die erste Variante empfohlen - diese ist auch näher am Gedanken der Keywords, die den genaueren Prozess der Aktion (welche Felder müssen in welcher Reihenfolge ausgefüllt werden, ...) abstrahieren.
Es spricht aber auch nichts dagegen, für diese Übung beide Varianten zu implementieren, wenn du willst!
Um die Methoden wie oben aneinander reihen zu können, musst du darauf achten, dass jede ``fill...``-Methode mit ``return this;`` eine Referenz auf sich selbst zurück gibt! Diese "Technik" nennt sich `Method Chaining`_ und findet bereits in vielen Teilen des Frameworks Einsatz.

Ein Nachteil der Variante mit einem einzelnen Keyword besteht in der Flexibilität: was ist, wenn du nur die allernotwendigsten Felder ausfüllen willst? Und wenn du in einem anderen Testfall alle ausfüllen willst? Und wieder in anderen Testfällen füllst du manche nicht aus, manche schon, in verschiedenen Kombinationen? Wenn du überprüfen willst, ob man das Formualar eh nicht absenden kann, wenn nicht alle Felder ausgefüllt sind?
Hier wäre einer der simpelsten Varianten das Überladen der Keywords - das Definieren von mehreren gleichnamigen Keywordmethoden, allerdings mit unterschiedlichen Parameterlisten, je nach Verwendungszweck. Dies wird schnell unübersichtlich, sowie schlecht les- und wartbar!

.. _Method Chaining: https://en.wikipedia.org/w/index.php?title=Method_chaining&oldid=728277651#Java


Testfall schreiben
------------------

Wenn du die Keywordklasse angelegt hast, kannst du damit einen Test schreiben, um zu sehen ob auch alles funktioniert, oder überhaupt aus den Testfällen erreichbar ist.
