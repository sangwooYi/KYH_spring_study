package hello.core.order;

public class Order {

    private Long mermberId;
    private String itemName;
    private int itemPrice;
    private int discountPrice;

    public Order(Long mermberId, String itemName, int itemPrice, int discountPrice) {
        this.mermberId = mermberId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
    }

    public Long getMermberId() {
        return mermberId;
    }

    public void setMermberId(Long mermberId) {
        this.mermberId = mermberId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int calculatePrice() {
        return this.itemPrice-this.discountPrice;
    }

    // 객체 출력시 아래 결과대로 나올 수 있도록 (편의성)
    @Override
    public String toString() {
        return "Order{" +
                "mermberId=" + mermberId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
