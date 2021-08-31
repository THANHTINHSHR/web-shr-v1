package cdw.cdwproject.controller.admin;

import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.order.FormDataUpdateStatus;
import cdw.cdwproject.model.order.Order;
import cdw.cdwproject.model.order.OrderItem;
import cdw.cdwproject.model.order.OrderStatus;
import cdw.cdwproject.oauth.CustomOAuth2User;
import cdw.cdwproject.service.OrderServiceImp;
import cdw.cdwproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class OrderManagerController {
    @Autowired
    private OrderServiceImp orderServiceImp;
    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping("/admin/order-list")
    public String showOrderList(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails,   @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = myUserDetails.getUser();
        }

        List<Order> orders = orderServiceImp.getAllOrder();
        model.addAttribute("userName", user.getName());
        model.addAttribute("orders", orders);

        return "admin/order-list";
    }

    @PostMapping("/admin/order/update-status")
    @ResponseBody
    public String updateOrderStatus(@ModelAttribute FormDataUpdateStatus formDataUpdateStatus) {
        try {
            int oID = formDataUpdateStatus.getoID();
            OrderStatus status = formDataUpdateStatus.getStatus();
            Order order = orderServiceImp.getOrderByOrderID(oID);
            order.setStatus(status);
            if (formDataUpdateStatus.isSuccess()) {
                order.setSuccessDeliveryTime(new Date());
            }
            order.setUpdateTime(new Date());
            orderServiceImp.saveOrder(order);
            System.out.println("UPDATE ORDER STATUS SUCCESS");


            return "Update Status Success";
        } catch (Exception e) {
            System.out.println("UPDATE ORDER STATUS FAIL");
            return "Update Status Fail";
        }
    }

    @GetMapping("/admin/order/view/{oid}")
    public String showViewOrder(Model modal, @AuthenticationPrincipal MyUserDetails myUserDetails, @PathVariable("oid") int oid,  @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = myUserDetails.getUser();
        }
        Order order = orderServiceImp.getOrderByOrderID(oid);
        List<OrderItem> orderItems = orderServiceImp.getOrderItemsByOrderId(oid);

        modal.addAttribute("userName", user.getName());
        modal.addAttribute("order", order);
        modal.addAttribute("orderItems", orderItems);

        return "admin/view-order";


    }
}
