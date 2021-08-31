package cdw.cdwproject.model.order;

public class FormDataUpdateStatus {
private int oID;
     private OrderStatus status;
     public boolean isSuccess;


    public int getoID() {
        return oID;
    }

    public void setoID(int oID) {
        this.oID = oID;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public void setStatus(String status) {
     switch (status){
         case "PACKING":
             this.status = OrderStatus.PACKING;
             break;
         case "PACKED":
             this.status = OrderStatus.PACKED;
             break;
         case "CANCEL":
             this.status = OrderStatus.CANCEL;
             break;
         case "SHIPPING":
             this.status = OrderStatus.SHIPPING;
             break;
         case "FINISH":
             this.status = OrderStatus.FINISH;
             isSuccess = true;
             break;
         case "PENDING":
             this.status = OrderStatus.PENDING;
             break;
         case "CREATED":
         default:
             this.status = OrderStatus.CREATED;
     }

    }
}
