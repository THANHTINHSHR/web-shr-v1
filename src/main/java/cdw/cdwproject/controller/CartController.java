package cdw.cdwproject.controller;

import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.cart.CartItem;
import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.product.Product;
import cdw.cdwproject.oauth.CustomOAuth2User;
import cdw.cdwproject.service.CartItemServiceImp;
import cdw.cdwproject.service.ProductServiceImp;
import cdw.cdwproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartItemServiceImp cartItemServiceImp;
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private ProductServiceImp productServiceImp;

    @GetMapping("/cart")

    public String cart(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User, @AuthenticationPrincipal MyUserDetails myUserDetails, @CookieValue(value = "guestCart", defaultValue = "") String guestCart, HttpServletResponse response) {

        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
            List<CartItem> carts = cartItemServiceImp.findCartsByUser(user);
            SecurityContextHolder.getContext().getAuthentication().getDetails();
            float cartTotal = cartItemServiceImp.getCartItemTotal(carts);

            model.addAttribute("cartTotal", cartTotal);
            model.addAttribute("carts", carts);
            return "normal/cart";
        } catch (NullPointerException e) {
            try {
                user = myUserDetails.getUser();
                List<CartItem> carts = cartItemServiceImp.findCartsByUser(user);
                SecurityContextHolder.getContext().getAuthentication().getDetails();
                float cartTotal = cartItemServiceImp.getCartItemTotal(carts);

                model.addAttribute("cartTotal", cartTotal);
                model.addAttribute("carts", carts);
                return "normal/cart";
            } catch (NullPointerException e2) {
                // read from cookie, if have no cart item (cookie) -> creat new guest cart cookie
                if (guestCart.equalsIgnoreCase( "")) {
                    Cookie cookieGuestCart = new Cookie("guestCart", "");
                    cookieGuestCart.setPath("/");
                    System.out.println("create new cookie");
                    response.addCookie(cookieGuestCart);
                    // return new empty carts
                    List<CartItem> carts = new ArrayList<>();
                    float cartTotal = 0f;
                    model.addAttribute("carts", carts);
                    model.addAttribute("cartTotal", cartTotal);
                }
                // ready have cart cookie  -> read cookie
                else {
                    System.out.println("read current cookie ");
                    System.out.println("guest cart cookie" +guestCart);
                    List<CartItem> carts = readCookieCartItems(guestCart);
                    float cartTotal = getSubTotalFromCookie(guestCart);
                    model.addAttribute("carts", carts);
                    model.addAttribute("cartTotal", cartTotal);
                }
                return "normal/cart";


            }

        }
    }

    private List<CartItem> readCookieCartItems(String guestCart) {
        // generate output
        List<CartItem> carts = new ArrayList<>();
        // read cookie
        String guestCartDecode = new String(Base64.getDecoder().decode(guestCart));
        // remove @ at head of guestCastDecode
        if(guestCartDecode.startsWith("@")){
            guestCartDecode = guestCartDecode.substring(1);
        }
        // separation between each cartItem is @
        String[] cartItemsSplit = guestCartDecode.split("@");

        for (String s: cartItemsSplit
             ) {
            System.out.println(s);
        }
        // find cartItemId,in this case it identified by product id and quantity
        for (int i = 0; i < cartItemsSplit.length; i++) {
            String indexID = cartItemsSplit[i].split("-")[0];
            String indexQty = cartItemsSplit[i].split("-")[1];
            Product product = productServiceImp.getProductByID(Integer.parseInt(indexID));
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(Integer.parseInt(indexQty));
            carts.add(cartItem);
        }

        return carts;
    }
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
    @GetMapping("/clear-cookies")
    public String clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie c: cookies
             ) {
            c.setMaxAge(0);
            response.addCookie(c);
        }


        return "normal/register";

    }


}
