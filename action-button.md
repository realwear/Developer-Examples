---
title: HMT-1 Developer Examples
description: Action Button Example
---

The default behavior of the action button is to navigate the user back to the system's home screen. This behavior can be overwritten by an application if required. Since this changes the behavior that the user may expect it is not recommended to use this feature often. 

The action button functions as a normal key press and so can be handled in the standard Android way for any other key press. The action button's key code is set to 500.

```java
  private static final int ActionBtnKeyCode = 500;

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    switch (keyCode) {
      case ActionBtnKeyCode:
        // Action key is down - return true to stop default behavior
        return true;
      }

      return super.onKeyDown(keyCode, event);
  }

  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    switch (keyCode) {
      case ActionBtnKeyCode:
        // Action key has been released - return true to stop default behavior
        return true;
    }

    return super.onKeyUp(keyCode, event);
  }
```

### [View Full Source Code](https://github.com/realwear/Developer-Examples/blob/master/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/ActionButtonActivity.java)
