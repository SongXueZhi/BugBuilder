```diff
diff --git a/defects4j/csv10fix/src/main/java/org/apache/commons/csv/CSVFormat.java b/defects4j/csv10buggy/src/main/java/org/apache/commons/csv/CSVFormat.java
index 5963edb..a6fde0a 100644
--- a/defects4j/csv10fix/src/main/java/org/apache/commons/csv/CSVFormat.java
+++ b/defects4j/csv10buggy/src/main/java/org/apache/commons/csv/CSVFormat.java
@@ -605,10 +605,8 @@ public final class CSVFormat implements Serializable {

-    public CSVPrinter print(final Appendable out) throws IOException {
+    public CSVPrinter print(final Appendable out) {
         return new CSVPrinter(out, this);
     }
 
diff --git a/defects4j/csv10fix/src/main/java/org/apache/commons/csv/CSVPrinter.java b/defects4j/csv10buggy/src/main/java/org/apache/commons/csv/CSVPrinter.java
index d2968b5..d048f89 100644
--- a/defects4j/csv10fix/src/main/java/org/apache/commons/csv/CSVPrinter.java
+++ b/defects4j/csv10buggy/src/main/java/org/apache/commons/csv/CSVPrinter.java
@@ -45,31 +45,24 @@ public final class CSVPrinter implements Flushable, Closeable {

-    public CSVPrinter(final Appendable out, final CSVFormat format) throws IOException {
+    public CSVPrinter(final Appendable out, final CSVFormat format) {
         Assertions.notNull(out, "out");
         Assertions.notNull(format, "format");
 
         this.out = out;
         this.format = format;
         this.format.validate();
         // TODO: Is it a good idea to do this here instead of on the first call to a print method?
         // It seems a pain to have to track whether the header has already been printed or not.
-        if (format.getHeader() != null) {
-            this.printRecord((Object[]) format.getHeader());
-        }
     }
 
     // ======================================================
```
