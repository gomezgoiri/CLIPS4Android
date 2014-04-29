package eu.deustotech.clips;

/**
 * A number consists only of digits (0‑9), a decimal point (.), a sign (+ or ‑), and, optionally, an (e) for exponential notation with its corresponding sign.
 * A number is either stored as a float or an integer.
 * Any number consisting of an optional sign followed by only digits is stored as an integer (represented internally by CLIPS as a C long integer).
 */
public class IntegerValue extends PrimitiveValue
  {
   /*****************/
   /* IntegerValue: */
   /*****************/
   public IntegerValue()
     {
      super(new Long(0));
     }

   /*****************/
   /* IntegerValue: */
   /*****************/
   public IntegerValue(
     long value)
     {
      super(new Long(value));
     }
     
   /*****************/
   /* IntegerValue: */
   /*****************/
   public IntegerValue(
     Long value)
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

   /*************/
   /* intValue: */
   /*************/
   public int intValue() throws Exception
     {
      return ((Long) getValue()).intValue();
     }

   /**************/
   /* longValue: */
   /**************/
   public long longValue() throws Exception
     {
      return ((Long) getValue()).longValue();
     }

   /***********/
   /* retain: */
   /***********/
   public void retain()
     {
      //System.out.println("IntegerValue retain");
     }
     
   /*************/
   /* release: */
   /*************/
   public void release()
     {
      //System.out.println("IntegerValue release");
     }
  }
