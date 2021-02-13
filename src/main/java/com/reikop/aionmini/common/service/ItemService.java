package com.reikop.aionmini.common.service;

import com.reikop.aionmini.work.model.Item;

public interface ItemService {

    List<Item> findItem(String keyword);
}
