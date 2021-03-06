package com.luv2code.springbootecommerce.config;

import com.luv2code.springbootecommerce.com.luv2code.springbootecommerce.entity.Product;
import com.luv2code.springbootecommerce.com.luv2code.springbootecommerce.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

        private EntityManager entityManager;

        @Autowired
        public MyDataRestConfig(EntityManager theEntityManager){
            entityManager=theEntityManager;
        }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

        HttpMethod[] theUnwantedAction= {HttpMethod.DELETE,HttpMethod.POST,HttpMethod.PUT};

        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metadata,httpMethods)-> httpMethods.disable(theUnwantedAction))
                .withCollectionExposure((metadata,httpMethods)-> httpMethods.disable(theUnwantedAction));

        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metadata,httpMethods)-> httpMethods.disable(theUnwantedAction))
                .withCollectionExposure((metadata,httpMethods)-> httpMethods.disable(theUnwantedAction));

        exposeIds(config);
    }
    private void exposeIds(RepositoryRestConfiguration repositoryRestConfiguration){

        Set<EntityType<?>> entities =  entityManager.getMetamodel().getEntities();

        List<Class> entityClasses = new ArrayList<>();

        for(EntityType tempEntityType : entities){

            entityClasses.add(tempEntityType.getJavaType());
        }

        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        repositoryRestConfiguration.exposeIdsFor(domainTypes);

            }
}
