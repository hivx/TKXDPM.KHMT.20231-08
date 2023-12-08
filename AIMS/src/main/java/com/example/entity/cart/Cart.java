package com.example.entity.cart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.common.exception.MediaNotAvailableException;
import com.example.entity.media.Media;

//Sequential Cohesion
public class Cart {

    private List<CartMedia> lstCartMedia;
    private static Cart cartInstance;

    public static Cart getCart(){
        if(cartInstance == null) cartInstance = new Cart();
        return cartInstance;
    }

    private Cart(){
        lstCartMedia = new ArrayList<>();
    }

    //Data coupling
    public void addCartMedia(CartMedia cm){
        lstCartMedia.add(cm);
    }

    //Data Coupling
    public void removeCartMedia(CartMedia cm){
        lstCartMedia.remove(cm);
    }

    //Data coupling
    public List getListMedia(){
        return lstCartMedia;
    }

    //Data coupling
    public void emptyCart(){
        lstCartMedia.clear();
    }

    //Data Coupling
    public int getTotalMedia(){
        int total = 0;
        for (Object obj : lstCartMedia) {
            CartMedia cm = (CartMedia) obj;
            total += cm.getQuantity();
        }
        return total;
    }

    //Data Coupling
    public int calSubtotal(){
        int total = 0;
        for (Object obj : lstCartMedia) {
            CartMedia cm = (CartMedia) obj;
            total += cm.getPrice()*cm.getQuantity();
        }
        return total;
    }

    //Stamp Coupling
    public void checkAvailabilityOfProduct() throws SQLException{
        boolean allAvai = true;
        for (Object object : lstCartMedia) {
            CartMedia cartMedia = (CartMedia) object;
            int requiredQuantity = cartMedia.getQuantity();
            int availQuantity = cartMedia.getMedia().getQuantity();
            if (requiredQuantity > availQuantity) allAvai = false;
        }
        if (!allAvai) throw new MediaNotAvailableException("Some media not available");
    }

    //Stamp coupling
    public CartMedia checkMediaInCart(Media media){
        for (CartMedia cartMedia : lstCartMedia) {
            if (cartMedia.getMedia().getId() == media.getId()) return cartMedia;
        }
        return null;
    }

}
