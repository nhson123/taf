A2A.TAF Dokumentation
=====================

Dieses Repository enthält die Dokumentation zum A2A.TAF. Geschrieben ist es mit reStructuredText und Sphinx.

reStructuredText
----------------
rst ist eine Methode um Text zu gestalten, ähnlich wie Markdown, oder LaTeX. Von der Komplexität liegt es zwischen den beiden Alternativen - hierzu kannst du dir einfach vorhandene Beispiele in den `.rst`-Dateien innerhalb dieses Repositories ansehen. Eine kurze Übersicht über die Syntax von rst findest du [in deren Dokumentation](http://docutils.sourceforge.net/docs/user/rst/quickref.html).

Sphinx
------
Sphinx ist ein mächtiges, in Python geschriebenes Dokumentationswerkzeug.
Das in der `index.rst` angegebene Inhaltsverzeichnis wird für die Grundlegende Strukturierung herangezogen.

### Installation
Da Sphinx Python benötigt, musst du dieses erst mal herunterladen - es gibt sogar für Windows ein praktisches Installationspaket :)
Anschließend musst du die notwendigen Pakete in Python selbst nachinstallieren - dazu öffnest du eine Kommandozeile und führst folgenden Befehl aus:

    pip install sphinx sphinx-autobuild sphinx_rtd_theme

### Dokumentation erstellen
Um aus den ganzen `.rst`s eine anschauliche HTML-Dokumentation zu erhalten, führe einfach `./make html` (oder `make.bat html` auf Windows) aus; im `_build_html` Ordner findest du dann die produzierte Dokumentation.

Solltest du selbst an der Dokumentation arbeiten wollen kannst du auch `sphinx-autobuild . _build_html` aufrufen - dies startet einen Webserver auf http://localhost:8000 und generiert bei jeder Änderung an den Quelldateien die Dokumentation neu, woraufhin du im Browser nur F5 drücken musst.