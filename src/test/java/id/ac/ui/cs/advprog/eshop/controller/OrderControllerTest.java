package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockitoBean
    private PaymentService paymentService;

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/CreateOrder")); // Disesuaikan
    }

    @Test
    void testOrderHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/HistoryOrder")); // Disesuaikan
    }

    @Test
    void testOrderHistoryPost() throws Exception {
        when(orderService.findAllByAuthor("Safira")).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/order/history").param("author", "Safira"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/HistoryOrderResult")) // Disesuaikan
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attributeExists("author"));
    }

    @Test
    void testPaymentOrderPage() throws Exception {
        Order order = new Order("order-1", new ArrayList<>(), 123L, "Safira");
        when(orderService.findById("order-1")).thenReturn(order);

        mockMvc.perform(get("/order/pay/order-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/PaymentOrder"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void testPaymentOrderPost() throws Exception {
        Order order = new Order("order-1", new ArrayList<>(), 123L, "Safira");
        when(orderService.findById("order-1")).thenReturn(order);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("payment-1", "VOUCHER", paymentData);

        when(paymentService.addPayment(eq(order), eq("VOUCHER"), any())).thenReturn(payment);

        mockMvc.perform(post("/order/pay/order-1")
                        .param("method", "VOUCHER")
                        .param("voucherCode", "ESHOP1234ABC5678"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/PaymentOrderResult"))
                .andExpect(model().attributeExists("payment"));
    }
}