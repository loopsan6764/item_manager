package com.example.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Item;
import com.example.form.ItemForm;
import com.example.repository.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryService categoryService;

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryService categoryService) {
        this.itemRepository = itemRepository;
        this.categoryService = categoryService;
    }
    
public List<Item> findAll() {
    return this.itemRepository.findAll();
    }

//データ保存用のメソッドです
public Item save(ItemForm itemForm) {
    Item item = new Item();
    item.setName(itemForm.getName());
    item.setPrice(itemForm.getPrice());
    // カテゴリIDをセットする
    item.setCategoryId(itemForm.getCategoryId());
    return this.itemRepository.save(item);
}


public Item findById(Integer id) {
    Optional<Item> optionalItem = this.itemRepository.findById(id);
    Item item  = optionalItem.get();
    return item;
}


//データ更新用のメソッドです
public Item update(Integer id, ItemForm itemForm) {
    Item item = this.findById(id);
    item.setName(itemForm.getName());
    item.setPrice(itemForm.getPrice());
    // カテゴリIDをセットする
    item.setCategoryId(itemForm.getCategoryId());
    return this.itemRepository.save(item);
}

//データ削除用のメソッドです
//saveメソッドはEntityを返り値にもつため、返り値の型を変更
public Item delete(Integer id) {
    // idから該当のEntityクラスを取得します
    Item item = this.findById(id);
    // EntityクラスのdeletedAtフィールドを現在日時で上書きします
    item.setDeletedAt(LocalDateTime.now());
    // 更新処理
    return this.itemRepository.save(item);
}

public List<Item> findByDeletedAtIsNull() {
    return this.itemRepository.findByDeletedAtIsNull();
}

}