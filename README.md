RichEditText
============

This is a custom EditText that Includes validations and custom font picking (xml attributes and programatically). Future feature additions will include basic, native rich text editing functionality.

GETTING STARTED
---------------
//TODO :P


CUSTOM FONTS
------------
Supported font formats are .ttf and .otf. 

First, you'll need to add your fonts to YOUR project's assets/fonts folder. If you don't have a fonts folder in your assets directory, create one. 

For .otf fonts, you'll need to add the name for each font you would like to use to this library's arrays.xml file (res/values/arrays.xml). Opening arrays.xml, you will see:

	<string-array name="otf_fonts">
        <item>RandomFontNameHere</item>
    </string-array>

Simply add an item for each .otf font you are using. Double check to make sure your font is located in YOUR project's assets/fonts folder. Here's what it looks like with three example fonts: foo.otf, Bar.otf, fuz.otf.

	<String-array name="otf_fonts">
        <item>foo</item>
        <item>Bar</item>
        <item>fuz</item>
    </string-array>

Now you're ready to use your fonts by using the fontName attribute in your xml layouts. 