package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentVoucherSuccess() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("1", "VOUCHER", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectedLength() {
        paymentData.put("voucherCode", "ESHOP1234ABC567"); // 15 karakter
        Payment payment = new Payment("2", "VOUCHER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectedNotStartWithEshop() {
        paymentData.put("voucherCode", "ESHOQ1234ABC5678"); // Tidak diawali ESHOP
        Payment payment = new Payment("3", "VOUCHER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectedNot8Digits() {
        paymentData.put("voucherCode", "ESHOP1234ABC567A"); // Hanya 7 angka
        Payment payment = new Payment("4", "VOUCHER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliverySuccess() {
        paymentData.put("address", "Jalan Margonda Raya");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("5", "CASH_ON_DELIVERY", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryRejectedMissingAddress() {
        paymentData.put("address", ""); // Alamat kosong
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("6", "CASH_ON_DELIVERY", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryRejectedMissingDeliveryFee() {
        paymentData.put("address", "Jalan Margonda Raya");
        paymentData.put("deliveryFee", ""); // Biaya pengiriman kosong
        Payment payment = new Payment("7", "CASH_ON_DELIVERY", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }
}