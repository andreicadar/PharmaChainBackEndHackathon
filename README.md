# PharmaChain Back-End

## How it started

Me and my university colleagues participated in the InnovationLabs 2023 hackathon in Cluj-Napoca and had to build an app in 24 hours. Some of my colleagues had the idea of the app from a previous hackathon and a part of the Frond-End.

## Usage

The user can search for a specific medicine on the app and the nearest pharmacies which have that medicine pop up first. We do not have access to the stock of the pharmacies, instead, the user can make a request to the pharmacy with the push of a button to ask for the stock, or a forum-style app where a user can ping that he had found that requested medicine at that pharmacy. This ping will remain active for 24 hours so other users can see its availability.

## Technical Details

The Back-End was written in Java with SpringBoot.
All the API Requests are defined in the [**UserController**](src/main/java/api/UserController.java) class. All requests except **/login** are filtered using JWT Authentication.
The [**DataAccessService**](src/main/java/api/DataAccessService.java) class is the last layer between the Back-End and the DBMS. All the queries are _SQL Parameterized Query_
