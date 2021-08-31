package cdw.cdwproject.controller;

import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.cart.CartItem;
import cdw.cdwproject.model.product.Product;
import cdw.cdwproject.oauth.CustomOAuth2User;
import cdw.cdwproject.service.CartItemServiceImp;
import cdw.cdwproject.service.ProductServiceImp;
import cdw.cdwproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Base64;
import java.util.List;

@RestController
@Transactional
public class CartRestController {
    @Autowired
    private CartItemServiceImp cartItemServiceImp;
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private ProductServiceImp productServiceImp;

    @PostMapping("/cart/add/{pid}/{qty}")
    @ResponseBody
    public String addProductToCart(@PathVariable("pid") int pid, @AuthenticationPrincipal CustomOAuth2User oAuth2User, @PathVariable("qty") int qty, @AuthenticationPrincipal MyUserDetails userDetails, @CookieValue(value = "guestCart", defaultValue = "") String guestCart, HttpServletResponse response) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
            cartItemServiceImp.addProduct(pid, qty, user);
            return "items is add to your cart";
        } catch (NullPointerException e) {
            try {
                user = userDetails.getUser();
                cartItemServiceImp.addProduct(pid, qty, user);
                return "items is add to your cart";
            } catch (NullPointerException e2) {
                String guestCartValue = (guestCart.equalsIgnoreCase("") ) ? writeCookieCartItems(pid, qty) : updateCookieCartItems(guestCart, pid, qty);

                Cookie cookieGuestCart = new Cookie("guestCart", guestCartValue);
                cookieGuestCart.setPath("/");
                cookieGuestCart.setMaxAge( 60 * 60);// 1h

                response.addCookie(cookieGuestCart);
                return "items is add to your cart";
            }
        }
        /* after try to get user, if return null -> guest user
         * */

//        if (user == null) {
//            // read from cookie, if have no cart item (cookie) -> creat new guest cart cookie
//            String guestCartValue = (guestCart == "") ? writeCookieCartItems(pid, qty) : updateCookieCartItems(guestCart, pid, qty);
//
//            Cookie cookieGuestCart = new Cookie("guestCart", guestCartValue);
//            cookieGuestCart.setMaxAge(3 * 24 * 60 * 60);// 2 day
//
//            response.addCookie(cookieGuestCart);
//            // return new empty carts
//
//
//        } else {
//            cartItemServiceImp.addProduct(pid, qty, user);
//
//        }
//        return "items is add to your cart";
    }

    // sub function :write new Cookie (with one cartItem)
    private String writeCookieCartItems(int cartItemID, int qty) {
        // separation between id and qty is -
        String joinValue = cartItemID + "-" + qty;
        String encodedString = Base64.getEncoder().encodeToString(joinValue.getBytes());

        return encodedString;
    }

    // sub function : add new CartItem in cookie
    private String updateCookieCartItems(String guestCart, int cartItemID, int qty) {

        // create input for encode
        String strInputEncode = "";
        // read cookie first
        String guestCartDecode = new String(Base64.getDecoder().decode(guestCart));
        // remove @ at head of guestCastDecode
        if(guestCartDecode.startsWith("@")){
            guestCartDecode = guestCartDecode.substring(1);
        }
        // separation between each cartItem is @
        String[] cartItemsSplit = guestCartDecode.split("@");

        // find cartItemId
        int index = -1;
        for (int i = 0; i < cartItemsSplit.length; i++) {
            String indexID = cartItemsSplit[i].split("-")[0];
            if (Integer.parseInt(indexID) == cartItemID) {
                index = i;
                break;
            }

        }
        // update cart item
        // case update current cart Item
        if (index != -1) {
//            int oldQty = Integer.parseInt(cartItemsSplit[index].split("-")[1]);
//            String newQty = (oldQty + qty)+"";
            String cartItemMod = cartItemID + "-" + qty;
            cartItemsSplit[index] = cartItemMod;


            for (String cartItem : cartItemsSplit
            ) {
                strInputEncode += "@" + cartItem;
            }
            // encode.
        }
        // case add new cart item
        else {
            String newCartItem = cartItemID + "-" + qty;
            for (String cartItem : cartItemsSplit
            ) {
                strInputEncode += "@" + cartItem;
            }
            // add new cartItem
            strInputEncode += "@" + newCartItem;

        }
        String result = Base64.getEncoder().encodeToString(strInputEncode.getBytes());
        return result;
    }

    // sub function :delete CartItem in cookie
    private String deleteCookieCartItems(String guestCart, int cartItemID) {
        // create input for encode
        String strInputEncode = "";
        // read cookie first
        String guestCartDecode = new String(Base64.getDecoder().decode(guestCart));
        // remove @ at head of guestCastDecode
        if(guestCartDecode.startsWith("@")){
            guestCartDecode = guestCartDecode.substring(1);
        }
        // separation between each cartItem is @
        String[] cartItemsSplit = guestCartDecode.split("@");
        // find cartItemId
        for (int i = 0; i < cartItemsSplit.length; i++) {
            System.out.println("run loop " + i +" total loop " + (cartItemsSplit.length-1));
            System.out.println("ci length " + cartItemsSplit.length);
            String indexID = cartItemsSplit[i].split("-")[0];
            System.out.println("index id: " + indexID +" cartItem id " + cartItemID);
            if (Integer.parseInt(indexID) != cartItemID) {
                System.out.println("add this to str Input Enncode "+ cartItemsSplit[i].split("-")[0] + "-" + cartItemsSplit[i].split("-")[1]);
                strInputEncode +="@"+ cartItemsSplit[i].split("-")[0] + "-" + cartItemsSplit[i].split("-")[1];

            }
        }
        if(strInputEncode.startsWith("@")){
            strInputEncode = strInputEncode.substring(1);
        }
        System.out.println("input encode delete :"  + strInputEncode);
        String result = Base64.getEncoder().encodeToString(strInputEncode.getBytes());
        return result;
    }

    // sub function :get sub total CartItems in cookie
    private float getSubTotalFromCookie(String guestCartValue) {
        float subTotal = 0;
        // read cookie first
        String guestCartDecode = new String(Base64.getDecoder().decode(guestCartValue));
        // remove @ at head of guestCastDecode
        if(guestCartDecode.startsWith("@")){
            guestCartDecode = guestCartDecode.substring(1);
        }
        // separation between each cartItem is @
        String[] cartItemsSplit = guestCartDecode.split("@");
        // find cartItemId,in this case it identified by product id and quantity
        for (int i = 0; i < cartItemsSplit.length; i++) {
            String indexID = cartItemsSplit[i].split("-")[0];
            String indexQty = cartItemsSplit[i].split("-")[1];
            Product product = productServiceImp.getProductByID(Integer.parseInt(indexID));
            subTotal += product.getPrice() * Integer.parseInt(indexQty);

        }

        return subTotal;
    }

    @PostMapping("/cart/update/{pid}/{qty}")
    @ResponseBody
    public String updateQuantity(@PathVariable("pid") int pid, @PathVariable("qty") int qty, @AuthenticationPrincipal MyUserDetails userDetails, @AuthenticationPrincipal CustomOAuth2User oAuth2User, @CookieValue(value = "guestCart", defaultValue = "") String guestCart, HttpServletResponse response) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
            float subTotal = cartItemServiceImp.updateQuantity(pid, qty, user);
            System.out.println(subTotal);
            return String.format("%.0f", subTotal);
        } catch (NullPointerException e) {
            try {
                user = userDetails.getUser();
                float subTotal = cartItemServiceImp.updateQuantity(pid, qty, user);
                System.out.println(subTotal);
                return String.format("%.0f", subTotal);
            } catch (NullPointerException e2) {
                // because update mean cart cookie ready exits
                System.out.println("before update "  + guestCart);
                String guestCartValue = updateCookieCartItems(guestCart, pid, qty);
                System.out.println("after update " + guestCartValue);
                Cookie cookieGuestCart = new Cookie("guestCart", guestCartValue);
                cookieGuestCart.setMaxAge( 60 * 60);// 1h
                cookieGuestCart.setPath("/");
                float subTotal = getSubTotalFromCookie(guestCartValue);
                response.addCookie(cookieGuestCart);
                System.out.println(subTotal);
                return String.format("%.0f", subTotal);
            }


        }
//        if (user == null) {
//            // because update mean cart cookie ready exits
//            String guestCartValue = updateCookieCartItems(guestCart, pid, qty);
//            Cookie cookieGuestCart = new Cookie("guestCart", guestCartValue);
//            cookieGuestCart.setMaxAge(3 * 24 * 60 * 60);// 2 day
//            float subTotal = getSubTotalFromCookie(guestCartValue);
//            response.addCookie(cookieGuestCart);
//            // return new empty carts
//
//            return String.format("%.0f", subTotal);
//        } else {
//            float subTotal = cartItemServiceImp.updateQuantity(pid, qty, user);
//            System.out.println(subTotal);
//            return String.format("%.0f", subTotal);
//        }
    }


    @PostMapping("/cart")
    @ResponseBody
    public String getCartItemTotal(@AuthenticationPrincipal MyUserDetails userDetails, @AuthenticationPrincipal CustomOAuth2User oAuth2User, @CookieValue(value = "guestCart", defaultValue = "") String guestCart, HttpServletResponse response) {
        User user;
        try {
            user = userDetails.getUser();
            List<CartItem> carts = cartItemServiceImp.findCartsByUser(user);
            float cartTotal = cartItemServiceImp.getCartItemTotal(carts);
            return String.format("%.0f", cartTotal);
        } catch (NullPointerException e) {
            try {
                user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
                List<CartItem> carts = cartItemServiceImp.findCartsByUser(user);
                float cartTotal = cartItemServiceImp.getCartItemTotal(carts);
                return String.format("%.0f", cartTotal);
            } catch (NullPointerException e2) {

                float cartTotal = getSubTotalFromCookie(guestCart);
                return String.format("%.0f", cartTotal);

            }

        }


    }

    @PostMapping("/cart/delete/{pid}")
    @ResponseBody
    public boolean deleteCartItem(@PathVariable("pid") int pid, @AuthenticationPrincipal MyUserDetails userDetails, @AuthenticationPrincipal CustomOAuth2User oAuth2User, @CookieValue(value = "guestCart", defaultValue = "") String guestCart, HttpServletResponse response) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
            cartItemServiceImp.deteteCartItemByProductIdAndUserId(pid, user.getId());
            // return cartitem total
            List<CartItem> carts = cartItemServiceImp.findCartsByUser(user);
            return !carts.isEmpty();
        } catch (NullPointerException e) {
            try {
                user = userDetails.getUser();
                cartItemServiceImp.deteteCartItemByProductIdAndUserId(pid, userDetails.getUserID());
                // return cartitem total
                List<CartItem> carts = cartItemServiceImp.findCartsByUser(user);
//                float cartTotal = cartItemServiceImp.getCartItemTotal(carts);
//                return String.format("%.0f", cartTotal);
                return !carts.isEmpty();
            } catch (NullPointerException e2) {
                // because update mean cart cookie ready exits
                String guestCartValue = (guestCart.equalsIgnoreCase("")? "":deleteCookieCartItems(guestCart, pid));
                Cookie cookieGuestCart = new Cookie("guestCart", guestCartValue);
                cookieGuestCart.setPath("/");
                cookieGuestCart.setMaxAge( 60 * 60);// 1h
                response.addCookie(cookieGuestCart);
//                float subTotal = getSubTotalFromCookie(guestCartValue);
//                float subTotal = 452f;
//                 return new empty carts

//                return String.format("%.0f", subTotal);

                return !guestCartValue.equalsIgnoreCase("");
            }



        }
    }

}
