---
title: HMT-1 Developer examples
description: Microphone Release Example
---

To ensure voice recognition is always responsive the HMT-1 functions in an "always listening" state where it waits for the user to trigger a voice command that is registered with the WearHF service at that point in time.

Applications can still access audio while the WearHF service is listening thanks to an audio share mechanism built into WearHF.

This means that applications should be able to fully function alongside WearHF and voice recognition but an application may wish to request for WearHF to release the microphone, stopping WearHF from being able to access it. When this happens the rest of the Android system will remain having access to the microphone, following the standard Android principles.

This feature should be used with caution as it will disable behavior that the user may be expecting such as removing all voice recognition. 

```java
  private static final String ACTION_RELEASE_MIC =
      "com.realwear.wearhf.intent.action.RELEASE_MIC";
  private static final String ACTION_MIC_RELEASED =
      "com.realwear.wearhf.intent.action.MIC_RELEASED";

  private static final String EXTRA_SOURCE_PACKAGE =
      "com.realwear.wearhf.intent.extra.SOURCE_PACKAGE";

  private void takeMicrophone() {
      Intent intent = new Intent(ACTION_RELEASE_MIC);
      intent.putExtra(EXTRA_SOURCE_PACKAGE, getPackageName());
      sendBroadcast(intent);
  }

  private void releaseMicrophone() {
      Intent intent = new Intent(ACTION_MIC_RELEASED);
      intent.putExtra(EXTRA_SOURCE_PACKAGE, getPackageName());
      sendBroadcast(intent);
  }

  /**
   * Called when activity is paused - See Android docs
   */
  @Override
  protected void onPause() {
      super.onPause();

      // Make sure we release the microphone on pause
      releaseMicrophone();
  }
```

### [View Full Source Code](https://github.com/realwear/Developer-Examples/blob/master/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/MicrophoneReleaseActivity.java)