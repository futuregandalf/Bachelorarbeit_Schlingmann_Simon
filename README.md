# Bachelorarbeit_Schlingmann_Simon
___________________________________
Setup Anleitung für das Programm:


-Disclaimer-  Dieser Setup Guide ist darauf ausgelegt, die Applikation in IntelliJ laufen zu lassen. 
              Für andere Entwicklungsumgebungen können die notwendigen Schritte variieren.
              
             
1. Aktuelle IntelliJ Version installieren (https://www.jetbrains.com/de-de/idea/)

2. Die aktuelle JavaFX SDK (17.0.3) herunterladen (https://gluonhq.com/products/javafx/) und an beliebiger Stelle entpacken.

3. Das Projekt herunterladen und in IntelliJ laden. Dabei werden die JavaFX Klassen noch nicht erkannt.

4. Unter File -> Project Structure -> Project die Projekt SDK 17 auswählen.  

5. Unter File -> Project Structure -> Libraries die heruntergeladene JavaFX 17 Library hinzufügen, dabei den \lib Ordner angeben.

6. Unter Run -> Edit Configurations die VM Option     --module-path %PATH_TO_FX% --add-modules=javafx.controls,javafx.fxml      einfügen, wobei %PATH_TO_FX% durch den Pfad zum lib Ordner der JavaFX 17 library ersetzt wird.



___________________________________

Hinweise zur Funktionalität / Benutzung


- Das Einlesen von Punktmengen erfolgt über .txt Dateien, die pro Zeile einen Punkt darstellen. Es sind also pro Zeile eine X- und eine Y-Koordinate als double Werte anzugeben.

- Die grafische Fläche verfügt über keine Skalierung. Die Punkte sollten sich für anschauliche Ergebnisse zwischen Werten von 200. bis 1000. befinden, mit 
  für diesen Zahlrenraum ausreichend großen Abständen.
  
- Eingelesene Punktmengen werden standardmäßig als Polygon interpretiert. Soll Eine Kette eingelesen werden, so muss in der letzten Zeile der .txt datei ein 'k' eingefügt werden.  

- Polygone und Ketten können auch frei per Mausklick erstellt werden. Dabei wird zu jeder geraden Anzahl von Punkten sofort das aktuell optimale Matching generiert.

- Soll ein geschlossenes Polygon erstellt werden statt einer Kette, so kann durch strg+Mausklick die letzte, schließende Kante gezogen werden.

- Die grafische Oberfläche stellt den Punkt (0,0) in der oberen linken Ecke dar. Wegen des Verfahrens zur Findung der inneren Kanten muss für frei erstellte Punktmengen der erste Punkt höher liegen als der zweite, um von Anfang an alle Innenkanten zu generieren.
