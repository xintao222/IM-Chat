#!/bin/bash

sqlite3 im.$1.db "drop table if exists users;"
sqlite3 im.$1.db "create table users (username primary key asc, email, crypted_password);"
