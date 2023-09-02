package com.config;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

public class UserIdGenerator implements IdentifierGenerator, org.hibernate.id.Configurable {

    public static final String GENERATOR_NAME = "produtcIdGenerator";
    public static final String PREFIX_PARAM = "prefix";

    private String prefix = "";

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        this.prefix = params.getProperty(PREFIX_PARAM, "");
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object entityObject) throws HibernateException {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return prefix + uuid;
    }
}