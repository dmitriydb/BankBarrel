# Содержание
1. [Описание проекта](#описание-проекта)
2. [Архитектура](#архитектура)
3. [Скриншоты веб-приложения](#скриншоты-веб-приложения)
4. [Стек технологий](#стек-технологий)
5. [Как деплоить](#как-деплоить)

## Описание проекта

Веб-приложение для осуществления простейших банковских операций и соответствующая инфраструктура. 

## Архитектура

Распределенный монолит.

![](https://files.catbox.moe/gehqgf.png)

## Скриншоты веб-приложения

![](https://files.catbox.moe/yiq5nm.png)
![](https://files.catbox.moe/n3bpv0.png)
![](https://files.catbox.moe/h4l1pw.png)
![](https://files.catbox.moe/j8ackd.png)

## Стек технологий
- Spring Boot, MVC, Data, Security
- Persistence: Postgres, Redis, H2
- Messaging: ActiveMQ
- Swagger, Flyway
- Thymeleaf, Bootstrap, jQuery
- Testing: JUnit, JAssert, Mockito
- Docker 

## Как деплоить

`git clone https://github.com/dmitriydb/BankBarrel.git`

`docker network create bb-network`

`cd BankBarrel`

`docker compose up`
