package com.reikop.aionmini.common.service;

import com.reikop.aionmini.common.repo.StatusRepo;
import com.reikop.aionmini.work.model.Status;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StatusServiceImpl implements StatusService {

    @Resource
    private StatusRepo repo;

    @Override
    public Status putData(Status status) {
        return repo.save(status);
    }
}
