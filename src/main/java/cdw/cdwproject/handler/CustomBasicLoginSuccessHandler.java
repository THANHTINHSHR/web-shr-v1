package cdw.cdwproject.handler;

import cdw.cdwproject.model.User.AuthenticationProvider;
import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.cart.CartItem;
import cdw.cdwproject.model.product.Product;
import cdw.cdwproject.oauth.CustomOAuth2User;
import cdw.cdwproject.service.CartItemServiceImp;
import cdw.cdwproject.service.ProductServiceImp;
import cdw.cdwproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/*
this class will be invoked by Spring Security right after a user has logged in successfully to the application with basic login
 */
@Component
public class CustomBasicLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private CartItemServiceImp cartItemServiceImp;
    @Autowired
    private ProductServiceImp productServiceImp;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MyUserDetails mUser = (MyUserDetails) authentication.getPrincipal();
        String email = mUser.getUser().getEmail();


        List<CartItem> cartItemList = cartItemServiceImp.findCartsByUser(mUser.getUser());
        // if cart in database is empty, check cart in cookie
        if (cartItemList.isEmpty()) {
            Cookie[] cookies = request.getCookies();
            Cookie guestCartCookie = null;
            for (Cookie c : cookies
            ) {
                if (c.getName().equals("guestCart")) {
                    guestCartCookie = c;
                    break;
                }
            }
            if(guestCartCookie== null|| guestCartCookie.getValue().equalsIgnoreCase("")){
                List<CartItem> cartItems = new ArrayList<>();
                // save cart from cookie to db
                cartItemServiceImp.saveCartItems(cartItems);
            }
            else {
                List<CartItem> cartItems =  getCartItemsFromCookie(guestCartCookie, mUser.getUser());
                // save cart from cookie to db
                cartItemServiceImp.saveCartItems(cartItems);
                // delete cookie
                guestCartCookie.setMaxAge(0);
                response.addCookie(guestCartCookie);
            }
        }
        else {
           Cookie  guestCartCookie = new Cookie("guestCart","");
           guestCartCookie.setPath("/");
           guestCartCookie.setMaxAge(0);
           response.addCookie(guestCartCookie);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
    private List<CartItem> getCartItemsFromCookie(Cookie cartItemCookie, User user){
        List<CartItem> result = new ArrayList<>();
        String guestCartDecode = new String(Base64.getDecoder().decode(cartItemCookie.getValue()));
        // remove @ at head of guestCastDecode
        if(guestCartDecode.startsWith("@")){
            guestCartDecode = guestCartDecode.substring(1);
        }
        // separation between each cartItem is @
        String[] cartItemsSplit = guestCartDecode.split("@");
        for (int i = 0; i < cartItemsSplit.length; i++) {
            String indexID = cartItemsSplit[i].split("-")[0];
            String qty = cartItemsSplit[i].split("-")[1];

            Product product = productServiceImp.getProductByID(Integer.parseInt(indexID));
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(Integer.parseInt(qty));
            result.add(cartItem);

        }

        return  result;
    }
}
