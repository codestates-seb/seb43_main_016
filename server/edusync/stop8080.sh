#!/bin/bash

# Find the process running on port 8080 and get its process ID (PID)
pid=$(lsof -ti :8080)

# If a process is found, kill it
if [ -n "$pid" ]; then
  echo "Killing process on port 8080 with PID: $pid"
  kill $pid
  echo "Process killed."
else
  echo "No process found running on port 8080."
fi