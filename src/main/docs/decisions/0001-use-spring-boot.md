---
status: accepted
date: 2023-04-15
deciders: @juwit
consulted: 
informed: 
---
# Use Spring Boot for development

## Context and Problem Statement

For the development of Gitlab Classrooms, a web framework, with the followiong features is required :

* REST API serving
* Static pages or template pages service
* Simple database access
* Security
* OAuth2 authentication using external provider (Gitlab)

Spring Boot provides all these features.

## Considered Options

* Spring Boot
* Quarkus
* Micronaut

## Decision Outcome

Chosen option: "Spring Boot", because there's no learning curve necessary to be able to develop quickly.
