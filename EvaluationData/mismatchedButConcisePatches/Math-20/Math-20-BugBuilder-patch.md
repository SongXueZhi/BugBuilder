```diff
diff --git a/defects4j/math20fix/src/main/java/org/apache/commons/math3/optimization/direct/CMAESOptimizer.java b/defects4j/math20buggy/src/main/java/org/apache/commons/math3/optimization/direct/CMAESOptimizer.java
index b54cb3744..d01cd158c 100644
--- a/defects4j/math20fix/src/main/java/org/apache/commons/math3/optimization/direct/CMAESOptimizer.java
+++ b/defects4j/math20buggy/src/main/java/org/apache/commons/math3/optimization/direct/CMAESOptimizer.java
@@ -24,11 +24,9 @@ import java.util.List;
 import org.apache.commons.math3.analysis.MultivariateFunction;
 import org.apache.commons.math3.exception.DimensionMismatchException;
 import org.apache.commons.math3.exception.MathUnsupportedOperationException;
-import org.apache.commons.math3.exception.MathIllegalStateException;
 import org.apache.commons.math3.exception.NotPositiveException;
 import org.apache.commons.math3.exception.OutOfRangeException;
 import org.apache.commons.math3.exception.TooManyEvaluationsException;
-import org.apache.commons.math3.exception.util.LocalizedFormats;
 import org.apache.commons.math3.linear.Array2DRowRealMatrix;
 import org.apache.commons.math3.linear.EigenDecomposition;
 import org.apache.commons.math3.linear.MatrixUtils;
@@ -416,7 +414,7 @@ public class CMAESOptimizer
                     bestValue = bestFitness;
                     lastResult = optimum;
                     optimum = new PointValuePair(
-                            fitfun.repairAndDecode(bestArx.getColumn(0)),
+                            fitfun.decode(bestArx.getColumn(0)),
                             isMinimize ? bestFitness : -bestFitness);
                     if (getConvergenceChecker() != null && lastResult != null) {
                         if (getConvergenceChecker().converged(iterations, optimum, lastResult)) {
@@ -913,16 +911,6 @@ public class CMAESOptimizer
             return res;
         }
 
-        public double[] repairAndDecode(final double[] x) {
-            return boundaries != null && isRepairMode ?
-                decode(repair(x)) :
-                decode(x);
-        }

         /**
          * @param x Normalized objective variables.
          * @return the original objective variables.

```
