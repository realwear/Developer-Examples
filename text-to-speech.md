---
title: Text to Speech Example
---

The HMT-1 comes with a built in text to speech engine that can relay text to the user by reading it out aloud. This allows for quick pieces of information to be given to the user without them having to view the screen.

```java
  private final String ACTION_TTS = "com.realwear.wearhf.intent.action.TTS";
  
  private final String EXTRA_TEXT = "text_to_speak";
  private String EXTRA_ID = "tts_id";
  private final String EXTRA_PAUSE = "pause_speech_recognizer";
  
  private final int TTS_REQUEST_CODE = 34;

  private void textToSpeech() {
      final String speech = "Welcome to the HMT-1 text to speech engine";
      	  
	  final Intent intent = new Intent(ACTION_TTS);
	  intent.putExtra(EXTRA_TEXT, speech);
      intent.putExtra(EXTRA_ID, TTS_REQUEST_CODE);
      intent.putExtra(EXTRA_PAUSE, false);
      sendBroadcast(intent);
  }
```

### [View Full Source Code](https://github.com/realwear/Developer-Examples/blob/master/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/TTSActivity.java)