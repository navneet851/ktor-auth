#!/bin/bash
docker build -t my-app .
docker run -d -p 8080:8080 my-app