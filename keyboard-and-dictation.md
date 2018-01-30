---
title: Keyboard and Dictation Example
---

Most application require the user to input some form of information. The HMT-1 comes with 2 main methods for inputting information:

* A standard keyboard which allows the user to use their voice to enter information by speaking characters
* Dictation input which allows the user to speak in sentences

The best method will depend on the situation, and an application may use both methods for different types of inputs. For example the keyboard is best when entering entering an e-mail addresses while dictation would be better suited when typing out e-mail messages.

## Keyboard input

When an application wants to accept keyboard input from the user it passes in the control that the user will be typing into:

```java
  EditText textField = (EditText) findViewById(R.id.email_address);

  //
  // Make sure text field is selected so keyboard entry is directed to it
  //
  textField.setFocusable(true);
  textField.setFocusableInTouchMode(true);
  textField.requestFocus();

  InputMethodManager imm =
      (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
  imm.showSoftInput(textField, InputMethodManager.SHOW_FORCED);
```

## Dictation input

When an application wants to accept dictation input from the user it sends an intent to launch dictation and receives the result in `onActivityResult`:

```java
  // Request code identifying dictation events
  private static final int DICTATION_REQUEST_CODE = 34;

  // Dictation intent action
  private final static String ACTION_DICTATION =
      "com.realwear.keyboard.intent.action.DICTATION";

  public void launchDictation() {
      Intent intent = new Intent(ACTION_DICTATION);
      startActivityForResult(intent, DICTATION_REQUEST_CODE);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (resultCode == Activity.RESULT_OK &&
              requestCode == DICTATION_REQUEST_CODE) {
          String result = "[Error]";
          if (data != null) {
              result = data.getStringExtra("result");
          }

          EditText textField = (EditText) findViewById(R.id.email_message);
          textField.setText(result);
      }
  }
```

### [View Full Source Code](https://github.com/realwear/Developer-Examples/blob/master/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/DictationActivity.java)