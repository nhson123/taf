TODO #9: REST-Keywords für Account anlegen
==========================================

Fast fertig!

Du weißt nun hoffentlich schon wie du über das ``restApi``-Objekt HTTP-Requests abschicken kannst, und wie eine Klasse aussehen muss, damit sie wie ``User`` (de)serialisiert werden kann - du hast auch schon neue Keywordklassen in die vorhandene Struktur eingebettet, um diese aus Testfällen aufrufen zu können.

Die letzte Aufgabe: implementiere die Funktionalität um neue Accounts über espoCRMs REST-Schnittstelle anzulegen!

Zur Orientierung kannst du einerseits die ``User``-Klasse für deine neue ``Account``-Klasse verwenden, andererseits die ``UserRestEspoKeywords`` für deine ``AccountRestEspoKeywords``.
Anschließend musst du noch eine Methode in der ``RestEspoKeywords``-Klasse anlegen.