@echo off
set back=%cd%
for /d %%i in (%cd%\*) do (
cd "%%i"
echo current directory:
cd
git push
)
cd %back%