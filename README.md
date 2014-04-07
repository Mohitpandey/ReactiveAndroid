ReactiveAndroid
===============

This is an experimental Android app. It shows reactive programming paradigm by using RxJava from Netflix. To make coding
more pleasant, it uses Lamdas from Java 8. The app pulls the content from [Rotten Tomatoes API](http://developer.rottentomatoes.com/ "API").
Please use this link and register to get your own developer key. It is needed to run this demo app.

Place the key under src/main/res/values/key.xml (or any other name). key.xml is already added to .gitignore so it will
save you some work.

Here is what the contents of key.xml look like:
<pre><code>
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="ROTTEN_KEY">YOUR DEVELOPER KEY</string>
</resources>
</code></pre>

In progress:

The work in this app is still in progress. I want to also demonstrate usage of Dagger, Otto(?) and couple more
useful libraries.
