UI-Keywords mit Selenium
========================
Selenium UI-Keywords dienen dazu, mehrere Selenium-Primitiven (wie z.B. auf ein HTML Element zu clicken oder einen Wert im Dropdown Box auszuwählen) zu sinnvollen komplexeren Funktionen zu kapseln (z.B. selectFirstCustomer).

Elementidentifikation: ID, CSS, XPATH, ...
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
Eine der Hauptfragen, wenn man mit Selenium arbeitet, ist, wie man ein konkretes Element auf der Weboberfläche identifizieren kann, um anschließend damit Arbeiten zu können.

Es gibt grundsätzlich die folgenden Möglichkeiten, um Elemente zu identifizieren (+ kurze Beispiele in java; in Klammern):

* Anhand von ID (WebLocator.id("footer"))
* Anhand vom CSS Selector (WebLocator.css("input[name=email]")))
* Anhand von css Klassen (WebLocator.className("col col-4 js-smphr")) // in diesem Fall wird nach 3 css Klassen gesucht
* Anhand vom Xpath (WebLocator.xpath("//a[@href='#Lead']"))
* Anhand vom 'name' Html-Attribut (WebLocator.name("description")))
* Anhand vom Html Tagnamen (WebLocator.tagName("select")))
* Anhand vom Text eines Hyperlinks (WebLocator.linkText("App Configuration"))

Für weitere Details kannst du dir die z.B. die javadoc oder den Code der Klasse org.openqa.selenium.By anschauen


Navigationskeywords
^^^^^^^^^^^^^^^^^^^

Zu den Navigationskeywords gehören solche, die zur Navigation innerhalb einer Webapplikation dienen. Z.B. könnten es die fiolgenden sein:

* Navigierung nach vorne und zurück in der Browserhistorie
* Navigierung in Menüs (z.B. com.espocrm.auto.keywords.ui.topMenu.TopMenuUiKeywords.createLead())
* Navigierung in Workflow- / Wizard- basierten Formularen

Aktionen
^^^^^^^^
Keywords, die zu Aktionen gehören kapseln die eigentliche Businesslogik. Ein Beispiel könnte ein Keyword sein, dass ein schon vorhandes GUI Objekt kopiert (z.B. einen Lead), und dabei inter prüft, ob das Objekt Lead überhaupt schon existiert:

.. code-block:: java

    public WebElement copyLead(String salutation, String firstName, String lastName) {
        if (null != leadExists(salutation, firstName, lastName)) {
            ...
        } else {
            createLead(salutation, firstName, lastName);
        }
    }