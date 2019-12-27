# the-exploration-game

A game in which you can interactively explore implicational knowledge in Wikidata.

## Local development
Install dependencies with `npm install`, then run `npm run serve` to start the local development server. Run `lein run` from the `teg-rest` directory to start the backend service.

## Deployment on Wikimedia Toolforge
The [ansible](ansible) directory contains an ansible playbook that manages
deployment to Toolforge. Run `ansible-playbook -i production site.yml` to build
the backend and the app bundle, and deploy them to Toolforge. Force a clean
rebuild using `ansible-playbook -i production site.yml --tags=all,clean` to
force clean rebuilds.


## Examples to Try

### Set A: Credit Cards
+ card network P4443
+ operator P137
+ fee P2555

### Set B: 
+ source of energy P618
+ use P366

### Set C:
+ CPU P880
+ volatile random-access memory capacity (P2928)

### Set D: 
+ time of spacecraft landing P620
+ time of spacecraft launch P619

