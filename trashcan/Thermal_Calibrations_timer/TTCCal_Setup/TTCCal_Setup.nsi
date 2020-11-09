; example1.nsi
;
; This script is perhaps one of the simplest NSIs you can make. All of the
; optional settings are left to their default settings. The installer simply 
; prompts the user asking them where to install, and drops a copy of example1.nsi
; there. 

;--------------------------------

; The name of the installer
Name "Thermal Calibration Program"

; The file to write
OutFile "TTCCal_Setup.exe"

; The default installation directory
InstallDir $PROGRAMFILES\ThermalCalibrationProgram

; The text to prompt the user to enter a directory
DirText "This will install the Thermal Calibration Program in your computer. Choose a directory"


; Request application privileges for Windows Vista
RequestExecutionLevel user

;--------------------------------

; Pages

Page directory
Page instfiles

;--------------------------------


; ----------------------------------------------------------------------------------
; *************************** SECTION FOR INSTALLING *******************************
; ----------------------------------------------------------------------------------

Section "" ; A "useful" name is not needed as we are not installing separate components

; Set output path to the installation directory. Also sets the working
; directory for shortcuts
SetOutPath $INSTDIR\

File readme.txt
File TTCCal_Setup.nsi
File TTCCal_Setup.jar

WriteUninstaller $INSTDIR\Uninstall.exe

; ///////////////// CREATE SHORT CUTS //////////////////////////////////////

CreateDirectory "$SMPROGRAMS\Thermal Calibrations"
CreateShortCut "$SMPROGRAMS\Thermal Calibrations\readme.lnk" "$INSTDIR\readme.txt"
CreateShortCut "$SMPROGRAMS\Thermal Calibrations\Uninstall Thermal Calibration Program.lnk" "$INSTDIR\Uninstall.exe"
CreateShortCut "$SMPROGRAMS\Thermal Calibrations\Run New Thermal Calibration with Console.lnk" "$SYSDIR\java" "-jar TTCCal_Setup.jar"
CreateShortCut "$SMPROGRAMS\Thermal Calibrations\Run New Thermal Calibration without Console.lnk" "$SYSDIR\javaw" "-jar TTCCal_Setup.jar"


; ///////////////// END CREATING SHORTCUTS ////////////////////////////////// 

; //////// CREATE REGISTRY KEYS FOR ADD/REMOVE PROGRAMS IN CONTROL PANEL /////////


; //////////////////////// END CREATING REGISTRY KEYS ////////////////////////////

MessageBox MB_OK "Installation was successful."

SectionEnd

; ----------------------------------------------------------------------------------
; ************************** SECTION FOR UNINSTALLING ******************************
; ---------------------------------------------------------------------------------- 

Section "Uninstall"
; remove all the files and folders
Delete $INSTDIR\Uninstall.exe ; delete self
Delete $INSTDIR\readme.txt
Delete $INSTDIR\TTCCal_Setup.nsi
Delete $INSTDIR\TTCCal_Setup.jar
RMDir $INSTDIR

; now remove all the startmenu links
Delete "$SMPROGRAMS\Thermal Calibrations\readme.lnk"
Delete "$SMPROGRAMS\Thermal Calibrations\Uninstall Thermal Calibration Program.lnk"
Delete "$SMPROGRAMS\Thermal Calibrations\Run New Thermal Calibration with Console.lnk"
Delete "$SMPROGRAMS\Thermal Calibrations\Run New Thermal Calibration without Console.lnk"
RMDIR "$SMPROGRAMS\Thermal Calibrations"

; Now delete registry keys

SectionEnd


