package eu.deustotech.clips;

/**
 * A number consists only of digits (0‑9), a decimal point (.), a sign (+ or ‑), and, optionally, an (e) for exponential notation with its corresponding sign.
 * A number is either stored as a float or an integer.
 * Any number consisting of an optional sign followed by only digits is stored as an integer (represented internally by CLIPS as a C long integer).
 * All other numbers are stored as floats (represented internally by CLIPS as a C double‑precision float).
 */
public class FloatValue extends PrimitiveValue
  {
   /***************/
   /* FloatValue: */
   /***************/
   public FloatValue()
     {
      super(new Double(0.0));
     }

   /***************/
   /* FloatValue: */
   /***************/
   public FloatValue(
     double value)
     {
      super(new Double(value));
     }

   /***************/
   /* FloatValue: */
   /***************/
   public FloatValue(
     Double value)
     {
      super(value);
     }

   /****************/
   /* numberValue: */
   /****************/
   public Number numberValue() throws Exception
     {
      return (Number) getValue();
     }
     
   /***************/
   /* floatValue: */
   /***************/
   public float floatValue() throws Exception
     {
      return ((Double) getValue()).floatValue();
     }

   /****************/
   /* doubleValue: */
   /****************/
   public double doubleValue() throws Exception
     {
      return ((Double) getValue()).doubleValue();
     }

   /***********/
   /* retain: */
   /***********/
   public void retain()
     {
      //System.out.println("FloatValue retain");
     }

   /*************/
   /* release: */
   /*************/
   public void release()
     {
      //System.out.println("FloatValue release");
     }
  }
