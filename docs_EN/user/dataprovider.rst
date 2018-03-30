Data Provider
=============

Data Provider bereiten Testdaten im System vor und merken sich zusätzlich, wie diese wieder rückgängig gemacht werden können.
Hierbei wird ein Lambda-Ausdruck an eine `ActionStack`-Instanz übergeben, welche später im Cleanup aufgerufen wird.
Zur Interaktion mit dem SUT werden Keywords verwendet.

Dataprovider schreiben
^^^^^^^^^^^^^^^^^^^^^^

Ein einfacher DataProvider sieht so aus:

.. code-block:: java

    public class DummyDataProvider implements Cleanable {
        private static final Logger log = LoggerFactory.getLogger(DummyDataProvider.class);

        private final DummyKeyword dummyKeyword;

        public DummyDataProvider(DummyKeyword dummyKeyword) {
            this.dummyKeyword = dummyKeyword;
        }

        private ActionStack undoStack = new ActionStack();

        public void provideDummyString() {
            dummyKeyword.doSomething();

            undoStack.put(dummyKeyword::undoSomething);
        }

        @Override
        public void cleanUp() {
            undoStack.executeAll();
        }
    }
..

Beispiele
^^^^^^^^^

Mögliche Beispiele für DataProvider:

- Anlage von Testdaten (User, ...) in einer bestimmten Konfiguration
- Ändern der Systemkonfiguration