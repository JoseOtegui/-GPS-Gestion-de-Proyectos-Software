BEGIN;
TRUNCATE countries;
 
INSERT INTO `usuarios` VALUES
    ("elmillor@imbecil.es", "69"),
    ("sote@payaso.net", "88" ),
    ("shrek@cienaga.gr", "420");
 
COMMIT;