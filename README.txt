Supplied with this project is both an eclipse project file and an 
ant build file. I have ran into portability issues in the past with
eclipse project files when importing them other computers; therefore,
I highly recommend using the ant build file to build and run the
application. Using the ant build file is explained below.


To build the project, run the following command:
------------------------------------------------------------------
$ ant build


To run the application, run the following command:
------------------------------------------------------------------
$ ant Boot


------------------------------------------------------------------
Subversion Repository & Trac Instance
------------------------------------------------------------------
The application was originally developed on another repository, but
was relocated to the location below. When relocating the repository,
a complete dump was performed and therefore the new repository still
contains all the revision history. A trac instance was used during 
the development of this project. Trac was used to manage the tickets
and bugs, as well as to help distribute the workload. The trac 
instance is for the old repository location; however, it is up to 
date and still accessible at the location below.

Svn:  https://pl3.projectlocker.com/codecompanion/web/svn/trunk/apps/PhotoGallery
User: cs319
Password:  cs319fall2010
Trac: http://trac.codecompanion.org/trunk


------------------------------------------------------------------
Credits & Attributions
------------------------------------------------------------------

*  All Icons used in this project are apart of the Oxygen icon theme.
   Website: http://www.oxygen-icons.org/
   License: Creative Common Attribution-ShareAlike 3.0 License

*  Sanaware Java Docking Framework (javadocking.jar)
   Website: http://www.javadocking.com/
   License: GPL

*  Metadata Extractor (metadata-extractor-2.3.1.jar)
   Website: http://www.drewnoakes.com/drewnoakes.com/code/exif/
   License: None

*  JCompanion (JCompanion.jar) is small collection of java classes
   written by me.

------------------------------------------------------------------
Notes
------------------------------------------------------------------
* The Sanaware Java Docking library is released under the GPL
license, which technically means the project must be using the
GPL license in order to distribute the library with the application.
I don't see a point in putting a license on this project since
it was for a class project and won't be released to the public.
I assume this is okay for this situation and have included the 
library in the source code under 'src/runtime/resources/lib'.