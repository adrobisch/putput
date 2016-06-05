PutPut
======

PutPut is a Twitter-like web application based on open source technologies:

* Spring Boot
* Twitter Bootstrap (obviously)
* AngularJS
* PostgreSQL

Features
========

* Updates stream with @-mentions, favorites and likes
* Direct Messages with markdown support
* User-level RSS Import / Feeds
* PIM: Contacts, Calendar (WIP)
* Files Upload (WIP)

Screenshot
==========

![Screenshot](putput_screen.png "PutPut Home Screen")

Build
=====

    mvn clean install docker:build

Run
===

    docker run adrobisch/putput

Per default the datasource will be in-memory.
You can use a local PostgreSQL database by specifying the postgres profile:

    ENV APP_OPTS "-Dspring.profiles.active=production,postgres"

JDBC-URL: `jdbc:postgresql://postgres:postgres@localhost:5432/putput`

Alternatively, you can add a `/etc/putput/putput.properties` file with your settings in a derived Dockerfile.

The initial username is `user` with password `password`. This can be changed in the settings view.

License
=======

PutPut is published under the terms of the GNU Lesser General Public License Version 3.
See the [LICENSE](LICENSE) file.

![LGPLV3](lgplv3.png)
