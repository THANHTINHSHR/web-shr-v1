package cdw.cdwproject.controller;

import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.cart.CartItem;
import cdw.cdwproject.model.order.Order;
import cdw.cdwproject.model.order.OrderItem;
import cdw.cdwproject.model.order.OrderResponse;
import cdw.cdwproject.model.order.payment.PaymentMethod;
import cdw.cdwproject.model.order.shipping.ShippingMethod;
import cdw.cdwproject.oauth.CustomOAuth2User;
import cdw.cdwproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {
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

    @GetMapping("/checkout")
    public String goToCheckout(Model model, @AuthenticationPrincipal MyUserDetails userDetails,  @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = userDetails.getUser();
        }
        // check if cart is empty --> empty step page
        List<CartItem> carts = cartItemServiceImp.findCartsByUser(user);
        if (carts.isEmpty()) return "errors/empty-step";
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("shippingAddress", user.getShippingAddress());

        // add cart total
        float cartTotal = cartItemServiceImp.getCartItemTotal(carts);
        model.addAttribute("cartTotal", cartTotal);

        // shipping methold
        List<ShippingMethod> shippingMethods = shippingMethodServiceImp.getShippingMethods();
        model.addAttribute("shippingMethods", shippingMethods);
        // payment methodd
        List<PaymentMethod> paymentMethods = paymentMethodServiceImp.getPaymentMethods();
        model.addAttribute("paymentMethods", paymentMethods);

        return "normal/checkout";

    }



    @GetMapping("/my-account/account-orders")
    public String myAccount(Model model, @AuthenticationPrincipal MyUserDetails userDetails, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = userDetails.getUser();
        }
        // notification
        //order
        Pageable requestedPage = PageRequest.of(0, 5, Sort.by("CREATE_AT"));
        Page<Order> orders = orderServiceImp.getOrdersByUserID(user.getId(), requestedPage);
//        Order order = new Order
        // user Detail
        model.addAttribute("orders", orders.getContent());
        model.addAttribute("user", user);
        model.addAttribute("totalPages", orders.getTotalPages());

        return "user/account-orders";

    }

    @PostMapping("/my-account/orders")
    @ResponseBody
    public OrderResponse findUserOrdersPaginated( @AuthenticationPrincipal MyUserDetails userDetails, @Param(value = "currentPage") int currentPage, @Param(value = "sByCreateDate") String sByCreateDate, @Param(value = "sByGrandTotal") String sByGrandTotal, @Param(value = "isDESC") String isDESC, @Param(value = "pageSize") int pageSize, @Param("SearchText") String searchText,  @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = userDetails.getUser();
        }

        Page<Order> orders = null;
        OrderResponse orderResponse;
        Pageable requestedPage;

        boolean sBGT = Boolean.parseBoolean(sByGrandTotal);
        boolean sBCD = Boolean.parseBoolean(sByCreateDate);
        boolean desc = Boolean.parseBoolean(isDESC);

        // sort by grand total
        if (sBGT) {
//            short by total + desc
            if (desc) {
                requestedPage = PageRequest.of(currentPage, pageSize, Sort.by("GRAND_TOTAL").descending());
            }
//            short by total + asc
            else {
                requestedPage = PageRequest.of(currentPage, pageSize, Sort.by("GRAND_TOTAL").ascending());
            }
        }
        // sort by creat date
        // because button sortByGrandTotal and sortByCreate Date in same group radiobutton.
        else {
            // sort by create date + desc
            if (desc) {
                requestedPage = PageRequest.of(currentPage, pageSize, Sort.by("CREATE_AT").descending());
            }
            // sort by create date + asc
            else {
                requestedPage = PageRequest.of(currentPage, pageSize, Sort.by("CREATE_AT").ascending());
            }
        }
//        if(searchText.equals(""){
//            orders = orderServiceImp.getOrdersByUserID(user.getId(), requestedPage);
//        }

        orders = (searchText.equals("") || searchText == null) ? orderServiceImp.getOrdersByUserID(user.getId(), requestedPage) : orderServiceImp.getOrdersByUserSearch(user.getId(), searchText, requestedPage);

//        orders = orderServiceImp.getOrdersByUserSearch(user.getId(), searchText, requestedPage);
        orderResponse = new OrderResponse(user, orders.getContent(), orders.getTotalPages(), currentPage, pageSize);
        return orderResponse;

    }

    @GetMapping("/my-account/orders/view/{orderId}")
    public String showUserOrder(Model model, @AuthenticationPrincipal MyUserDetails userDetails, @PathVariable("orderId") int orderId, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = userDetails.getUser();
        }
        Order order = orderServiceImp.getOrderByUserIDAndOrderID(user.getId(), orderId);
        // if user edit url, change order id and controller cant find order, return home page.
        if (order == null) {
            return "redirect:/#";
        }
        // because we use scroll table for order Items. So get all (not page).
        List<OrderItem> orderItems = orderServiceImp.getOrderItemsByOrderId(orderId);


        int total = 0;
        for (OrderItem orderItem : orderItems
        ) {
            total += orderItem.getProduct().getPrice() * orderItem.getQuantity();
        }

        model.addAttribute("orderItems", orderItems);
        model.addAttribute("total", total);
        model.addAttribute("order", order);

//        model.addAttribute("user" , user);
//        return"user/account-details";
        return "user/my-account-order-details";
    }
}
