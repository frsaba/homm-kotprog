package Utils;


/**
 * A Thread.sleep exceptionjét le kell kezelni mert java
 */
public class Sleep {
    public static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
           e.printStackTrace();
        }

    }
    
}
