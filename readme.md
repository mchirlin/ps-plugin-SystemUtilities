System Utilities
=========

System Utilities is a plugin and application that empowers users to perform various system utilities typically only available through the API.

Various Utilities include
  - Content Browser
  - Content Details
  - Update Folder Searchability
  - Copy Rule or Constant
  - Rename Rule or Constant
  - Rename Rules Folder
  - Process Model Browser
  - Process Model Details
  - Legacy Form to SAIL Conversion
  - Legacy Report to SAIL Conversion
  - Create Datatype XSD
  - Convert DDL to Datatype
  - Grab Log Files

Version
----

1.3.0

Required Plugins
-----------

* [Legacy Forms Designer to SAIL Form] - returns SAIL Form text that you can then copy into your own rules.
* [Portal to SAIL] - provides functions to retrieve portal report data for use in Tempo Records, Tempo Reports and processes.

Replaces Plugins
--------------

* [Content Details by UUID] - retrieves content details by ID or UUID.  This plugin's version returns all data in LabelValue pairs instead of as a string.
* [Custom Content Functions] - extend Appian content and offer document/folder search, directory listing and the ability to get/set the searchable attribute of a folder. This version returns some data as LabelValues for better display on SAIL forms.

Installation
--------------

```
deploy System Utilities jar
deploy Portal to SAIL jar
deploy Legacy Forms Designer to SAIL Form jar

import System Utilities application
```

[Legacy Forms Designer to SAIL Form]:https://forum.appian.com/suite/tempo/records/type/components/item/i8BWsQdLlzKy55h8z8zJ0sPqpDWFrba_b1bgFKXBD1O1vna17flqFUwELUxrOFkvA/view/summary
[Portal to SAIL]:https://forum.appian.com/suite/tempo/records/type/components/item/i8BWsQdLlzKy55h8z8zJ0sPqpDWFrba_bxb1lcYHs5SKFyBZTLc_SjsoHud0SOfWg/view/summary
[Content Details by UUID]:https://forum.appian.com/suite/tempo/records/type/components/item/i4BWsQdLlzKy55h8z8zJ0sPqpDWFrba9ry_HEcQwZo8zbOvJLVMf5NGpZPe_l48/view/summary
[Custom Content Functions]:https://forum.appian.com/suite/tempo/records/type/components/item/i8BWsQdLlzKy55h8z8zJ0sPqpDWFrba_rxb_n8SaSJXdwOc6LYC9dx81kBEz1Z6Cw/view/summary
