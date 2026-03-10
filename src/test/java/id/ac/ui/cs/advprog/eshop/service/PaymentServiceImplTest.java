package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderService orderService; // Diperlukan untuk update status Order

    List<Payment> payments;
    Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("order-13652556", products, 1708560000L, "Safira Sudrajat");

        payments = new ArrayList<>();
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        paymentData.put("orderId", "order-13652556");
        Payment payment = new Payment("payment-1", "VOUCHER", paymentData);
        payments.add(payment);
    }

    @Test
    void testAddPayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        doReturn(payments.get(0)).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("payment-1", result.getId());
        assertEquals("order-13652556", result.getPaymentData().get("orderId"));
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = payments.get(0);

        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderService).updateStatus("order-13652556", "SUCCESS");

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        verify(orderService, times(1)).updateStatus("order-13652556", "SUCCESS");
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = payments.get(0);

        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderService).updateStatus("order-13652556", "FAILED");

        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", result.getStatus());
        verify(orderService, times(1)).updateStatus("order-13652556", "FAILED");
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testGetPaymentIfFound() {
        doReturn(payments.get(0)).when(paymentRepository).findById("payment-1");
        Payment result = paymentService.getPayment("payment-1");
        assertEquals(payments.get(0).getId(), result.getId());
    }

    @Test
    void testGetPaymentIfNotFound() {
        doReturn(null).when(paymentRepository).findById("invalid-id");
        Payment result = paymentService.getPayment("invalid-id");
        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).findAll();
        List<Payment> result = paymentService.getAllPayments();
        assertEquals(1, result.size());
    }
}