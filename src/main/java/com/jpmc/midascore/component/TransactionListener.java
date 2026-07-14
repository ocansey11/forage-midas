package com.jpmc.midascore.component;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    // The method takes the 'Transaction' class as a parameter
    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "midas-core"
    )
    public void handleTransaction(Transaction transaction) {
        System.out.println(transaction);
    }
}
