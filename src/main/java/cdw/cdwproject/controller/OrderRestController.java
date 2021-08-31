package cdw.cdwproject.controller;

import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.cart.CartItem;
import cdw.cdwproject.model.order.Order;
import cdw.cdwproject.model.order.OrderStatus;
import cdw.cdwproject.model.order.payment.PaymentMethod;
import cdw.cdwproject.model.order.shipping.ShippingMethod;
import cdw.cdwproject.oauth.CustomOAuth2User;
import cdw.cdwproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@Transactional
public class OrderRestController {
    @Autowired
    private OrderServiceImp orderServiceImp;
    @Autowired
    private CartItemServiceImp cartItemServiceImp;
    @Autowired
    private ShippingMethodServiceImp shippingMethodServiceImp;
    @Autowired
    private PaymentMethodServiceImp paymentMethodServiceImp;
    @Autowired
    private UserServiceImp userServiceImp;

    @PostMapping("/checkout")
    public String createOrder(Model model, @AuthenticationPrincipal MyUserDetails userDetails, @RequestParam("recipient") String recipient, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("shipping-address") String shippingAddress, @RequestParam("note") String note, @RequestParam("shippingID") int shippingID, @RequestParam("paymentID") int paymentID,  @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = userDetails.getUser();
        }
        // check if cart is empty --> empty step page
        List<CartItem> carts = cartItemServiceImp.findCartsByUser(user);
        if (carts.isEmpty()) return "errors/empty-step";
        //create Order parameter values

        Order order = new Order();
        order.setUser(user);
        order.setRecipient(recipient);
        order.setPhone(phone);
        order.setEmail(email);
        order.setShippingAddress(shippingAddress);
        order.setNote(note);
        // shipping method
        ShippingMethod sM = shippingMethodServiceImp.getShippingMethodByID(shippingID);
        order.setShippingMethod(sM);
        // payment method
        PaymentMethod pM = paymentMethodServiceImp.getPaymentMethodByID(paymentID);
        order.setPaymentMethod(pM);
    /*
    grand total and date ( base on shipping method)
     */
        float cartTotal = cartItemServiceImp.getCartItemTotal(carts);
        // grand total = cart total + shipping cost
        float grandTotal = cartTotal + sM.getShippingCost();
        // date

        Calendar calendarE = Calendar.getInstance();
        Calendar calendarL = Calendar.getInstance();
        calendarE.setTime(new Date());
        calendarE.add(Calendar.DATE, sM.getEarlyDay());
        calendarL.setTime(new Date());
        calendarL.add(Calendar.DATE, sM.getLateDay());
        Date earlyTime = calendarE.getTime();
        Date lateTime = calendarL.getTime();
        // add grand total and date to order
        order.setGrandTotal((long) grandTotal);
        order.setEarlyDeliveryTime(earlyTime);
        order.setLateDeliveryTime(lateTime);
        //status, createAt, updateAt
        order.setStatus(OrderStatus.CREATED);
        order.setCreateDate(new Date());
        order.setUpdateTime(new Date());
        // save to db
        orderServiceImp.saveOrder(order);
        orderServiceImp.saveOrderItems(order, carts);
        // delete curent cart
        cartItemServiceImp.deleteCartItemsByUserId(user.getId());
        // add attribute
        model.addAttribute("order", order);
        return "normal/order-success";
    }

}
