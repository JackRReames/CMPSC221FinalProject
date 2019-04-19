package final221.database;

/**
 *
 * @author Nicholas
 */
public class transactionBean {
//    private int transID;
//    private int custID;
//    private int prodID;
    private long transTimestamp;
    
    public transactionBean() {
        transTimestamp = System.currentTimeMillis();
    }
}
