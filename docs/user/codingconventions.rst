Coding Conventions
==================

Java Conventions
----------------
Als Orientierung bei Coding Conventions sollte sich vor allem an die offiziellen `Java Conventions`_ gehalten werden.

.. _Java Conventions: http://www.oracle.com/technetwork/java/codeconvtoc-136057.html

Kein System.{out/err}.println verwenden
---------------------------------------
Immer den slf4j-Logger verwenden, damit nicht benötigte Logausgaben über die Logger-Konfiguration abgeschalten werden können.

.. code-block:: java

   // Falsch:
   System.out.println("Logausgabe");
   
   // Richtig
   log.info("Logausgabe");

..

Exceptions müssen immer geloggt werden
--------------------------------------
In einem catch-Block muss zumindest einmal eine ähnliche Zeile wie diese vorkommen: log.error("Sinnvolle Meldung", e);
Sollte die Exception nicht so schwerwiegend sein, kann auch log.debug, bzw. log.trace verwendet werden.
Diese Regel tritt außer Kraft, wenn die Exception weiter geworfen wird oder die Exception keinen Fehlerfall darstellt.

.. code-block:: java

   // Falsch:
   try {
      // do something that can throw an exception
   } catch (Exception e) {
      // nichts
   }
    
   // Auch falsch:
   try {
      // do something that can throw an exception
   } catch (Exception e) {
      // hier fehlt die Exception im Aufruf:
      log.error("Sinnvolle Meldung");
   }
    
   // Richtig:
   try {
      // do something that can throw an exception
   } catch (Exception e) {
      log.error("Sinnvolle Meldung", e); // Es kann z.B. auch log.debug, oder log.trace verwendet werden
   }
    
   // Auch richtig:
   try {
      // do something that can throw an exception
   } catch (Exception e) {
      // Exception behandeln, z.B. Resourcen schließen
      throw new AutomationFrameworkException("Sinnvolle Meldung", e);
   }
   
..

Resourcen (Streams, Reader, Clients, ...) müssen immer geschlossen werden
-------------------------------------------------------------------------
Wenn mit InputStreams, Readern (z.B. BufferedReader) oder Clients (z.B. HttpClient) gearbeitet wird, müssen diese auch wieder geschlossen werden (entweder die close()-Methode aufrufen oder die Java 7 Syntax "try-with-resources" verwenden).

.. code-block:: java

   // Falsch:
   CloseableHttpClient client = null;
   try {
      client = HttpClients.createDefault();
      // do something with the client
   } catch (Exception e) {
      log.error("request failed", e);
   }
    
   // Richtig:
   CloseableHttpClient client = null;
   try {
      client = HttpClients.createDefault();
      // do something with the client
   } catch (Exception e) {
      log.error("request failed", e);
   } finally {
      if (client != null) {
         client.close();
      }
   }
    
   // Auch richtig (Java >=7 Syntax)
   try (CloseableHttpClient client = HttpClients.createDefault()) {
      // do something with the client
   } catch (Exception e) {
      log.error("request failed", e);
   }
   // der Client wird automatisch geschlossen, auch wenn eine Exception auftritt

..

Code gegen Interfaces statt gegen konkrete Implementierungen schreiben
----------------------------------------------------------------------

.. code-block:: java

   // Falsch:
   ArrayList<Testcase> testcaseList = new ArrayList<>();
   HashMap<Testcase> testcaseList = new HashMap<>();
    
   // Richtig:
   List<Testcase> testcaseList = new ArrayList<>();
   Map<Testcase> testcaseList = new HashMap<>();

..

Sleeper.sleep(...) nur wenn nicht anders möglich verwenden
----------------------------------------------------------
So oft wie möglich auf irgendeine Art von Event warten (Element wird angezeigt, Element verschwindet, etc.), anstatt z.B. eine fixe Anzahl an Millisekunden zu warten und davon auszugehen, dass bis dahin das Event aufgetreten ist.

Source-Code muss formatiert sein
--------------------------------
Wenn alle ein anderes Formatting verwenden würden, würde man bei den Commits in Git nur noch schwer sehen können, was die relevanten Änderungen waren, da beim Speichern jede Zeile neu formatiert wird.

Utility-Klassen müssen einen private-Konstruktor haben
------------------------------------------------------

.. code-block:: java

   // Beispiel
   public class UtilityClass {
    
      private UtilityClass() {
         throw new AssertionError("Instantiation not allowed");
      }
    
      public static void doSomethingUseful() {
      
      }
   }

..

Es sollen keine Klassen/Methoden verwendet werden, die als Deprecated gekennzeichnet sind (wie z.B. DefaultHttpClient).
-----------------------------------------------------------------------------------------------------------------------

.. code-block:: java

   // Falsch:
   HttpClient client = new DefaultHttpClient();
    
   // Richtig:
   HttpClient client = HttpClients.createDefault();

..

Es darf keine Exception bzw. RuntimeException geworfen werden
-------------------------------------------------------------
Wenn im Testfall/Keyword, etc. eine Exception geworfen werden muss, dann darf keine Exception bzw. RuntimeException geworfen werden. Stattdessen sollten spezifische Exceptions, wie z.B. IOException oder ähnliches verwendet werden. Statt einer RuntimeException sollte die AutomationFrameworkException verwendet werden.

.. code-block:: java

   // Falsch
   if (!expected.equals(actual)) {
      throw new Exception("Überprüfung fehlgeschlagen");
   }
    
   // Auch falsch:
   if (!expected.equals(actual)) {
      throw new RuntimeException("Überprüfung fehlgeschlagen");
   }
    
   // Richtig:
   if (!expected.equals(actual)) {
      throw new IOException("Überprüfung fehlgeschlagen");
   }
    
   // Auch richtig:
   if (!expected.equals(actual)) {
      throw new AutomationFrameworkException("Überprüfung fehlgeschlagen");
   }

..

Überprüfung auf Leerstrings mit StringUtils.isEmpty(str) (Apache Commons Lang o.ä.)
-----------------------------------------------------------------------------------
Null-Safe Überprüfung, ob ein String null oder leer ist.

.. code-block:: java

   // Falsch:
   if ("".equals(str)) {
      // whatever
   }

   // Richtig:
   if (StringUtils.isEmpty(str)) {
      // whatever
   }

..

Bei Stringvergleichen muss der hardgecodete String auf der linken Seite stehen
------------------------------------------------------------------------------
Dadurch wird auch der Fall abgefangen, dass (wie im Beispiel unten) actual null ist.

.. code-block:: java

   // Falsch:
   if (actual.equals("expected")) {
      // whatever
   }
    
   // Richtig:
   if ("expected".equals(actual)) {
      // whatever
   }

..

In Schleifen dürfen keine Strings zusammengehängt werden
--------------------------------------------------------
Das kann zu gröberen Performance-Problemen bei langen Strings führen. Stattdessen z.B. den StringBuilder verwenden

.. code-block:: java

   // Falsch:
   String result = ""
   for (String string : strings) {
      result += string;
   }
   
   // Richtig:
   StringBuilder builder = new StringBuilder();
   for (String string : strings) {
      builder.append(string);
   }
   String result = builder.toString();

..

Ob eine Collection leer ist, muss mit isEmpty() überprüft werden
----------------------------------------------------------------
Je nach Datentyp kann isEmpty() performanter sein

.. code-block:: java

   // Falsch:
   if (list.size() == 0) {
      // whatever
   }
   // Richtig:
   if (list.isEmpty()) {
      // whatever
   }

..

Keinen auskommentierten Code einchecken
---------------------------------------
Entweder der Code wird gebraucht, oder nicht. Wenn er nicht gebraucht wird, dann auch wieder entfernen.

Keine Java-Warnings
-------------------
Unbedingt darauf achten, dass keine neuen Java-Warnings eingecheckt werden. Ziel sollte ein 0-Warning-Build sein.

An die klassischen Naming-Conventions von Java halten
-----------------------------------------------------

.. code-block:: java

   // Falsch
   public class someClass
   public class some_class
   public class Someclass
    
   // Richtig:
   public class SomeClass
    
    
   // Falsch:
   public void do_something()
   public void dosomething()
    
   // Richtig:
   public void doSomething()
    
    
   // Falsch:
   String my_string;
   String mystring;
    
   // Richtig:
   String myString;
    
    
   // Falsch:
   final String my_constant;
    
   // Richtig:
   final String MY_CONSTANT;

..

Klassennamen dürfen nur dann auf "Exception" oder "Error" enden, wenn die Klasse auch wirklich von Exception oder Error erbt
----------------------------------------------------------------------------------------------------------------------------

.. code-block:: java

   // Falsch:
   public class AssertCommitBookingException extends SomeGenericClass {}
    
   // Auch falsch:
   public class AssertCommitBookingError extends SomeGenericClass {}
    
   // Richtig:
   public class AssertCommitBookingFailure extends SomeGenericClass {}

..
