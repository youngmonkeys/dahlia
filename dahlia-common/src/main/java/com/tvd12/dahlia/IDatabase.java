package com.tvd12.dahlia;

public interface IDatabase {

    int getId();

    String getName();

    ICollection getCollection(String name);

    ICollection createCollection(Object setting);
}
