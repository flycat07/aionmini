package com.reikop.aionmini.common.service;

import com.reikop.aionmini.work.model.Item;

import java.util.List;

public interface ItemService {

    List<Item> findItem(String keyword);

    void make();
}
