```diff
diff --git a/defects4j/lang14fix/src/main/java/org/apache/commons/lang3/StringUtils.java b/defects4j/lang14buggy/src/main/java/org/apache/commons/lang3/StringUtils.java
index 4733b7e9..ff6e35cf 100644
--- a/defects4j/lang14fix/src/main/java/org/apache/commons/lang3/StringUtils.java
+++ b/defects4j/lang14buggy/src/main/java/org/apache/commons/lang3/StringUtils.java

@@ -772,28 +771,20 @@ public class StringUtils {

     public static boolean equals(CharSequence cs1, CharSequence cs2) {
-        if (cs1 == cs2) {
-            return true;
-        }
-        if (cs1 == null || cs2 == null) {
-            return false;
-        }
-        if (cs1 instanceof String && cs2 instanceof String) {
-            return cs1.equals(cs2);
-        }
-        return CharSequenceUtils.regionMatches(cs1, false, 0, cs2, 0, Math.max(cs1.length(), cs2.length()));
+        return cs1 == null ? cs2 == null : cs1.equals(cs2);
     }
```
