- Welche grundlegenden Elemente müssen bei einer REST Schnittstelle zur Verfügung gestellt werden?
  - Endpukte(Pfade) und deren Dokumentation und Beschreibung. Diese pfade sollten "Resourcen" (Order und Dateien) darstellen
- Wie stehen diese mit den HTTP-Befehlen in Verbindung?
  - Jeder Pfad kann über mehrere Methoden (GET, PUT, etc) angesprochen werden um die Ressource auf andere weise abzufragen/ändern oder löschen
- Welche Datenbasis bietet sich für einen solchen Use-Case an?
  - Jegliche Datenbank basierte Lösung eignet sich. Aufgrund der Einfachheit ist jedoch eine NoSQL Lösung zu bevorzugen 
- Welche Erfordernisse sollten hier bedacht werden?
  - Das DBMS (oder NoSQL Repo) muss keine besonderen Bedingungen erfüllen welche über den Standardumfang eines Datenbanksystems hinaus gehen 
- Verschiedene Frameworks bieten schnelle Umsetzungsmöglichkeiten, welche Eckpunkte müssen jedoch bei einer öffentlichen Bereitstellung von solchen Services beachtet werden?
  - Sicherheit anhand von Spamschutz und Bruteforcing sind meistens nicht nativ eingebaut auch "rating limits" könnten eine sinnvolle Ergänzung sein.