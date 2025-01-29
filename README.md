# ParallelCucumber


Descriptions of Test Automation Framework:

Tools;
I utilise Java as main language in my framework.
For build and dependency management I utilised Maven.
For browser handling I use Selenium Webdriver.
I utilise assertions, before/after method, class and test annotations etc. with the help of TestNG.
I used Junit to create automation framework for testing UI.
I create test results reports with ExtenReport and Cucumber Reports.

Design;
In terms of Design, my framework is based on Page Object Model (POM).
I manage to do this with the Cucumber in  Behaviour Driven Development
In POM framework, I have also Page Object Class that stores locator of elements to utilize in testing.
I manage dependencies in pom.xml
I have a CukesRunner class to run all tests from one location.
As a design pattern for the Driver, I utilise Singleton object structure
I store my cucumber feature file separately, and I utilize them in step definition class.
Under Features package, I create Cucumber feature files.
Under StepDefinitions package, there are actual test scripts.
It has also Hooks class in order to call methods before and after steps.
I am also handling the taking screenshot and failed steps inside the Hooks class.

Driver;
With this driver, both mobile view and web tests can be run in the same project.
For jointly used meyhods, a single project supports less labor force.
The driver supports every browser.
It can be changed with url and browser with configuration proportions.

=>There are two conditions for the test to run in parallel. First, there must be more than one features and second, it must be run with maven.
