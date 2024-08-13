package utilities;

public class SeleniumUtilities {

    public static void waitForSeconds(int seconds){

        try{
            Thread.sleep(seconds * 1000);
        }catch (InterruptedException e){
            e.getStackTrace();
        }
    }
}
