package esiea.yangnguyen.architectureapplication.adapters.infrastructure.configuration;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.EventPublisherPort;
import esiea.yangnguyen.architectureapplication.domain.entities.Transaction;
import esiea.yangnguyen.architectureapplication.domain.repository.*;
import esiea.yangnguyen.architectureapplication.usecase.service.MessageService;
import esiea.yangnguyen.architectureapplication.usecase.service.ProductService;
import esiea.yangnguyen.architectureapplication.usecase.service.TransactionService;
import esiea.yangnguyen.architectureapplication.usecase.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DomainConfig {

    @Bean
    public MessageService messageService(MessageRepository messageRepository,
                                         UserRepository userRepository,
                                         EventPublisherPort eventPublisherPort) {
        return new MessageService(messageRepository, userRepository, eventPublisherPort);
    }

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductService(productRepository);
    }

    @Bean
    public TransactionService transactionService(TransactionRepository transactionRepository,
                                                 UserRepository userRepository,
                                                 ProductRepository productRepository) {
        return new TransactionService(transactionRepository, userRepository, productRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new UserService(passwordEncoder, userRepository);
    }
}
