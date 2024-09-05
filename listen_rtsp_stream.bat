@echo off
setlocal enabledelayedexpansion

REM Check if an RTSP URL is provided as an argument
if "%1"=="" (
    echo No RTSP URL provided. Exiting...
    exit /b 1
)

REM Define paths to your files and commands
set RTSP_URL=%1
set SPRING_BOOT_JAR=%CD%\target\rtsp-stream-0.0.1-SNAPSHOT.jar
set STREAM_URL=http://localhost:8080/index.html
set HLS_OUTPUT_PATH=E:\Ashirwad\rtspStream\hls

REM Ensure the output directory for recordings exists
if not exist "%HLS_OUTPUT_PATH%" mkdir "%HLS_OUTPUT_PATH%"

REM Start video streaming
echo Streaming video...
start /b cmd /c "ffmpeg -re -i %RTSP_URL% -f hls -hls_time 10 -hls_list_size 0 -hls_segment_filename E:/Ashirwad/rtspStream/hls/segment_%%Y%%m%%d%%H%%M%%S.ts -strftime 1 E:/Ashirwad/rtspStream/hls/stream.m3u8"

REM Wait for the video streaming to start (adjust the sleep time as needed)
timeout /t 20

REM Start Spring Boot application
echo Starting Spring Boot application...
start /b java -jar "%SPRING_BOOT_JAR%"

REM Wait for the Spring Boot application to start (adjust the sleep time as needed)
timeout /t 10

REM Redirect to the video stream page
echo Opening the video stream in the web browser...
start "" "%STREAM_URL%"

REM Pause to keep the window open
pause
