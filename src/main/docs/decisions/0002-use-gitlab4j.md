---
status: accepted
date: 2023-04-15
deciders: @juwit
consulted: 
informed: 
---
# Use Gitlab4J for Gitlab API access

## Context and Problem Statement

Gitlab Classroom needs to access Gitlab API to be able to list, create repositories and so on.

## Considered Options

* Custom development with low-level Spring's `WebClient`
* Use some higher-level client, such as `Retrofit`
* Use an SDK, such as Gitlab4J

## Decision Outcome

Chosen option: "Use an SDK, such as Gitlab4J", because using an SDK will reduce the amount of code to write, to be as efficient as possible in development.
