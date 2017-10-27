# Introduction

Welcome to the RealWear Developer GitHub repository, where you can find out about the latest development features available to get the most out your Android application on a HMT-1 device.

Since the HMT-1 runs Android 6.0 it's very quick and easy to get your application up and running, and thanks to our revolutionary WearHF service most applications are speech enabled without any initial modifications. 

There are 3 steps for getting your application ready for the HMT-1:

1. **Just install your application** - The WearHF service will automatically detect the most common controls, and assign voice commands to them. This will provide a hands-free experience to most applications.
1. **Create a WearML script** - To provide a smoother user experience, or to help WearHF understand complex applications we have created the WearML scripting language. This sits between the application and the WearHF service and allows a developer to make their applications hands-free without editing the source code of their application. 
1. **Update application source code** - Once you have discovered the benefits the HMT-1 device, the final step is to edit your application to provide a optimized experience.

To learn more about WearHF, WearML, and how to get your application running on a HMT-1, see the examples below or checkout the [Developer Wiki.](https://github.com/realwear/Developer-Examples/wiki)

# Repository Contents

  ## WearML Example
  - ## Creating a WearML script
    ### [Source code](https://github.com/realwear/Developer-Examples/tree/master/WearML-Example/WearML-Example-Application) - [Documentation](https://github.com/realwear/Developer-Examples/wiki/WearML-Example) 
    This demo shows how to use a WearML script to customise the hands-free experience of a simple application without modifying its source code.

  ## Developer Examples

  When you are ready to modify your application's source code, the following code examples provide clear explanations of how to use the key features that the HMT-1 provides.

  - ## Action Button
    ### [Source code](https://github.com/realwear/Developer-Examples/blob/master/Developer-Examples/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/ActionButtonActivity.java) - [Documentation](https://github.com/realwear/Developer-Examples/wiki/Action-Button)    
    This example shows how to take control of the action button on the HMT-1 from inside your application, allowing a developer to trigger events when the user pushes the button.
  - ## Audio Capture
    ### [Source code](https://github.com/realwear/Developer-Examples/blob/master/Developer-Examples/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/AudioCaptureActivity.java) - [Documentation](https://github.com/realwear/Developer-Examples/wiki/Audio-Capture)
    This example shows how to record and playback audio from an application on the HMT-1.
  - ## Barcode/QR Code Scanner
    ### [Source code](https://github.com/realwear/Developer-Examples/blob/master/Developer-Examples/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/BarcodeActivity.java) - [Documentation](https://github.com/realwear/Developer-Examples/wiki/Barcode-API)    
    This example shows how launch a barcode scanner from an application and how to read the response once the user has scanned a code.
  - ## Camera
     ### [Source code](https://github.com/realwear/Developer-Examples/blob/master/Developer-Examples/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/CameraActivity.java) - [Documentation](https://github.com/realwear/Developer-Examples/wiki/Camera-API)
    This example shows how launch a camera from an application and how to display the picture the user takes.
  - ## Keyboard/Dictation
    ### [Source code](https://github.com/realwear/Developer-Examples/blob/master/Developer-Examples/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/DictationActivity.java) - [Documentation](https://github.com/realwear/Developer-Examples/wiki/Keyboard-Dictation-Input)
    This example shows how accept input from the user using either a keyboard or dictation.
  - ## Document Viewer
    ### [Source code](https://github.com/realwear/Developer-Examples/blob/master/Developer-Examples/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/DocumentActivity.java) - [Documentation](https://github.com/realwear/Developer-Examples/wiki/Document-Viewer-API)
    This example shows how to open documents and images in the document viewer from an application.
  - ## Microphone Release
    ### [Source code](https://github.com/realwear/Developer-Examples/blob/master/Developer-Examples/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/MicrophoneReleaseActivity.java) - [Documentation](https://github.com/realwear/Developer-Examples/wiki/Microphone-Release)
    This example shows how to release the microphone to disable voice recognition, allowing an application to take full control of the audio input.
  - ## Movie Player
    ### [Source code](https://github.com/realwear/Developer-Examples/blob/master/Developer-Examples/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/MovieActivity.java) - [Documentation](https://github.com/realwear/Developer-Examples/wiki/Movie-Player-API)
    This example shows how to open videos in the movie viewer from an application.
  - ## Show Help
    ### [Source code](https://github.com/realwear/Developer-Examples/blob/master/Developer-Examples/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/ShowHelpActivity.java) - [Documentation](https://github.com/realwear/Developer-Examples/wiki/Show-Help)
    This example shows how to add voice commands for an application to the show help menu.
  - ## Speech Recognizer
    ### [Source code](https://github.com/realwear/Developer-Examples/blob/master/Developer-Examples/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/SpeechRecognizerActivity.java) - [Documentation](https://github.com/realwear/Developer-Examples/wiki/Speech-Recognizer)
    This example shows how to programmatically add voice commands to an application. 

  ## Unity Example

  Unity applications do not function in the same way that standard Android applications do, so WearHF is not able to interpret them in the same way out of the box. 

  However, we have developed a Unity plugin that enables speech commands to work in Unity applications.

  - ### [Source code](https://github.com/realwear/Developer-Examples/tree/master/Unity-Example) - [Documentation](https://realwear.github.io/Unity-Plugin/Home)

# Other Resources

  - ## [Screen Casting](https://github.com/realwear/Developer-Examples/wiki/Screen-Casting)
    Learn how to cast your HMT-1 device to an external screen.
