# Projet API - Tristan Chaumont - M2 ACSI - Bank service

Ce service représente la banque qui va autoriser le paiement d'une [réservation](https://github.com/tristan-chaumont/reservation-service).

## Choix de conception

Le service est composé d'une unique entité `Account` contenant les attributs :
- `accountId` : l'id du compte bancaire
- `clientName` : le nom du client qui possède le compte
- `balance` : le solde restant du client

Vu que les id des données dans `Traveler` ne correspondent pas aux id des données dans `Account`, j'utilise le nom de l'utilisateur pour communiquer entre les deux services.

## Database

Contrairement au service Reservation, je n'ai pas mis en place de Docker avec une base PostgreSQL, tout se fait via une base H2.

## Communication avec le service Reservation

La communication avec le service Reservation fonctionne de la même manière que l'exemple conversion-bourse que l'on a étudié durant le cours. 
J'utilise une classe `BankResponse` pour transférer les données d'un service à un autre et qui contient les éléments suivants :
- `paymentAuthorized` : un booléen qui indique si le paiement est autorisé ou non
- `username` : le nom de l'utilisateur (je n'utilise pas l'id de l'utilisateur pour stocker les données dans la banque mais son nom)
- `port `: le port utilisé

## Données de test

Des données de test sont pré-insérées dans la base pour pouvoir utiliser le service, ici ce sont deux comptes :
| | Compte 1 | Compte 2 |
| --- | --- | --- |
| `account_id` | '1' | '2' |
| `client_name` | 'Chaumont' | 'Noirot' |
| `balance` | 100 | 0 |

Le premier compte permet de tester l'autorisation de paiement, le second permet de tester l'interdiction puisque le solde n'est pas suffisant.

## API

`GET /accounts/{accountName}/debit/{amount}`
- PathVariable
  - `accountName` : nom du client
  - `amount` : montant à vérifier
- Autorisation de paiement (retourne une `BankResponse`)
  - `false` :
    - si le compte n'existe pas
    - si le solde du compte n'est pas suffisant
  - `true` : le compte existe et le solde est suffisant  
