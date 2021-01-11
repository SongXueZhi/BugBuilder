```diff
diff --git a/defects4j/compress34fix/src/main/java/org/apache/commons/compress/archivers/zip/X7875_NewUnix.java b/defects4j/compress34buggy/src/main/java/org/apache/commons/compress/archivers/zip/X7875_NewUnix.java
index e325b564..87d1e1d4 100644
--- a/defects4j/compress34fix/src/main/java/org/apache/commons/compress/archivers/zip/X7875_NewUnix.java
+++ b/defects4j/compress34buggy/src/main/java/org/apache/commons/compress/archivers/zip/X7875_NewUnix.java
@@ -32,8 +32,6 @@ import static org.apache.commons.compress.archivers.zip.ZipUtil.unsignedIntToSig
 
 public class X7875_NewUnix implements ZipExtraField, Cloneable, Serializable {
     private static final ZipShort HEADER_ID = new ZipShort(0x7875);
-    private static final ZipShort ZERO = new ZipShort(0);
     private static final BigInteger ONE_THOUSAND = BigInteger.valueOf(1000);
     private static final long serialVersionUID = 1L;
 
@@ -144,7 +134,7 @@ public class X7875_NewUnix implements ZipExtraField, Cloneable, Serializable {
      * @return a <code>ZipShort</code> for the length of the data of this extra field
      */
     public ZipShort getCentralDirectoryLength() {
-        return ZERO;
+        return getLocalFileDataLength();  // No different than local version.
     }
 
     /**
@@ -191,7 +181,7 @@ public class X7875_NewUnix implements ZipExtraField, Cloneable, Serializable {
      * @return get the data
      */
     public byte[] getCentralDirectoryData() {
-        return new byte[0];
+        return getLocalFileDataData();
     }
 
     /**
@@ -220,12 +210,14 @@ public class X7875_NewUnix implements ZipExtraField, Cloneable, Serializable {
     }

     public void parseFromCentralDirectoryData(
             byte[] buffer, int offset, int length
     ) throws ZipException {
+        reset();
+        parseFromLocalFileData(buffer, offset, length);
     }
 
     /**
```
