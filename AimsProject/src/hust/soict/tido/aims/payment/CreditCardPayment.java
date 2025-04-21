package hust.soict.tido.aims.payment;

import hust.soict.tido.aims.order.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class CreditCardPayment implements Payment {

    // Simple mock for connecting to a card association system
    private static class CardAssociationSystem {
        public static boolean validateCard(String cardNumber, String expiryDate, String cvv, String cardholderName) {
            // In a real system, this would connect to a payment gateway
            // For simplicity, we'll perform some basic validation
            
            // Card number should be 16 digits
            if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
                return false;
            }
            
            // Expiry date should be in MM/YY format and not expired
            if (expiryDate == null || !expiryDate.matches("\\d{2}/\\d{2}")) {
                return false;
            }
            
            try {
                // Parse expiry date
                String[] parts = expiryDate.split("/");
                int month = Integer.parseInt(parts[0]);
                int year = Integer.parseInt(parts[1]) + 2000; // Assuming 20xx
                
                // Check if card is expired
                LocalDate expiryLocalDate = LocalDate.of(year, month, 1).plusMonths(1).minusDays(1);
                if (expiryLocalDate.isBefore(LocalDate.now())) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
            
            // CVV should be 3 digits
            if (cvv == null || !cvv.matches("\\d{3}")) {
                return false;
            }
            
            // Cardholder name should not be empty
            if (cardholderName == null || cardholderName.trim().isEmpty()) {
                return false;
            }
            
            return true;
        }
        
        public static boolean processPayment(String cardNumber, float amount) {
            // In a real system, this would process the payment through a payment gateway
            // For demo purposes, we'll randomly succeed or fail (mostly succeed)
            Random random = new Random();
            return random.nextFloat() > 0.1f; // 90% success rate
        }
        
        public static float getCardBalance(String cardNumber) {
            // In a real system, this would get the balance from the card provider
            // For demo purposes, we'll return a random balance
            Random random = new Random();
            return 1000f + random.nextFloat() * 9000f; // Random balance between 1000 and 10000
        }
    }
    
    public static class CreditCardInfo {
        private String cardNumber;
        private String expiryDate;
        private String cvv;
        private String cardholderName;
        
        public CreditCardInfo(String cardNumber, String expiryDate, String cvv, String cardholderName) {
            this.cardNumber = cardNumber;
            this.expiryDate = expiryDate;
            this.cvv = cvv;
            this.cardholderName = cardholderName;
        }
        
        public String getCardNumber() {
            return cardNumber;
        }
        
        public String getExpiryDate() {
            return expiryDate;
        }
        
        public String getCvv() {
            return cvv;
        }
        
        public String getCardholderName() {
            return cardholderName;
        }
        
        // Returns masked card number (e.g., **** **** **** 1234)
        public String getMaskedCardNumber() {
            if (cardNumber == null || cardNumber.length() < 4) {
                return "****";
            }
            return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        }
    }
    
    @Override
    public TransactionResult processPayment(Order order, Object paymentInfo) {
        if (!(paymentInfo instanceof CreditCardInfo) || order == null) {
            return new TransactionResult(
                false, 
                "ERROR",
                "Invalid payment information or order",
                0f,
                0f,
                "Unknown"
            );
        }
        
        CreditCardInfo cardInfo = (CreditCardInfo) paymentInfo;
        
        // Validate payment information
        if (!validatePaymentInfo(cardInfo)) {
            return new TransactionResult(
                false, 
                "ERROR",
                "Invalid card details",
                0f,
                0f,
                cardInfo.getCardholderName()
            );
        }
        
        float amount = order.calculateFinalTotal();
        boolean paymentSuccess = CardAssociationSystem.processPayment(cardInfo.getCardNumber(), amount);
        float balance = CardAssociationSystem.getCardBalance(cardInfo.getCardNumber());
        
        String transactionId = generateTransactionId();
        String message = paymentSuccess ? 
                "Payment processed successfully" : 
                "Payment failed. Please try again or use a different card.";
        
        return new TransactionResult(
            paymentSuccess,
            transactionId,
            message,
            amount,
            balance,
            cardInfo.getCardholderName()
        );
    }
    
    @Override
    public boolean validatePaymentInfo(Object paymentInfo) {
        if (!(paymentInfo instanceof CreditCardInfo)) {
            return false;
        }
        
        CreditCardInfo cardInfo = (CreditCardInfo) paymentInfo;
        return CardAssociationSystem.validateCard(
            cardInfo.getCardNumber(),
            cardInfo.getExpiryDate(),
            cardInfo.getCvv(),
            cardInfo.getCardholderName()
        );
    }
    
    private String generateTransactionId() {
        // Generate a unique transaction ID
        return "TX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase() + "-" +
               LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
    }
} 