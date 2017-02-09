# smart-contract-maven-archetypes

maven acrchetypes for smart contract development in java with eclipse and a model driven approach

Creates an eclipse project with an [UML2Solidity](https://github.com/UrsZeidler/uml2solidity) ready model and a generation config to produce a simple java application to deploy and manage smart contracts. The application uses the [eth-contract-api](https://github.com/adridadou/eth-contract-api) to access the ethereum blockchain, native via [ethereumj](https://github.com/UrsZeidler/ethereumj) or per rpc via [web3j](https://github.com/web3j/web3j).

Generate and write [Junit](https://github.com/UrsZeidler/uml2solidity/wiki/TestWithJava) tests for the smart contracts and execute them on the standalone blockchain really fast.

Change the used implementation simple and fast via system properties. 

### archetype catalog

the archetype catalog url: http://opensource.urszeidler.de/maven/archetype-catalog.xml

### archetype layout

The archetype introduce two new parameters:

* managerClassName - The name for the main Java class. 
* umlModelName - The name of the uml model files.

Project layout:

-etc
	\__artifactId__.conf
	\__artifactId__.sh
	\__umlModelName__.uml - java project.launch
-model
	  \__umlModelName__.di
	  \__umlModelName__.notation
	  \__umlModelName__.uml
-src
	\main
		 \java
		 	  \__managerClassName__.java
	\resources
			  \contracts
distri.xml
pom.xml

In the etc directory:
The shell script is a simple linux script to start the application it will be configured by the corresponding .conf file.
The laucher config is a startConfiguration to start the generation process it get picked up automatically from eclipse and is configured to produce the java files (model, deployer and tests)

in the model directory:
Just the uml model.

In the src/main/java directory:
The main class for the program. It is a simple class which uses the commons cli library to read the parameters and controls the deployment and management operations for your smart contracts.

At the end of the packaging process maven produces a zip and tar.gz file containing all the dependecies jars in a lib directory together with the jar of the product the shell script and it's configuration.


#### quick start

Add the http://opensource.urszeidler.de/maven/archetype-catalog.xml archetype catalog to the configuration <windows><preferences>, choose <maven><archetypes> add the url.

Create a new maven project and choose the archetype.
Fill the missing parameters. Model your smart contacts and generate the code.
 