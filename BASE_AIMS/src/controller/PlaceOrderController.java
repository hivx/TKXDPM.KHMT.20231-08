package controller;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;
import utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

public class PlaceOrderController extends BaseController {

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder
     * button
     *
     * @throws SQLException
     */
    public void placeOrder() throws SQLException {
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     *
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException {
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                    cartMedia.getQuantity(),
                    cartMedia.getPrice());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     *
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {

        order.createOrderEntity();
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     *
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException {
        validateDeliveryInfo(info);
    }

    /**
     * The method validates the info
     *
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {

    }


    /**
     * @param phoneNumber
     * @return boolean
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10)
            return false;
        if (Character.compare(phoneNumber.charAt(0), '0') != 0)
            return false;
        try {
            Long.parseUnsignedLong(phoneNumber);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }


    /**
     * @param name
     * @return boolean
     */
    public boolean validateContainLetterAndNoEmpty(String name) {
        // Check name is not null
        if (name == null)
            return false;
        // Check if contain leter space only
        if (name.trim().length() == 0)
            return false;
        // Check if contain only leter and space
        if (name.matches("^[a-zA-Z ]*$") == false)
            return false;
        return true;
    }

    /**
     * This method calculates the shipping fees of order
     *
     * @param order
     * @param weight
     * @param cartSize
     * @return shippingFee
     */
    public int calculateShippingFee(int amount, int weight) {
        if (amount >= 100) {
            return 0; // Nếu amount lớn hơn 100000, phí vận chuyển là 0.
        } else {
            // Giá cho 0.5 kg đầu là 30000
            int baseFee = 30;
            // Số lượng 0.5 kg tiếp theo
            int additionalWeight = (weight * 2) - 1; // Trừ đi 1 vì đã tính 0.5 kg đầu
            // Phí cho trọng lượng thêm theo quy tắc 2500 cho mỗi 0.5 kg
            int additionalFee = additionalWeight * 2;

            // Tổng phí
            int fees = baseFee + additionalFee;
            return fees;
        }
    }

    /**
     * This method get product available place rush order media
     *
     * @param order
     * @return media
     * @throws SQLException
     */
    public Media getProductAvailablePlaceRush(Order order) throws SQLException {
        Media media = new Media();
        for (OrderMedia pd : order.getlstOrderMedia()) {
            // CartMedia cartMedia = (CartMedia) object;
            if( validateMediaPlaceRushorder()){
                media = pd.getMedia();
            }
        }
        return media;
    }


    /**
     * @param province
     * @param address
     * @return boolean
     */
    public boolean validateAddressPlaceRushOrder(String province, String address) {
        if (!validateContainLetterAndNoEmpty(address))
            return false;
        if (!province.equals("Hà Nội"))
            return false;
        return true;
    }


    /**
     * @return boolean
     */
    public boolean validateMediaPlaceRushorder() {
        if (Media.getIsSupportedPlaceRushOrder())
            return true;
        return false;
    }
}
