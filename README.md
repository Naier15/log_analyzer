# log_analyzer

RUN:
- put access.log into root directory
- mvn package ; cat access.log | java -jar .\target\analyze.jar -u 99.9 -t 45