---
title: Document Applet example
---

## Document viewer intent

The document viewer can be used by third party applications. These applications can launch the document viewer application directly, and pass in a document or image to view. In order to launch a document the application passes the location of the file they wish to open. This document must be saved locally on the device

Applications broadcast Intent.ACTION_VIEW with the file’s URI and Mime type.

```java
  File document =
      new File(Environment.getExternalStorageDirectory(), "document.pdf");
  String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
      MimeTypeMap.getFileExtensionFromUrl(document.toURI().toString())); 

  if (mimeType != null) {
    Intent intent = new Intent(Intent.ACTION_VIEW); 
    intent.addCategory(Intent.CATEGORY_DEFAULT); 
    intent.setDataAndType(Uri.fromFile(document), mimeType); 
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
  }

  startActivity(intent)
```

### [View Full Source Code](https://github.com/realwear/Developer-Examples/blob/master/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/DocumentActivity.java)