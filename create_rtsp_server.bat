@echo off
setlocal enabledelayedexpansion

REM Define paths to your files and commands
set RTSP_CONFIG_PATH=%CD%\rtsp-simple-server.yml
set DOCKER_IMAGE=aler9/rtsp-simple-server:v1.3.0

REM Set the RTSP port (you can change this as needed)
set RTSP_PORT=8554

REM Ensure the YAML file has the correct port configuration
echo Updating RTSP server configuration to use port %RTSP_PORT%...

REM Start RTSP server with the specified configuration
echo Starting RTSP server on port %RTSP_PORT%...
start /b docker run --rm -v "%RTSP_CONFIG_PATH%":/rtsp-simple-server.yml -p %RTSP_PORT%:%RTSP_PORT% %DOCKER_IMAGE%

