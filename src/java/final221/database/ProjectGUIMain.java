package final221.database;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.UIManager;
/**
 * CMPSC 221 Final Program
 * DBFrameClass.java
 * Purpose: Creates the window to hold the admin DB access GUI
 * 
 * @author Nicholas Hutton
 * @version 1.0 4/30/2019
 */
    
public class ProjectGUIMain {
    
    /**
    * The main function creates the window
    * 
    * @param args not used 
    */
    public static void main(String[] args) {

        // create an object of the gui class we wrote
        DBFrameClass dbFrame = new DBFrameClass(); 
        dbFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dbFrame.setLocationRelativeTo(null);
        // 700x250 seems to fit everything nicely in Mac Look-And-Feel, will definitely be overkill in Metal
        //printerFrame.setSize(700, 250); // set frame size
        dbFrame.setVisible(true); // display frame
    }
    
}
