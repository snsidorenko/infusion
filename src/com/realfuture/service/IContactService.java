package com.realfuture.service;

import com.realfuture.model.Contact2;

import java.util.List;

/**
 * Created by Serge on 26.01.2017.
 */
public interface IContactService {
    abstract Contact2 save(Contact2 c);
    abstract Contact2 findById(Long id);
    abstract List<Contact2> findAll();
}