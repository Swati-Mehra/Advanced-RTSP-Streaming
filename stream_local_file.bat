@echo off
setlocal enabledelayedexpansion

REM Run RTSP server setup batch file
echo Running RTSP server setup...
start /wait cmd /c "create_rtsp_server.bat"
if errorlevel 1 exit /b %errorlevel%

REM Paths and Variables
set HLS_OUTPUT_PATH=E:/Ashirwad/rtspStream/hls
set LOCAL_FILE_PATH=E:/Ashirwad/rtspStream/stream.mp4

REM Ensure the output directory exists
if not exist "%HLS_OUTPUT_PATH%" mkdir "%HLS_OUTPUT_PATH%"

REM Start video streaming
echo Streaming local video file to RTSP server...
start /b cmd /c "ffmpeg -re -stream_loop -1 -i %LOCAL_FILE_PATH% -f hls -hls_time 10 -hls_list_size 0 -hls_segment_filename %HLS_OUTPUT_PATH%/segment_%%Y%%m%%d%%H%%M%%S.ts -strftime 1 %HLS_OUTPUT_PATH%/stream.m3u8"

pause
