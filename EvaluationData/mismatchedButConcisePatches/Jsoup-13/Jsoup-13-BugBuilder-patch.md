```diff
diff --git a/defects4j/jsoup13fix/src/main/java/org/jsoup/nodes/Node.java b/defects4j/jsoup13buggy/src/main/java/org/jsoup/nodes/Node.java
index 9456fc4..2154a15 100644
--- a/defects4j/jsoup13fix/src/main/java/org/jsoup/nodes/Node.java
+++ b/defects4j/jsoup13buggy/src/main/java/org/jsoup/nodes/Node.java
@@ -70,7 +70,7 @@ public abstract class Node implements Cloneable {
     public String attr(String attributeKey) {
         Validate.notNull(attributeKey);
 
-        if (attributes.hasKey(attributeKey))
+        if (hasAttr(attributeKey))
             return attributes.get(attributeKey);
         else if (attributeKey.toLowerCase().startsWith("abs:"))
             return absUrl(attributeKey.substring("abs:".length()));
@@ -103,12 +103,6 @@ public abstract class Node implements Cloneable {
      */
     public boolean hasAttr(String attributeKey) {
         Validate.notNull(attributeKey);

-        if (attributeKey.toLowerCase().startsWith("abs:")) {
-            String key = attributeKey.substring("abs:".length());
-            if (attributes.hasKey(key) && !absUrl(key).equals(""))
-                return true;
-        }
         return attributes.hasKey(attributeKey);
     }
```
