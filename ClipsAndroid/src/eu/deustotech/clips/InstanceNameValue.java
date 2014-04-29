package eu.deustotech.clips;

/**
 * <p>
 * Within the scope of a module, an instance can also be uniquely referred to by name.
 * An instance‑name is formed by enclosing a symbol within left and right brackets.
 * Thus, pure symbols may not be surrounded by brackets.
 * If the CLIPS Object Oriented Language (COOL) is not included in a particular CLIPS configuration, then brackets may be wrapped around symbols.
 * </p>
 * 
 * <p>Some examples of instance‑names are:</p>
 * <ul>
 * 	<li>[pump-1]</li>
 * 	<li>[foo]</li>
 * 	<li>[+++]</li>
 * 	<li>[123-890]</li>
 * </ul>
 */
public class InstanceNameValue extends InstanceValue
  {
   /**********************/
   /* InstanceNameValue: */
   /**********************/
   public InstanceNameValue()
     {
      super(new String(""));
     }

   /**********************/
   /* InstanceNameValue: */
   /**********************/
   public InstanceNameValue(
     String value)
     {
      super(value);
     }
     
   /****************/
   /* lexemeValue: */
   /****************/
   public String lexemeValue() throws Exception
     {
      return (String) getValue();
     }
     
   /**********************/
   /* instanceNameValue: */
   /**********************/
   public String instanceNameValue() throws Exception
     {
      return (String) getValue();
     }

   /***********/
   /* retain: */
   /***********/
   public void retain()
     {
      //System.out.println("InstanceNameValue retain");
     }
     
   /*************/
   /* release: */
   /*************/
   public void release()
     {
      //System.out.println("InstanceNameValue release");
     }
   
   /*************/
   /* toString: */
   /*************/
   public String toString()
     {        
      return "[" + super.toString() + "]";
     }
  }
