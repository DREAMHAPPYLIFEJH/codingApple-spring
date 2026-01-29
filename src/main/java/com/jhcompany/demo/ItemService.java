package com.jhcompany.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    public final ItemRepository itemRepository;

    public void saveItem(String title, Integer price) {
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }

    public void editItem(Long id, String title, Integer price) {
        Item item = itemRepository.findById(id).orElseThrow();
        item.setTitle(title);
        item.setPrice(price);
    }
}
