//package views.screen.shipping;
//
//import controller.PlaceOrderController;
//import controller.PlaceRushOrderController;
//import entity.cart.CartMedia;
//import entity.invoice.Invoice;
//import entity.order.Order;
//import entity.shipping.Shipment;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.input.MouseEvent;
//import javafx.stage.Stage;
//import utils.Configs;
//import views.screen.BaseScreenHandler;
//import views.screen.cart.CartScreenHandler;
//import views.screen.cart.MediaHandler;
//import views.screen.invoice.InvoiceScreenHandler;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import static views.screen.home.HomeScreenHandler.LOGGER;
//
//public class DeliveryMethodsScreenHandler extends BaseScreenHandler {
//
//    private Order order;
//
//    @FXML
//    private RadioButton placeRushOrderValue;
//
//    @FXML
//    private RadioButton placeOrderValue;
//
//    @FXML
//    private TextField deliveryInstruction;
//
//    @FXML
//    private TextField shipmentDetail;
//
//    @FXML
//    private DatePicker deliveryTime;
//
//    @FXML
//    private Label errorProvince;
//
//    @FXML
//    private Button updateDeliveryMethodInfoButton;
//
//    private CartScreenHandler cartScreen;
//
//    private List<CartMedia> selectedCartMediaList;
//
//    private ShippingScreenHandler shippingScreenHandler;
//
//
//    public DeliveryMethodsScreenHandler(Stage stage, String screenPath, Order order, CartScreenHandler cartScreen, List<CartMedia> selectedCartMediaList) throws IOException {
//        super(stage, screenPath);
//        this.order = order;
//        this.cartScreen = cartScreen;
//        this.selectedCartMediaList = selectedCartMediaList;
//    }
//
//    @FXML
//    private void updateDeliveryMethodInfo(MouseEvent event) throws IOException {
//        String deliveryInstructionString = deliveryInstruction.getText();
//        String shipmentDetailString = shipmentDetail.getText();
//        String deliveryDateString = (deliveryTime.getValue() != null) ? deliveryTime.getValue().toString() : "";
//
//        int typeDelivery = (placeRushOrderValue.isSelected()) ? Configs.PLACE_RUSH_ORDER : Configs.PALCE_ORDER;
//
//        var shipment = new Shipment(typeDelivery);
//        shipment.setShipmentDetail(shipmentDetailString);
//        shipment.setDeliveryTime(deliveryDateString);
//        shipment.setDeliveryInstruction(deliveryInstructionString);
//
//        // Validate placeRushOrder data and update order's shipment
//        PlaceRushOrderController.validatePlaceRushOrderData(shipment);
//        order.setShipment(shipment);
//
//        List<CartMedia> selectedMediaList = cartScreen.getMediaHandler().getSelectedCartMediaList();
//
//        LOGGER.info("Selected Media List Size (in DeliveryMethodsScreenHandler): " + selectedMediaList.size());
//        LOGGER.info("abc: " + cartScreen.getMediaHandler().getSelectedCartMediaList());
//
//        // Create invoice screen only if there are selected items in the cart
//        if (selectedMediaList.isEmpty()) {
//            try {
//                Invoice invoice = getBController().createInvoice(order);
//                InvoiceScreenHandler invoiceScreenHandler = new InvoiceScreenHandler(stage, Configs.INVOICE_SCREEN_PATH, invoice, selectedMediaList);
//                invoiceScreenHandler.setPreviousScreen(this);
//                invoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
//                invoiceScreenHandler.setScreenTitle("Invoice Screen");
//                invoiceScreenHandler.setBController(getBController());
//                invoiceScreenHandler.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            // Display an error message when trying to proceed without selected items
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("You don't have anything selected to placee");
//            alert.showAndWait();
//        }
//
////        MediaHandler mediaHandler = cartScreen.getMediaHandler();
////
////        // Create invoice screen only if there are selected items in the cart
////        if (mediaHandler.getSelectedCartMediaList().size()>0) {
////            try {
////                Invoice invoice = getBController().createInvoice(order);
////                InvoiceScreenHandler invoiceScreenHandler = new InvoiceScreenHandler(stage, Configs.INVOICE_SCREEN_PATH, invoice, mediaHandler.getSelectedCartMediaList());
////                invoiceScreenHandler.setPreviousScreen(this);
////                invoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
////                invoiceScreenHandler.setScreenTitle("Invoice Screen");
////                invoiceScreenHandler.setBController(getBController());
////                invoiceScreenHandler.show();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        } else {
////            // Display an error message when trying to proceed without selected items
////            Alert alert = new Alert(Alert.AlertType.ERROR);
////            alert.setTitle("Error");
////            alert.setHeaderText(null);
////            alert.setContentText("You don't have anything selected to placee");
////            alert.showAndWait();
////        }
//    }
//
//    @FXML
//    private void handleBack(MouseEvent event) throws IOException {
//        // Truyền danh sách sản phẩm đã chọn khi quay lại màn hình ShippingScreenHandler
//        shippingScreenHandler.setSelectedCartMediaList(selectedCartMediaList);
//
//        BaseScreenHandler shippingScreenHandler = new ShippingScreenHandler(stage, Configs.SHIPPING_SCREEN_PATH, order, cartScreen, selectedCartMediaList);
//        shippingScreenHandler.setPreviousScreen(this);
//        shippingScreenHandler.setHomeScreenHandler(homeScreenHandler);
//        shippingScreenHandler.setScreenTitle("Shipping Screen");
//        shippingScreenHandler.setBController(getBController());
//        shippingScreenHandler.show();
//    }
//
//
//    @FXML
//    private void handleDeliveryType(ActionEvent event) {
//        boolean isPlaceOrderSelected = placeOrderValue.isSelected();
//
//        deliveryInstruction.setDisable(isPlaceOrderSelected);
//        shipmentDetail.setDisable(isPlaceOrderSelected);
//        deliveryTime.setDisable(isPlaceOrderSelected);
//
//        handleProvinceError();
//    }
//
//    @FXML
//    private void handleProvinceError() {
//        String province = order.getProvince();
//
//        errorProvince.setVisible(false);
//        deliveryInstruction.setDisable(true);
//        shipmentDetail.setDisable(true);
//        deliveryTime.setDisable(true);
//        updateDeliveryMethodInfoButton.setDisable(false);
//
//        if (!province.equals("Hà Nội")) {
//            if (placeRushOrderValue.isSelected()) {
//                errorProvince.setVisible(true);
//                deliveryInstruction.setDisable(true);
//                shipmentDetail.setDisable(true);
//                deliveryTime.setDisable(true);
//                updateDeliveryMethodInfoButton.setDisable(true);
//            }
//        } else {
//            updateDeliveryMethodInfoButton.setDisable(false);
//            deliveryInstruction.setDisable(!placeRushOrderValue.isSelected());
//            shipmentDetail.setDisable(!placeRushOrderValue.isSelected());
//            deliveryTime.setDisable(!placeRushOrderValue.isSelected());
//            errorProvince.setVisible(false);
//        }
//    }
//
//    public PlaceOrderController getBController() {
//        return (PlaceOrderController) super.getBController();
//    }
//}

package views.screen.shipping;

import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.shipping.Shipment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;

import java.io.IOException;
import java.util.HashMap;

public class DeliveryMethodsScreenHandler extends BaseScreenHandler {

    private Order order;

    @FXML
    private RadioButton placeRushOrderValue;

    @FXML
    private RadioButton placeOrderValue;

    @FXML
    private TextField deliveryInstruction;

    @FXML
    private TextField shipmentDetail;

    @FXML
    private DatePicker deliveryTime;

    @FXML
    private Label errorProvince;

    @FXML
    private Button updateDeliveryMethodInfoButton;

    public DeliveryMethodsScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
        super(stage, screenPath);
        this.order = order;
    }


    /**
     * @param event
     * @throws IOException
     */
    @FXML
    private void updateDeliveryMethodInfo(MouseEvent event) throws IOException {
        String deliveryInstructionString = new String(deliveryInstruction.getText());
        String shipmentDetailString = new String(shipmentDetail.getText());
        String deliveryDateString = new String();
        if (deliveryTime.getValue() != null) {
            deliveryDateString = new String(deliveryTime.getValue().toString());
        }
        int typeDelivery;
        if (placeRushOrderValue.isSelected()) {
            typeDelivery = utils.Configs.PLACE_RUSH_ORDER;
        } else {
            typeDelivery = utils.Configs.PALCE_ORDER;
        }
        var shipment = new Shipment(typeDelivery);
        shipment.setShipmentDetail(shipmentDetailString);
        shipment.setDeliveryTime(deliveryDateString);
        shipment.setDeliveryInstruction(deliveryInstructionString);

        PlaceRushOrderController.validatePlaceRushOrderData(shipment);
        order.setShipment(shipment);

        // // create invoice screen
        Invoice invoice = getBController().createInvoice(order);
        BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
        InvoiceScreenHandler.setPreviousScreen(this);
        InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
        InvoiceScreenHandler.setScreenTitle("Invoice Screen");
        InvoiceScreenHandler.setBController(getBController());
        InvoiceScreenHandler.show();
    }


    /**
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleBack(MouseEvent event) throws IOException {
        // Back to previous screen
        BaseScreenHandler ShippingScreenHandler = new ShippingScreenHandler(this.stage, Configs.SHIPPING_SCREEN_PATH,
                this.order);
        ShippingScreenHandler.setPreviousScreen(this);
        ShippingScreenHandler.setHomeScreenHandler(homeScreenHandler);
        ShippingScreenHandler.setScreenTitle("Shipping Screen");
        ShippingScreenHandler.setBController(getBController());
        ShippingScreenHandler.show();
    }


    /**
     * @param event
     */
    @FXML
    private void handleDeliveryType(ActionEvent event) {
        if (placeOrderValue.isSelected()) {
            deliveryInstruction.setDisable(true);
            shipmentDetail.setDisable(true);
            deliveryTime.setDisable(true);
        } else if (placeRushOrderValue.isSelected()) {
            deliveryInstruction.setDisable(false);
            shipmentDetail.setDisable(false);
            deliveryTime.setDisable(false);
        }
        handleProvinceError(event);
    }


    /**
     * @param event
     */
    @FXML
    private void handleProvinceError(ActionEvent event) {
        String province = new String(order.getProvince());

        errorProvince.setVisible(false);
        deliveryInstruction.setDisable(true);
        shipmentDetail.setDisable(true);
        deliveryTime.setDisable(true);
        updateDeliveryMethodInfoButton.setDisable(false);

        if (!province.equals("Hà Nội")) {
            if (placeRushOrderValue.isSelected()) {
                errorProvince.setVisible(true);
                deliveryInstruction.setDisable(true);
                shipmentDetail.setDisable(true);
                deliveryTime.setDisable(true);
                updateDeliveryMethodInfoButton.setDisable(true);
            } else {
                updateDeliveryMethodInfoButton.setDisable(false);
                deliveryInstruction.setDisable(true);
                shipmentDetail.setDisable(true);
                deliveryTime.setDisable(true);
            }
        } else {
            if (placeRushOrderValue.isSelected()) {
                errorProvince.setVisible(false);
                deliveryInstruction.setDisable(false);
                shipmentDetail.setDisable(false);
                deliveryTime.setDisable(false);
                updateDeliveryMethodInfoButton.setDisable(false);
            } else {
                updateDeliveryMethodInfoButton.setDisable(false);
                deliveryInstruction.setDisable(true);
                shipmentDetail.setDisable(true);
                deliveryTime.setDisable(true);
                errorProvince.setVisible(false);
            }
        }
    }

    /**
     * @return PlaceOrderController
     */
    public PlaceOrderController getBController() {
        return (PlaceOrderController) super.getBController();
    }
}
