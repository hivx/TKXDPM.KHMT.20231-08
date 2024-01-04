package views.screen.home;

import common.exception.ViewCartException;
import controller.HomeController;
import controller.ViewCartController;
import entity.cart.Cart;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.cart.CartScreenHandler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HomeScreenHandler extends BaseScreenHandler implements Initializable {

    public static Logger LOGGER = Utils.getLogger(HomeScreenHandler.class.getName());

    @FXML
    private Label numMediaInCart;

    @FXML
    private ImageView aimsImage;

    @FXML
    private ImageView cartImage;

    @FXML
    private TextField txtSearch;

    @FXML
    private VBox vboxMedia1;

    @FXML
    private VBox vboxMedia2;

    @FXML
    private VBox vboxMedia3;

    @FXML
    private VBox vboxMedia4;

    @FXML
    private HBox hboxMedia;

    @FXML
    private HBox header;

    @FXML
    private Pagination paging;

    @FXML
    private SplitMenuButton splitMenuBtnSearch;

    private List homeItems;

    public HomeScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    /**
     * @return Label
     */
    public Label getNumMediaCartLabel() {
        return this.numMediaInCart;
    }

    /**
     * @return HomeController
     */
    public HomeController getBController() {
        return (HomeController) super.getBController();
    }

    @Override
    public void show() {
        numMediaInCart.setText(String.valueOf(Cart.getCart().getListMedia().size()) + " media");
        super.show();
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        header.setHgrow(paging, Priority.ALWAYS);
        setBController(new HomeController());
        try {
            List medium = getBController().getAllMedia();
            this.homeItems = new ArrayList<>();
            for (Object object : medium) {
                Media media = (Media) object;
                MediaHandler m1 = new MediaHandler(Configs.HOME_MEDIA_PATH, media, this);
                this.homeItems.add(m1);
            }
        } catch (SQLException | IOException e) {
            LOGGER.info("Errors occured: " + e.getMessage());
            e.printStackTrace();
        }

        aimsImage.setOnMouseClicked(e -> {
            addMediaHome(this.homeItems);
        });

        cartImage.setOnMouseClicked(e -> {
            CartScreenHandler cartScreen;
            try {
                LOGGER.info("User clicked to view cart");
                cartScreen = new CartScreenHandler(this.stage, Configs.CART_SCREEN_PATH);
                cartScreen.setHomeScreenHandler(this);
                cartScreen.setBController(new ViewCartController());
                cartScreen.requestToViewCart(this);
            } catch (IOException | SQLException e1) {
                throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
            }
        });

        splitMenuBtnSearch.setOnMouseClicked(e -> {
            hboxMedia.getChildren().forEach(node -> {
                VBox vBox = (VBox) node;
                vBox.getChildren().clear();
            });
            String searchText = txtSearch.getText().trim();
            if (!searchText.isEmpty()) {
                try {
                    searchByTitle(searchText);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                addMediaHome(homeItems);
                setupPagination(homeItems);
            }
        });
        setupPagination(homeItems);

        addMenuItem(0, "Book", splitMenuBtnSearch);
        addMenuItem(1, "DVD", splitMenuBtnSearch);
        addMenuItem(2, "CD", splitMenuBtnSearch);
    }

    /**
     * @param title
     * @throws IOException
     */
    private void searchByTitle(String title) throws IOException {
        try {
            List searchResults = getBController().getMediaByTitle(title);

            //ArrayList modifiedSearchResulst = new ArrayList(searchResults);

            List<MediaHandler> Resulst = new ArrayList<MediaHandler>();
            for (Object object : searchResults) {
                Media media = (Media) object;
                MediaHandler m1 = new MediaHandler(Configs.HOME_MEDIA_PATH, media, this);
                Resulst.add(m1);
            }

            addMediaHome(Resulst);

            setupPagination(Resulst);
        } catch (SQLException e) {
            LOGGER.info("Error occurred during search: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void setupPagination(List items) {
        int itemsPerPage = 12;
        int numPages = (int) Math.ceil((double) items.size() / itemsPerPage);
        paging.setPageCount(numPages);
        paging.setPageFactory((Integer pageIndex) -> createPage(pageIndex, items));
    }

    private VBox createPage(int pageIndex, List items) {
        VBox pageBox = new VBox();

        int itemsPerPage = 12;
        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, items.size());

        addMediaHome(items.subList(startIndex, endIndex));

        return pageBox;
    }

    public void setImage() {
        // fix image path caused by fxml
        File file1 = new File(Configs.IMAGE_PATH + "/" + "Logo.png");
        Image img1 = new Image(file1.toURI().toString());
        aimsImage.setImage(img1);

        File file2 = new File(Configs.IMAGE_PATH + "/" + "cart.png");
        Image img2 = new Image(file2.toURI().toString());
        cartImage.setImage(img2);
    }

    /**
     * @param items
     */
    public void addMediaHome(List items) {
        // Reset the content of the hboxMedia
        vboxMedia1.getChildren().clear();
        vboxMedia2.getChildren().clear();
        vboxMedia3.getChildren().clear();
        vboxMedia4.getChildren().clear();

        // Add media items to the hboxMedia
        ArrayList mediaItems = new ArrayList(items);
        while (!mediaItems.isEmpty()) {
            for (int j = 0; j < 3 && !mediaItems.isEmpty(); j++) {
                if (!mediaItems.isEmpty()) {
                    MediaHandler media = (MediaHandler) mediaItems.get(0);
                    vboxMedia1.getChildren().add(media.getContent());
                    mediaItems.remove(media);
                }
            }
            for (int j = 0; j < 3 && !mediaItems.isEmpty(); j++) {
                if (!mediaItems.isEmpty()) {
                    MediaHandler media = (MediaHandler) mediaItems.get(0);
                    vboxMedia2.getChildren().add(media.getContent());
                    mediaItems.remove(media);
                }
            }
            for (int j = 0; j < 3 && !mediaItems.isEmpty(); j++) {
                if (!mediaItems.isEmpty()) {
                    MediaHandler media = (MediaHandler) mediaItems.get(0);
                    vboxMedia3.getChildren().add(media.getContent());
                    mediaItems.remove(media);
                }
            }
            for (int j = 0; j < 3 && !mediaItems.isEmpty(); j++) {
                if (!mediaItems.isEmpty()) {
                    MediaHandler media = (MediaHandler) mediaItems.get(0);
                    vboxMedia4.getChildren().add(media.getContent());
                    mediaItems.remove(media);
                }
            }
        }
    }


    /**
     * @param position
     * @param text
     * @param menuButton
     */
    private void addMenuItem(int position, String text, MenuButton menuButton) {
        MenuItem menuItem = new MenuItem();
        Label label = new Label();
        label.prefWidthProperty().bind(menuButton.widthProperty().subtract(31));
        label.setText(text);
        label.setTextAlignment(TextAlignment.RIGHT);
        menuItem.setGraphic(label);
        menuItem.setOnAction(e -> {
            // empty home media
            hboxMedia.getChildren().forEach(node -> {
                VBox vBox = (VBox) node;
                vBox.getChildren().clear();
            });

            // filter only media with the choosen category
            List filteredItems = new ArrayList<>();
            homeItems.forEach(me -> {
                MediaHandler media = (MediaHandler) me;
                if (media.getMedia().getTitle().toLowerCase().startsWith(text.toLowerCase())) {
                    filteredItems.add(media);
                }
            });

            // fill out the home with filted media as category
            addMediaHome(filteredItems);
        });
        menuButton.getItems().add(position, menuItem);
    }
}
