---
title: HMT-1 Developer Examples
description: Help Menu Capture Example
---

Applications should generally display their common commands on screen for the user to easily know what to say, but in some instances this may not be practical. In these instances it is still useful for the user to easily find what voice commands are available. 

The global "Show Help" command was designed to handle these situations and always available to the user by default, unless an application has disabled it. By default the show help screen lists the global voice commands that are available but applications can also list their own commands.

```java
  private static final String ACTION_UPDATE_HELP =
      "com.realwear.wearhf.intent.action.UPDATE_HELP_COMMANDS";

  private static final String EXTRA_TEXT =
      "com.realwear.wearhf.intent.extra.HELP_COMMANDS";
  private static final String EXTRA_SOURCE =
      "com.realwear.wearhf.intent.extra.SOURCE_PACKAGE";

  private void updateShowHelp() {
      final ArrayList<String> helpCommands = new ArrayList<>();
      helpCommands.add("Command 1");
      helpCommands.add("Command 2");

      final Intent intent = new Intent(ACTION_UPDATE_HELP);
      intent.putStringArrayListExtra(EXTRA_TEXT, helpCommands);
      intent.putExtra(EXTRA_SOURCE, getPackageName());
      sendBroadcast(intent);
  }
```

### [View Full Source Code](https://github.com/realwear/Developer-Examples/blob/master/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/ShowHelpActivity.java)

## API

|  Intent | Description  |
| --- |  --- |
| com.realwear.wearhf.intent.action.UPDATE_HELP_COMMANDS |  Intent name |

|  Intent Extra | Data Type | Description  |
| --- |  --- |  --- |
| com.realwear.wearhf.intent.extra.HELP_COMMANDS |  String Array |  The list of commands for the help window to show |
| -com.realwear.wearhf.intent.extra.SOURCE_PACKAGE |  String |  The package name of the application |
