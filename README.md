# Dropwizard-guicey extensions
[![License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](http://www.opensource.org/licenses/MIT)
[![Build Status](http://img.shields.io/travis/xvik/dropwizard-guicey-ext.svg?style=flat)](https://travis-ci.org/xvik/dropwizard-guicey-ext)
[![Coverage Status](https://img.shields.io/coveralls/xvik/dropwizard-guicey-ext.svg?style=flat)](https://coveralls.io/r/xvik/dropwizard-guicey-ext?branch=master)

### About

Dropwizard [guicey](https://github.com/xvik/dropwizard-guicey) extensions and integrations. 
Provided modules may be used directly and for educational purposes (as examples for custom integrations).

NOTE: Guicey and extension modules use *different* versions, because release cycles are not unified (obviously, extensions would release more often, at least at first).
But all modules use the same version. Provided BOM simplifies version management even more.

Also, note that guicey base package (`ru.vyarus.dropwizard.guice`) is different from extensions base package (`ru.vyarus.guicey`)

### Setup
 
Releases are published to [bintray jcenter](https://bintray.com/vyarus/xvik/dropwizard-guicey-ext/) and 
[maven central](https://maven-badges.herokuapp.com/maven-central/ru.vyarus/dropwizard-guicey-ext) 

<!---
[![JCenter](https://img.shields.io/bintray/v/vyarus/xvik/dropwizard-guicey-ext.svg?label=jcenter)](https://bintray.com/vyarus/xvik/dropwizard-guicey-ext/_latestVersion)
[![Maven Central](https://img.shields.io/maven-central/v/ru.vyarus.guicey/guicey-bom.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/ru.vyarus.guicey/guicey-bom)
-->

You can either use [modules](#modules) directly (in this case see module page for setup) or use provided [BOM](guicey-bom)
to unify versions management.

[BOM usage](guicey-bom#setup) is recommended.

Note that BOM will also provide guice and dropwizard boms, so you can avoid declaring versions of these modules too. 

<!--
##### Snapshots

You can use snapshot versions through [JitPack](https://jitpack.io):

* Go to [JitPack project page](https://jitpack.io/#xvik/dropwizard-guicey-ext)
* Select `Commits` section and click `Get it` on commit you want to use (top one - the most recent)
* Follow displayed instruction: add repository and change dependency (NOTE: due to JitPack convention artifact group will be different)
-->

### Modules

#### [Guava EventBus integration](guicey-eventbus) 

Module provides integration with Guava EventBus: automates subscribtions, report events with subscriptions and registers EventBus for inject.

-
[![java lib generator](http://img.shields.io/badge/Powered%20by-%20Java%20lib%20generator-green.svg?style=flat-square)](https://github.com/xvik/generator-lib-java)