TODO #8: User über REST editieren
=================================

Das Ziel dieser Übung ist es, dir den Umgang mit den REST-Methoden etwas näher zu bringen.

Die Methoden ``createUser`` und ``deleteUser`` existierten bereits in den ``UserRestEspoKeywords`` - jetzt liegt es an dir, auch die ``editUser``-Methode zu implementieren!

Diese bekommt die ID eines existierenden Users, und ein ``User``-Objekt, das die neuen Attribute beinhaltet. Die `API-Dokumentation`_ von espoCRM gibt hier leider nicht viel her, weshalb du am Besten wieder über Chromes Developer Tools einen Änderungsrequest mitschneidest und versuchst, daraus die entsprechende ``restApi...``-Zeile abzuleiten.

.. _API-Dokumentation: https://github.com/espocrm/documentation/blob/master/development/api.md