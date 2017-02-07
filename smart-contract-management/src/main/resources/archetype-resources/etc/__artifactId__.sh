#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
${symbol_pound}!/bin/bash

${symbol_pound} include the conf
source ${artifactId}.conf;


${symbol_pound}call the jar
$JAVA -DEthereumFacadeProvider=$INSTANCE -Drpc-url=$RPC_URL -Dchain-id=$NET -DapiKey=$INFRA_API_KEY -jar  $JAR $@ -sk $KEYFILE -sp $PASS 

