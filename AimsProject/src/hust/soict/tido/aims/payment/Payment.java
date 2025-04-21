package hust.soict.tido.aims.payment;

import hust.soict.tido.aims.order.Order;

public interface Payment {
    /**
     * Process a payment for the given order
     * @param order The order to be paid
     * @param paymentInfo Additional payment information (e.g., card details)
     * @return Transaction result containing status and transaction ID
     */
    TransactionResult processPayment(Order order, Object paymentInfo);
    
    /**
     * Validate payment information before processing
     * @param paymentInfo The payment information to validate
     * @return True if the payment information is valid, false otherwise
     */
    boolean validatePaymentInfo(Object paymentInfo);
    
    /**
     * Class to represent the result of a transaction
     */
    class TransactionResult {
        private boolean success;
        private String transactionId;
        private String message;
        private float amount;
        private float balance;
        private String cardOwner;
        
        public TransactionResult(boolean success, String transactionId, String message, 
                               float amount, float balance, String cardOwner) {
            this.success = success;
            this.transactionId = transactionId;
            this.message = message;
            this.amount = amount;
            this.balance = balance;
            this.cardOwner = cardOwner;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public String getTransactionId() {
            return transactionId;
        }
        
        public String getMessage() {
            return message;
        }
        
        public float getAmount() {
            return amount;
        }
        
        public float getBalance() {
            return balance;
        }
        
        public String getCardOwner() {
            return cardOwner;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Transaction ID: ").append(transactionId).append("\n");
            sb.append("Card Owner: ").append(cardOwner).append("\n");
            sb.append("Transaction Amount: ").append(String.format("%.2f", amount)).append(" $\n");
            sb.append("Transaction Status: ").append(success ? "Success" : "Failed").append("\n");
            sb.append("Message: ").append(message).append("\n");
            sb.append("Balance: ").append(String.format("%.2f", balance)).append(" $");
            return sb.toString();
        }
    }
} 