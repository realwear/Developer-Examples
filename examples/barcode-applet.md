---
title: HMT-1 Developer Examples
description: Barcode Applet Example
---

The HMT-1 barcode application can be used by third party applications. These applications can launch the barcode application which will ask the user to scan a barcode. The data from the captured barcode will be returned to the calling application.

## Launch barcode scanner

```java
  private static final int RequestCode = 1984;

  private static final String ACTION_BARCODE =
      "com.realwear.barcodereader.intent.action.SCAN_BARCODE";
  private static final String EXTRA_RESULT =
      "com.realwear.barcodereader.intent.extra.RESULT";

  ...

  Intent intent = new Intent(ACTION_BARCODE);
  startActivityForResult(intent, RequestCode);
```

## Listen for barcode scan result

```java
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == Activity.RESULT_OK && requestCode == RequestCode) {
      String result = "[No Barcode]";
      if (data != null) {
        result = data.getStringExtra(EXTRA_RESULT);
      }

      mBarcodeResultsView.setText(result);
    }
  }
```

### [View Full Source Code](https://github.com/realwear/Developer-Examples/blob/master/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/BarcodeActivity.java)
