---
title: Movie Applet Example
---

The movie viewer can be used by third party applications. These applications can launch the movie viewer application directly, and pass in a video file. In order to launch a video the application can pass the location of the file they wish to open. This video must be saved locally on the device.

Applications broadcast Intent.ACTION_VIEW with the file’s URI and Mime type.

```java
  File movie =
      new File(Environment.getExternalStorageDirectory(), "movie.mp4");
  String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
    MimeTypeMap.getFileExtensionFromUrl(file.toURI().toString())); 

  Intent intent = new Intent(Intent.ACTION_VIEW); 
  intent.addCategory(Intent.CATEGORY_DEFAULT); 
  intent.setDataAndType(Uri.fromFile(file), mimeType); 
  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
  startActivity(intent)
```

### [View Full Source Code](https://github.com/realwear/Developer-Examples/blob/master/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/MovieActivity.java)