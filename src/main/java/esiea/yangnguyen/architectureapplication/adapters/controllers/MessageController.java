package esiea.yangnguyen.architectureapplication.adapters.controllers;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.EventPublisherPort;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageOutDTO;
import esiea.yangnguyen.architectureapplication.usecase.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public MessageOutDTO sendMessage(MessageCreateDTO messageCreateDTO) {
        return messageService.sendMessage(messageCreateDTO);
    }

    @GetMapping
    public Iterable<MessageOutDTO> getAllMessages() {
        return messageService.findAll();
    }

    @GetMapping("/{id}")
    public MessageOutDTO getMessage(@PathVariable Long id) {
        return messageService.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        messageService.deleteProductById(id);
    }

}
