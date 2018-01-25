---
title: HMT-1 Developer Examples
description: Audio Capture Example
---

Audio is delivered to applications using the standard Android mechanisms, so an application that records audio should work out of the box on a HMT-1.

It is possible to record audio in mono or stereo at the following sample rates:

* 8KHz
* 16KHz
* 44KHz
* 48KHz

The WearHF service handles microphone selection automatically and audio delivered to an application after the HMT-1 has preformed noise cancellation, so an application simply needs to record from the microphone.

```java
  int bufferSize = AudioRecord.getMinBufferSize(
      mSampleRate, monoOrStereo, AudioFormat.ENCODING_PCM_16BIT);

  AudioRecord audioRecorder = new AudioRecord(
      MediaRecorder.AudioSource.MIC,
      8000, // Sample rate
      AudioFormat.CHANNEL_IN_STEREO,
      AudioFormat.ENCODING_PCM_16BIT,
      bufferSize);

  mAudioRecorder.startRecording();  
```

### [View Full Source Code](https://github.com/realwear/Developer-Examples/blob/master/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/AudioCaptureActivity.java)