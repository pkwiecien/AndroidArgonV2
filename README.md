AndroidArgonV2
==============

#### What is AndroidArgonV2 and what's new?

This is a proof of concept of Argon for Android. The project integrates vuforia to display augmented reality objects (in this example teapot) with a custom built of chromium. This custom built of chromium allows to display complex CSS3D animations and to render WebGL animations. The best is that it works smoothly not only with the latest Android 4.4, but also with previous version. 

Check out screenshots to see WebGL animation in webview along with a teapot in augmented reality.

In this project I used ImageTargets project provided with vuforia [2]. 

#### How to run the app?
* Pull repository with libraries with custom chromium built: https://github.com/pkwiecien/AndroidArgon-libraries
* Pull this repository
* Open ADT (or eclipse with ADT), install vuforia if needed (see requirements below)
* Import libraries to workspace as existing projects into ADT
* Import AndroidArgon as exisiting Android project into ADT
* Add QCAR_SDK_ROOT into Java Build Path, see: Section "Set the QCAR environment variable" 
in https://developer.vuforia.com/resources/dev-guide/step-2-installing-vuforia-sdk
* Run project as Android application

#### Preview
![Alt text](https://raw.github.com/pkwiecien/AndroidArgonV2/master/Screenshots/device-2013-12-14-214554.png "Screenshot 1 - camera in the background with teapot, and webview with CSS3D Periodic table example")
![Alt text](https://raw.github.com/pkwiecien/AndroidArgonV2/master/Screenshots/device-2013-12-14-214700.png "Screenshot 2 - camera in the background with teapot, and webview with custom box in WebGL")

#### Requirements:
* Relatively new Android device that can handle WebGL and camera in the background
* vuforia installed, follow: https://developer.vuforia.com/resources/sdk/android

#### Test environment:
* ADT for Mac
* Nexus 7 Tablet (second generation) with Android 4.4 
* Moto X smartphone with Android 4.2.2

#### References

* [1] https://developer.android.com/guide/webapps/migrating.html
* [2] https://developer.vuforia.com/resources/sample-apps/image-targets-sample-app
* [3] http://threejs.org/examples/css3d_periodictable.html
