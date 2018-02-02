---
title: Camera Applet example
---

### Get image

In order to get an image back from RealWear's camera application you can use the standard android media intent in order to make a request. Once the request has been made the camera application will pass back the image as an extra with the parameter name of 'data'.

Note: The intent system can only handle up to 1mb when passing data back in a bundle. If you wish to get a better image quality back its best to use the file mechanism below.

```java
  private static final int CAMERA_REQUEST = 1889;

  ...

  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
  startActivityForResult(intent, CAMERA_REQUEST);

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && 
                data != null &&
                requestCode == CAMERA_REQUEST) {
            Bitmap photo = data.getExtras().getParcelable("data");
        }
  }
```

### Get image location

This mechanism will allow you to pass a URI location to the camera application and the camera application will save the image taken to this location. Once the image has been taken the application will be callback on the onActivityResult method. This will either comeback with a success or error message.

```java
  private static final int CAMERA_REQUEST_URI = 1888;

  ...

  File photoFile =
      new File(Environment.getExternalStorageDirectory(), "snapshot.jpg");
  Uri fileUri = Uri.fromFile(photoFile);

  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
  intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
  startActivityForResult(intent, CAMERA_REQUEST_URI);
```

### [View Full Source Code](https://github.com/realwear/Developer-Examples/blob/master/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/CameraActivity.java)