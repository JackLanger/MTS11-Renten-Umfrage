#!/bin/bash

py -m venv venv
venv\Scripts\activate.bat
venv\Scripts\pip.exe install -r requirements.txt
echo "done"
