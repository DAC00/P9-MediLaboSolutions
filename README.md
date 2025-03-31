# P9-MediLaboSolutions

# Projet
Le projet comprend plusieurs microservices qui communiquent entre eux par le biais d’une gateway sécurisée. Il utilise à la fois une base de données SQL et une autre NoSQL.
L’ensemble est déployable avec Docker grâce à un fichier docker-compose.yml.
Les microservices sont : 
- patientservice : gère les données des patients et communique avec une base de données MySQL.
- noteservice : gère les notes créées suite à un rendez-vous et communique avec une base de données MongoDB.
- riskservice : calcule le risque qu’un patient développe un diabète de type 2.
- frontservice : gère l’affichage de l’interface web.
- gateway : point d’entrée sécurisé, par lequel transite obligatoirement chaque requête interservice.

Le schéma ci-dessous permet de visualiser simplement l’architecture du projet.

IMAGE

# Utilisation

Une fois le projet téléchargé, il doit être déployé avec Docker. Pour cela, plusieurs solutions s'offrent à vous.
Assurez-vous d’abord que Docker est bien démarré, puis :
  - Utilisez le script Bash start.sh (par exemple avec Git Bash).
  Ce script se charge de compiler les microservices, de créer les images Docker, puis de démarrer l’ensemble de l’application.
  - Ou bien effectuez les étapes manuellement : exécutez mvn clean install dans chaque microservice, puis lancez docker-compose up --build depuis la racine.

Pour accéder à l'application, utilisez localhost:8080/login.
Pour vous connecter : nom d'utilisateur = admin, mot de passe = pass.

# Le Green Code

En ce qui concerne la recherche sur le Green Code, il s’agit d’un concept qui consiste à réduire la consommation de ressources pour accomplir une tâche.
Il est important de prendre conscience que la manière dont une application est développée a un impact direct sur la consommation d’énergie : plus une application est lourde, plus elle sollicite le processeur, ce qui augmente le temps de calcul et, par conséquent, la consommation électrique.

Concernant le projet, plusieurs pistes d'amélioration sont envisageables :
- réduire le volume d'informations transmises par certains microservices.
- mieux gérer les imports.
- optimiser l'utilisation des WebClient.
- réduire la taille des logs.