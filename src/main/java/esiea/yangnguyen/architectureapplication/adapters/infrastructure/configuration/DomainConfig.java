package esiea.yangnguyen.architectureapplication.adapters.infrastructure.configuration;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository.EventPublisherRepository;
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
                                         EventPublisherRepository eventPublisherRepository) {
        return new MessageService(messageRepository, userRepository, eventPublisherRepository);
    }

    @Bean
    public ProductService productService(ProductRepository productRepository,
                                         EventPublisherRepository eventPublisherRepository) {
        return new ProductService(productRepository, eventPublisherRepository);
    }

    @Bean
    public TransactionService transactionService(TransactionRepository transactionRepository,
                                                 UserRepository userRepository,
                                                 ProductRepository productRepository) {
        return new TransactionService(transactionRepository, userRepository, productRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
