This is a custom EditText library project which adds text formatting, validations and dynamic text formatting. Also included is a ColorPicker widget which is conveniently separated in to it's own package. 
### Text formatting 
* bold
* italic
* underline
* strikethrough
* subscript
* background color
* foreground color

_TODO: Paragraph formatting options such as ordered and unordered lists, tabs, left/right/center/justify, etc._

### [Validations](https://github.com/kemallette/RichEditText/wiki/Validations) 
* **regexp**: for custom regexp
* **numeric**: for an only numeric field
* **alpha**: for an alpha only field
* **alphaNumeric**: guess what?
* **email**: checks that the field is a valid email
* **creditCard**: checks that the field contains a valid credit card using Luhn Algorithm
* **phone**: checks that the field contains a valid phone number
* **domainName**: checks that field contains a valid domain name 
* **ipAddress**: checks that the field contains a valid ip address
* **webUrl**: checks that the field contains a valid url 
* **nocheck**: It does not check anything except the emptyness of the field.

### [Custom Fonts](https://github.com/kemallette/RichEditText/wiki/Custom-Fonts) 
* .TTF and .OTF supported. Android doesn't support other font formats.  

##Getting Started##
After downloading/cloning this repo, simply add this project as an Android library project. Make sure that the library is added to your project's build path. To use RichEditText, you can either declare it in your XML layouts, or programatically. For your XML layouts: 

    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:foo="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" >

         <com.RichEditText.Widget.RichEditText
            android:id="@+id/rich_edit_text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="5dp"
            foo:fontName="Roboto-Light"
            foo:testType="nocheck" />

    </LinearLayout>

Notice that I've declared an XML namespace to use the custom attributes included in the library:

    <com.RichEditText.Widget.RichEditText  
  
        foo:fontName="Roboto-Light"
        foo:testType="nocheck" />

Refer to the [Custom Fonts](https://github.com/kemallette/RichEditText/wiki/Custom-Fonts) section for more information on fontName="Roboto-Light". For more information on testType="nocheck" and other included validations, refer to [Validations](https://github.com/kemallette/RichEditText/wiki/Validations)

###Special Thanks###
Validations
[Android-form-edittext](https://github.com/vekexasia/android-form-edittext)
By [Vekexasia](https://github.com/vekexasia)

ColorPicker
[HoloColorPicker](https://github.com/LarsWerkman/HoloColorPicker)
By [LarsWerkman](https://github.com/LarsWerkman)

FileExplorer
[aFileChooser](https://github.com/iPaulPro/aFileChooser)
By [iPaulPro](https://github.com/iPaulPro)
