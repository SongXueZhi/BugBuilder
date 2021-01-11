```diff
diff --git a/defects4j/gson6fix/gson/src/main/java/com/google/gson/internal/bind/JsonAdapterAnnotationTypeAdapterFactory.java b/defects4j/gson6buggy/gson/src/main/java/com/google/gson/internal/bind/JsonAdapterAnnotationTypeAdapterFactory.java
index b52e1573..3801cfd4 100644
--- a/defects4j/gson6fix/gson/src/main/java/com/google/gson/internal/bind/JsonAdapterAnnotationTypeAdapterFactory.java
+++ b/defects4j/gson6buggy/gson/src/main/java/com/google/gson/internal/bind/JsonAdapterAnnotationTypeAdapterFactory.java
@@ -51,7 +51,7 @@ public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapte
   static TypeAdapter<?> getTypeAdapter(ConstructorConstructor constructorConstructor, Gson gson,
       TypeToken<?> fieldType, JsonAdapter annotation) {
     Class<?> value = annotation.value();
-    TypeAdapter<?> typeAdapter;
+    final TypeAdapter<?> typeAdapter;
     if (TypeAdapter.class.isAssignableFrom(value)) {
       Class<TypeAdapter<?>> typeAdapterClass = (Class<TypeAdapter<?>>) value;
       typeAdapter = constructorConstructor.get(TypeToken.get(typeAdapterClass)).construct();
@@ -64,9 +64,7 @@ public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapte
       throw new IllegalArgumentException(
           "@JsonAdapter value must be TypeAdapter or TypeAdapterFactory reference.");
     }
-    if (typeAdapter != null) {
-      typeAdapter = typeAdapter.nullSafe();
-    }
-    return typeAdapter;
+    return typeAdapter.nullSafe();
   }
 }

```
