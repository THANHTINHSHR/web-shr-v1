package cdw.cdwproject.model.order;

import cdw.cdwproject.model.User.User;

import java.util.List;

/*
Class for controller response to ajax
 */
public class OrderResponse {
    private User user;
    private List<Order> orders;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    // add attribute to make js know orders is empty or not
    private String isEmpty;

    public OrderResponse(User user, List<Order> orders, int totalPages, int currentPage, int pageSize) {
        this.user = user;
        this.orders = orders;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.isEmpty =  orders.isEmpty()+"";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        this.isEmpty =  orders.isEmpty()+"";
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(String isEmpty) {
        this.isEmpty = isEmpty;
    }
}
