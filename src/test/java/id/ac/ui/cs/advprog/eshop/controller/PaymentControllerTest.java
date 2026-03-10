package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PaymentController.class)
class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    @Test
    void testPaymentDetailPage() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/PaymentDetailSearch"));
    }

    @Test
    void testPaymentDetailByIdPage() throws Exception {
        Payment payment = new Payment("payment-1", "VOUCHER", new HashMap<>());
        when(paymentService.getPayment("payment-1")).thenReturn(payment);

        mockMvc.perform(get("/payment/detail/payment-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/PaymentDetail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testAdminListPaymentsPage() throws Exception {
        when(paymentService.getAllPayments()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/AdminPaymentList"))
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void testAdminPaymentDetailPage() throws Exception {
        Payment payment = new Payment("payment-1", "VOUCHER", new HashMap<>());
        when(paymentService.getPayment("payment-1")).thenReturn(payment);

        mockMvc.perform(get("/payment/admin/detail/payment-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/AdminPaymentDetail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testAdminSetPaymentStatus() throws Exception {
        Payment payment = new Payment("payment-1", "VOUCHER", new HashMap<>());
        when(paymentService.getPayment("payment-1")).thenReturn(payment);

        mockMvc.perform(post("/payment/admin/set-status/payment-1")
                        .param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/list"));
    }
}