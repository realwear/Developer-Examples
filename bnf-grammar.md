---
title: HMT-1 Developer Examples
description: BNF Grammar Example
---

Static speech commands are not suitable when there are a large number of selections available to a user. For instance, to have a speech command for entering a time would require 60 speech commands for every minute value, multiplied by the 24 hours in a day. That would mean your application would need to register 1,440 voice commands!

To solve this issue your application can register BNF grammar to the speech engine. This allows for 2 groups of words to be combined to make a single voice command. 

Once the BNF grammar has been registered the speech engine will listen for any of the possible combinations and report back the spoken command as a string for processing. 

The following example allows the user to select 1 to 10 of a certain fruit. For example the user could say "2 bananas", "8 oranges", ...

```java
  private final String BnfString = "#BNF+EM V2.0;" +
      "!grammar Commands;\n" +
      "!start <Commands>;\n" +
      "<Commands>:<global_commands>|<Hour> !optional(<Minute>);\n" +
      "<number>:1|2|3|4|5|6|7|8|9|10;\n" +
	  "<fruit>:apples|bananas|oranges;";
  
  private final String ACTION_OVERRIDE_COMMANDS =
      "com.realwear.wearhf.intent.action.OVERRIDE_COMMANDS";
	  
  private final String EXTRA_SOURCE_PACKAGE =
      "com.realwear.wearhf.intent.extra.SOURCE_PACKAGE";
  private static final String EXTRA_COMMANDS =
      "com.realwear.wearhf.intent.extra.COMMANDS";

  private void textToSpeech() {
	  final Intent intent = new Intent(ACTION_OVERRIDE_COMMANDS);
      intent.putExtra(EXTRA_SOURCE_PACKAGE, getPackageName());
      intent.putExtra(EXTRA_COMMANDS, BnfString);
      sendBroadcast(intent);
  }
```

### [View Full Source Code](https://github.com/realwear/Developer-Examples/blob/master/hmt1developerexamples/src/main/java/com/realwear/hmt1developerexamples/BNFGrammarActivity.java)