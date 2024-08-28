#!/bin/bash

# Check if the data directory is empty
if [ -z "$(ls -A /var/lib/postgresql/data)" ]; then
   echo "Initializing new PostgreSQL cluster..."
   initdb -D /var/lib/postgresql/data
else
   echo "PostgreSQL data directory is not empty, skipping initdb."
fi

# Start PostgreSQL
exec postgres
