# the-exploration-game

A game in which you can interactively explore implicational knowledge in Wikidata.

## Local development
Install dependencies with `npm install`, then run `npm run serve` to start the local development server. Run `lein run` from the `teg-rest` directory to start the backend service.

## Deployment on Wikimedia Toolforge
The [ansible](ansible) directory contains an ansible playbook that manages deployment to Toolforge. Run `ansible-playbook -i production site.yml` to build the backend and the app bundle, and deploy them to Toolforge. Force a clean rebuild using `ansible-playbook -i production site.yml --tags=all,clean` to force clean rebuilds.