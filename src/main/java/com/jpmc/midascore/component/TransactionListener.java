package com.jpmc.midascore.component;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public TransactionListener(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // The method takes the 'Transaction' class as a parameter
    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "midas-core"
    )


    public void handleTransaction(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        if(sender == null || recipient == null){
            return;
        }

        if(sender.getBalance() < transaction.getAmount()){
            return;
        }
        else {
            float amount = transaction.getAmount();
            sender.setBalance(sender.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount);

            userRepository.save(sender);
            userRepository.save(recipient);
            transactionRepository.save(new TransactionRecord(sender, recipient, amount));

            System.out.println("Processed :" + transaction);

        }
    }
}
