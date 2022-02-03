**NAGP_Automation**

Prerequisites
System should have following sets of software to run this project:

Java 1.8
Maven


**Framework Related Details**

Config File Location: src\main\resources
TestData Location: src\main\resources\testData

Current Test Report Location: Current_Report
Archive Test Report Location: Archive_Report

**How to Execute Test**

To run test from command line do following steps:

Moved to project location (cd /path/to/this/project/folder)
1. ExecuteTest.bat file
2. Open Terminal, 
run following command:
mvn clean test -DtestngXML=Regression.xml

**NOTE**

Please update "src\main\resources\config.properties" apikey & apitoken before starting test
