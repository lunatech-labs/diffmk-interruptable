# Interruptable diffmk

Diffmk can take a long time, so you may want to limit the amount of time available to it. This fork does a check to see if the thread running diffmk was interrupted, and throws InterruptedException if so.

This code was forked from SVN revision 9 from [http://sourceforge.net/p/diffmk/code/HEAD/tree/](http://sourceforge.net/p/diffmk/code/HEAD/tree/)

## Build process

Import the IntelliJ project, then Build->Build Artifacts to get a jar.
