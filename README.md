# carservice
Demo project for investigating consumer driven contracts using Pact


Build any project:
$ mvn clean install

Publish consumer pact into broker:
$ mvn pact:publish

Verify contract in publisher side:
$ mvn clean install -Dpact.verifier.publishResults=true