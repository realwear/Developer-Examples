# Synopsis
This example will show you how to speech enable an application with WearML. 

## Task 1: Install Test Application
### Install Test Application
In order to install this example you will need the RWExplorer application running on your computer. 
   <br>      1. Click the Clone or download button and the top of this page and then click download as '.zip'
        ![alt text](/images/download.PNG)

```xml

<WearML>
    <Package>com.samples.realwear.wearml</Package>
    <Language>en_US</Language>
    <UniqueIdentifier id_or_type_or_text_or_content_description="xyz"/>
    <View type="android.widget.FrameLayout">
        <View id="com.samples.realwear.wearml:id/boar" type="android.widget.ImageButton"/>
        <View id="com.samples.realwear.wearml:id/sunshine" type="android.widget.ImageButton"/>
        <View id="com.samples.realwear.wearml:id/amber" type="android.widget.ImageButton"/>
        <View id="com.samples.realwear.wearml:id/title" text="Full Boar Scotch Ale" type="android.widget.TextView"/>
        <View id="com.samples.realwear.wearml:id/description" text="Deeply malted and caramel apparent with overtones of chocolate and a mild, smoky flavor. A complex beer that is light on the palette and easy drinking." type="android.widget.TextView"/>
    </View>
</WearML>

```

Create UniqueIdentifier

```xml
    <UniqueIdentifier id="com.samples.realwear.wearml:id/boar"/>
```

Set to always visable
```xml
    <View id="com.samples.realwear.wearml:id/boar" type="android.widget.ImageButton" overlay_persists="yes"/>
```

Set speech command 
```xml
    <View id="com.samples.realwear.wearml:id/boar" type="android.widget.ImageButton" overlay_persists="yes" speech_command="Select Boar" overlay_show_text="yes"/>
```

Switch Number off
```xml
    <View id="com.samples.realwear.wearml:id/boar" type="android.widget.ImageButton" overlay_persists="yes" speech_command="Select Boar" overlay_show_text="yes" overlay_show_number="no"/>
```

## End 
```xml
<WearML>
    <Package>com.samples.realwear.wearml</Package>
    <Language>en_US</Language>
    <UniqueIdentifier id="com.samples.realwear.wearml:id/boar"/>
    <View type="android.widget.FrameLayout">
        <View id="com.samples.realwear.wearml:id/boar" type="android.widget.ImageButton" overlay_persists="yes" speech_command="Select Boar" overlay_show_text="yes" overlay_show_number="no"/>        
        <View id="com.samples.realwear.wearml:id/sunshine" type="android.widget.ImageButton" overlay_persists="yes" speech_command="Select Sunshine" overlay_show_text="yes" overlay_show_number="no"/>
        <View id="com.samples.realwear.wearml:id/amber" type="android.widget.ImageButton" overlay_persists="yes" speech_command="Select Amber" overlay_show_text="yes" overlay_show_number="no"/>
        <View id="com.samples.realwear.wearml:id/title" text="Full Boar Scotch Ale" type="android.widget.TextView"/>
        <View id="com.samples.realwear.wearml:id/description" text="Deeply malted and caramel apparent with overtones of chocolate and a mild, smoky flavor. A complex beer that is light on the palette and easy drinking." type="android.widget.TextView"/>
    </View>
</WearML>
```


